package server;

import shared.Command;
import shared.Human;
import shared.Response;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

public class CommandHandler extends Thread {

    public static String fileName;
    private String username = null;
    private String password = null;
    private String mail = null;
    private Vector<Human> storage;
    private Command command;
    private DataBaseConnection db;
    private DatagramChannel udpChannel;
    private InetSocketAddress clientAddress;


    public CommandHandler() { }

    public CommandHandler(Command command, Vector<Human> humans, DataBaseConnection db, DatagramChannel channel, InetSocketAddress clientAddress) {
        this.command = command;
        this.storage = humans;
        this.udpChannel = channel;
        this.db = db;
        this.clientAddress = clientAddress;
    }



    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        buffer.clear();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            Response response = new Response(handleCommand(command, storage, db));

            oos.writeObject(response);
            oos.flush();
            buffer.clear();
            buffer.put(baos.toByteArray());
            buffer.flip();

            udpChannel.send(buffer, clientAddress);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * <p>Ищет исполняемую команду и исполняет её</p>
     * @param com - команда
     * @param storage - ссылка на коллекцию с объектами
     */
    public Object handleCommand(Command com, Vector<Human> storage, DataBaseConnection db) {
        Object credentials = com.getCredentials();
        if (com.getCredentials() != null && ((String) credentials).split(" ").length > 2) {
            username = ((String) credentials).split(" ")[0];
            mail = ((String) credentials).split(" ")[1];
            password = ((String) credentials).split(" ")[2];
        } else if (credentials != null) {
            username = ((String) credentials).split(" ")[0];
            password = ((String) credentials).split(" ")[1];
        }


            String command = com.getCommand();
            Object data = com.getData();
            if (db.executeLogin(username, password) == 0 || command.toLowerCase().equals("connecting") || command.toLowerCase().equals("register")) {
                byte[] buffer;

                switch (command.toLowerCase()) {
                    case "connecting":
                        buffer = "connected".getBytes();
                        break;
                    case "add":
                        if (data != null) {
                            buffer = add(storage, (Human) data, db, username);
                        } else {
                            buffer = help();
                        }
                        break;
                    case "add_if_min":
                        if (data != null) {
                            buffer = add_if_min(storage, (Human) data, db, username);
                        } else {
                            buffer = help();
                        }
                        break;
                    case "remove":
                        if (data != null) {
                            buffer = remove(storage, (Human) data, db, username);
                        } else {
                            buffer = help();
                        }
                        break;
                    case "remove_greater":
                        if (data != null) {
                            buffer = remove_greater(storage, (Human) data, db, username);
                        } else {
                            buffer = help();
                        }
                        break;
                    case "remove_lower":
                        if (data != null) {
                            buffer = remove_lower(storage, (Human) data, db, username);
                        } else {
                            buffer = help();
                        }
                        break;
                    case "show":
                        buffer = show(storage);
                        break;
                    case "save":
                        buffer = save(storage, db);
                        break;
                    case "import":
                        buffer = import1(storage, (Vector<Human>) data, db, username);
                        break;
                    case "info":
                        buffer = info(storage);
                        break;
                    case "help":
                        buffer = help();
                        break;
                    case "register":
                        int resultR = db.executeRegister(username, mail, password);
                        if (resultR == 1) {
                            buffer = "Email registration is approved!".getBytes();
                        } else if (resultR == 0) {
                            buffer = "You've already registered!".getBytes();
                        } else {
                            buffer = "Can't register you".getBytes();
                        }
                        break;
                    case "login":
                        int result = db.executeLogin(username, password);
                        if (result == 0) {
                            buffer = "Logged in".getBytes();
                        } else if (result == 1) {
                            buffer = "You need to register first!".getBytes();
                        } else if (result == 2) {
                            buffer = "Wrong Password!".getBytes();
                        } else {
                            buffer = "Can't log in".getBytes();
                        }
                        break;
                    default:
                        buffer = "Error: undefined command! Type \"help\" for a list of available commands".getBytes();
                }return buffer;
            }else return "User is not authorized".getBytes();

    }

    /**
     * <p>Показывает все данные, содержащиеся в коллекции</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     */
    public byte[] show(Vector<Human> storage) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(outputStream)){
            oos.writeObject(storage);
            oos.flush();
            return outputStream.toByteArray();
        } catch (IOException e) {
            System.err.println("Aliens snatched the collection! Can't show it.");
        }
        return null;

    }

    /**
     * <p>Сохраняет данные работы программы в файл исходник</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     */
    public byte[] save(Vector<Human> storage, DataBaseConnection db) {
        if (storage != null) {
            db.savePersons(storage);
            return "Saved Humans to the DataBase".getBytes();
        } else return "Collection is empty; nothing to save!".getBytes();

    }

    /**
     * <p>Добавляет элемент в коллекцию</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     * @param human - объект Human, который надо добавить
     */
    public byte[] add(Vector<Human> storage, Human human, DataBaseConnection db, String username) {

        if (storage.add(human)) {
            sortCollection(storage);
            db.addToDB(human, username);
            System.out.println("A human " + human.toString()+ " was successfully added.");
            return ("A human " + human.toString()+ " was successfully added.").getBytes();
        } else {
            return "Collection already stores this object.".getBytes();
        }

    }

    /**
     * <p>Добавляет элемент в коллекцию если он является уникальным</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     * @param human - обьект, который надо добавить
     */
    public byte[] add_if_min(Vector<Human> storage, Human human, DataBaseConnection db, String username) {
        if (storage.size() > 0) {
            Human min = storage
                    .stream()
                    .min(Human::compareTo)
                    .get();
            if (human.compareTo(min) < 0) {
                return add(storage, human, db, username);
            } else {
                return (human.getName()+ "'s name isn't the smallest: Can't add to a collection!").getBytes();
            }
        } else {
            return add(storage, human, db, username);
        }
    }

    /**
     * <p>Импортирует все объекты из заданного json файла</p>
     *
     * @param storage - ссылка на коллекцию с объектом
     * @param importing - ссылка на импортируемую коллекцию
     */
    public byte[] import1(Vector<Human> storage, Vector<Human> importing, DataBaseConnection db, String username) {
        long start = storage.stream().count();
        for (Human human: importing) {
            add(storage, human, db, username);
        }
        long end = storage.stream().count();
        System.out.println("Imported "+ (end-start) + " objects.");
        return ("+++++ Imported "+ (end-start) + " objects +++++").getBytes();
    }

    /**
     * <p>Выводит информацию о коллекции</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     */
    public byte[] info(Vector<Human> storage) {
        return ("Collection is a " + storage.getClass() + " type.\n" +
                "Currently it contains " + storage.size() + " objects.").getBytes();
    }

    /**
     * <p>Удаляет элемент из коллекции</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     * @param human - обьект типа Human
     */
    public byte[] remove(Vector<Human> storage, Human human, DataBaseConnection db, String username) {
        if (storage.removeIf(x -> storage.contains(human) && (username.equals(x.getOwner()) || x.getOwner().equals("all")))) {

            db.removePerson(username, human);
            System.out.println("A human " + human.toString() + " has been deleted");
            return ("A human " + human.toString() + " has been deleted :(").getBytes();
        } else {
            return "There's no such object in the collection. Try adding instead.\nPerhaps, you don't have rights to delete it!".getBytes();
        }
    }

    /**
     * <p>Удаляет все элементы меньше аргумента</p>
     * @param endObject (Human) - Object of class Human
     */
    public byte[] remove_lower(Vector<Human> storage, Human endObject, DataBaseConnection db, String username) {
        long start = storage.stream().count();
        synchronized (storage) {
            storage.stream()
                    .filter(x -> (username.equals(x.getOwner()) || x.getOwner().equals("all")))
                    .filter(x -> x.compareTo(endObject) < 0)
                    .forEach(x -> {
                        db.removePerson(username, x);
                    });
            storage.removeIf(item -> item.compareTo(endObject) < 0 && (username.equals(item.getOwner()) || item.getOwner().equals("all")));
        }
        long end = storage.stream().count();
        System.out.println("Deleted " + (start - end) + " objects.");
        return ("Deleted " + (start - end) + " objects. :(").getBytes();
    }

    /**
     * <p>Удаляет все элементы больше аргумента</p>
     * @param startObject (Human) - Object of class Human
     */
    public byte[] remove_greater(Vector<Human> storage, Human startObject, DataBaseConnection db, String username) {
        long start = storage.stream().count();
        synchronized (storage) {
            storage.stream()
                    .filter(x -> (username.equals(x.getOwner()) || x.getOwner().equals("all")))
                    .filter(x -> x.compareTo(startObject) > 0)
                    .forEach(x -> {
                        db.removePerson(username, x);
                    });
            storage.removeIf(item -> item.compareTo(startObject) > 0 && (username.equals(item.getOwner()) || item.getOwner().equals("all")));
        }
        long end = storage.stream().count();
        System.out.println("Deleted " + (start - end) + " objects.");
        return ("Deleted " + (start - end) + " objects. :(").getBytes();
    }


    /**
     * <p>Выводит информацию о всех доступных командах</p>
     */
    public byte[] help() {
        String jsonExample = "\r\n{\r\n   \"name\": \"Elizabeth\",\r\n   \"age\": \"16\",\r\n   \"skill\": {\r\n      \"name\": \"\u041F\u0440\u044B\u0433\u0430\u0442\u044C\",\n" +
                "      \"info\": \"Спорт\"\n\r   }\r\n}\r\n";

        return ("Example of JSON Human declaration:" + jsonExample +
                "\nAvailable commands: \n" +
                "\n* add {element} - adds an element to collection, element - is a JSON string, see above" +
                "\n* show - shows a list of all elements in a collection" +
                "\n* save - save a collection to a source file" +
                "\n* import {path} - adds all of the elements to a collection from a file, path - path to the .csv file" +
                "\n* info - information about collection" +
                "\n* remove {element} - removes an element from collection, element - is a JSON string, see above" +
                "\n* add_if_min {element} - adds an element to collection if it's the smallest, element - is a JSON String, see above" +
                "\n* remove_greater {element} - removes objects greater than an element, element - is a JSON String, see above" +
                "\n* remove_lower {element} - removes objects lower than an element, element - is a JSON String, see above" +
                "\n* help - a list of all available commands").getBytes();
    }

    /**
     * <p>Сортирует коллекцию</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     */
    private static void sortCollection(Vector<Human> storage) {
        Collections.sort(storage);
    }
}