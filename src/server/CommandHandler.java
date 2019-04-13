package server;

import java.io.*;
import java.net.InetAddress;
import java.util.*;
import shared.*;

public class CommandHandler extends Thread {

    private InetAddress inetAddress;
    private int port;
    public static String fileName;
    private static String FILEPATH = "human.csv";

    public CommandHandler(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    public CommandHandler() {

    }

    @Override
    public void run() {
    }



    /**
     * <p>Ищет исполняемую команду и исполняет её</p>
     * @param com - команда
     * @param storage - ссылка на коллекцию с объектами
     */
    public Object handleCommand(Command com, Vector<Human> storage) {
        synchronized (CommandHandler.class) {
            String command = com.getCommand();
            Object data = com.getData();

            byte[] buffer;

            switch (command.toLowerCase()) {
                case "connecting":
                    buffer = "connected".getBytes();
                    break;
                case "add":
                    if (data != null) {
                        buffer = add(storage, (Human) data);
                    } else {buffer = help();}
                    break;
                case "add_if_min":
                    if (data != null) {
                        buffer = add_if_min(storage, (Human) data);
                    } else {buffer = help();}
                    break;
                case "remove":
                    if (data != null) {
                        buffer = remove(storage, (Human) data);
                    } else {buffer = help();}
                    break;
                case "remove_greater":
                    if (data != null) {
                        buffer = remove_greater(storage, (Human) data);
                    } else {buffer = help();}
                    break;
                case "remove_lower":
                    if (data != null) {
                        buffer = remove_lower(storage, (Human) data);
                    } else {buffer = help();}
                    break;
                case "show":
                    buffer = show(storage);
                    break;
                case "save":
                    buffer = save(storage);
                    break;
                case "import":
                    buffer = import1(storage, (Vector<Human>) data);
                    break;
                case "info":
                    buffer = info(storage);
                    break;
                case "help":
                    buffer = help();
                    break;
                default:
                    buffer = "Error: undefined command! Type \"help\" for a list of available commands".getBytes();
            }
            return buffer;
        }
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
    public byte[] save(Vector<Human> storage) {

        if (FileHandler.checkFileWrite(FILEPATH)) {
            return FileHandler.save(storage, FILEPATH);
        }
        return "Can't write to a file. Check permissions.".getBytes();
    }

    /**
     * <p>Добавляет элемент в коллекцию</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     * @param human - объект Human, который надо добавить
     */
    public byte[] add(Vector<Human> storage, Human human) {
        synchronized (storage) {
            if (storage.add(human)) {
                sortCollection(storage);
                System.out.println("A human " + human.toString()+ " was successfully added.");
                return ("A human " + human.toString()+ " was successfully added.").getBytes();
            } else {
                return "Collection already stores this object.".getBytes();
            }
        }
    }

    /**
     * <p>Добавляет элемент в коллекцию если он является уникальным</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     * @param human - обьект, который надо добавить
     */
    public byte[] add_if_min(Vector<Human> storage, Human human) {
        if (storage.size() > 0) {
            synchronized (storage) {
                Human min = storage
                        .stream()
                        .min(Human::compareTo)
                        .get();
                if (human.compareTo(min) < 0) {
                    return add(storage, human);
                } else {
                    return (human.getName()+ "'s name isn't the smallest: Can't add to a collection!").getBytes();
                }
            }
        } else {
            return add(storage, human);
        }
    }

    /**
     * <p>Импортирует все объекты из заданного json файла</p>
     *
     * @param storage - ссылка на коллекцию с объектом
     * @param importing - ссылка на импортируемую коллекцию
     */
    public byte[] import1(Vector<Human> storage, Vector<Human> importing) {
        long start = storage.stream().count();
        for (Human human: importing) {
            add(storage, human);
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
    public byte[] remove(Vector<Human> storage, Human human) {

        long start = storage.stream().count();
        synchronized(storage) {
            storage.removeIf(x -> !storage.contains(human));
        }
        long end = storage.stream().count();
        if ((start - end) > 0) {
            System.out.println("A human \"" + human.toString() + "\" has been deleted");
            return ("A human \"" + human.toString() + "\" has been deleted :(").getBytes();
        } else {return "There's no such object in the collection. Try adding instead.".getBytes();}
    }

    /**
     * <p>Удаляет все элементы меньше аргумента</p>
     * @param endObject (Human) - Object of class Human
     */
    public byte[] remove_lower(Vector<Human> storage, Human endObject) {


        long start = storage.stream().count();
        synchronized(storage) {
            storage.removeIf(item -> item.compareTo(endObject) > 0);
        }
        long end = storage.stream().count();


        System.out.println("Deleted " + (start - end) + " objects.");
        return ("Deleted " + (start - end) + " objects. :(").getBytes();

    }

    /**
     * <p>Удаляет все элементы больше аргумента</p>
     * @param startObject (Human) - Object of class Human
     */
    public byte[] remove_greater(Vector<Human> storage, Human startObject) {

        long start = storage.stream().count();
        synchronized(storage) {
            storage.removeIf(item -> item.compareTo(startObject) < 0);
        }
        long end = storage.stream().count();
        System.out.println("Deleted " + (start - end) + " objects.");
        return ("Deleted " + (start - end) + " objects. :(").getBytes();
    }


    /**
     * <p>Выводит информацию о всех доступных командах</p>
     */
    public byte[] help() {
        String jsonExample = "\r\n{\r\n   \"name\": \"Elizabeth\",\r\n   \"age\": \"16\",\r\n   \"skill\": {\r\n      \"name\": \"\u041F\u0440\u044B\u0433\u0430\u0442\u044C\"\r\n   },\r\n   \"disability\": \"chin\"\r\n}\r";

        return ("\nAvailable commands: \n" +
                "Example of JSON Human declaration:" + jsonExample +
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