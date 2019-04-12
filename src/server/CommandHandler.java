package server;

import java.io.*;
import java.net.InetAddress;
import java.util.*;
import shared.*;

public class CommandHandler extends Thread {

    private InetAddress inetAddress;
    private int port;
    public static String fileName;

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
                case "show":
                    buffer = show(storage);
                    break;
                case "save":
                    buffer = save(storage);
                    break;
                case "add":
                    buffer = add(storage, (Human) data);
                    break;
                case "add_if_min":
                    buffer = add_if_min(storage, (Human) data);
                    break;
                case "import":
                    buffer = import1(storage, (Vector<Human>) data);
                    break;
                case "info":
                    buffer = info(storage);
                    break;
                case "remove":
                    buffer = remove(storage, (Human) data);
                    break;
                case "help":
                    buffer = help();
                    break;
                default:
                    buffer = "Error: undefined command!".getBytes();
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
        try (FileOutputStream n = new FileOutputStream(FileHandler.getFileName(), false);
             PrintWriter printWriter = new PrintWriter(n)) {

            Iterator<Human> iterator = storage.iterator();
            while (iterator.hasNext()) {
                StringBuffer s = new StringBuffer();
                Human temp = iterator.next();

                s.append("\"" + temp.getName() + "\"," + "\"" + temp.getAge() + "\"");

                Iterator<Skill> iterator_s = temp.getSkills().iterator();
                if (iterator_s.hasNext()) {
                    s.append(",\"");
                }
                while (iterator_s.hasNext()) {
                    Skill skill = iterator_s.next();
                    if (iterator_s.hasNext()) {
                        s.append(skill.toString() + "-");
                    } else {
                        s.append(skill.toString() + "\"");
                    }
                }

                Iterator<Disability> iterator_d = temp.getDisabilities().iterator();
                if (iterator_d.hasNext()) {
                    s.append(",\"");
                }
                while (iterator_d.hasNext()) {
                    Disability disability = iterator_d.next();
                    if (iterator_d.hasNext()) {
                        s.append(disability.toString() + "-");
                    } else {
                        s.append(disability.toString() + "\"");
                    }
                }
                printWriter.println(s);
            }

            return ("<<<<<<<<<<<<<<< The source file has been updated >>>>>>>>>>>>>>>").getBytes();

        } catch (Exception e) {
            return ("Something bad has happened; Can't save!").getBytes();
        }
    }

    /**
     * <p>Добавляет элемент в коллекцию</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     */
    public byte[] add(Vector<Human> storage, Human human) {
        boolean exist = false;

        for (Human current: storage) {
            if (current.getName().toLowerCase().equals(human.getName().toLowerCase())) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            storage.add(human);
            sortCollection(storage);
            return "An object is successfully added.".getBytes();
        } else {
            return "Collection already stores this object.".getBytes();
        }
    }

    /**
     * <p>Добавляет элемент в коллекцию если он является уникальным</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     */
    public byte[] add_if_min(Vector<Human> storage, Human human) {
        if (storage.size() > 0) {
            Human min = storage.stream().min(Human::compareTo).get();
            if (human.compareTo(min) < 0) {
                return add(storage, human);
            } else {
                return "An object's name isn't the smallest! Can't add to a collection.".getBytes();
            }
        } else {
            return add(storage, human);
        }
    }

    /**
     * <p>Импортирует все объекты из заданного json файла</p>
     *
     * @param storage - ссылка на коллекцию с объектом
     */
    public byte[] import1(Vector<Human> storage, Vector<Human> importing) {
        for (Human human: importing) {
            add(storage, human);
        }

        return "+++++ Imported Successfully +++++".getBytes();
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
        if (storage.contains(human)) {
            System.out.println("A human \"" + human.toString() + "\" has been deleted :(");
            storage.remove(human);
            return ("A human \"" + human.toString() + "\" has been deleted :(").getBytes();
        }

        return "No such object in the collection. Try adding instead.".getBytes();
    }

    /**
     * <p>Выводит информацию о всех доступных командах</p>
     */
    public byte[] help() {
        String jsonExample = "\r{\r\n   \"name\": \"Elizabeth\",\r\n   \"age\": \"16\",\r\n   \"skill\": {\r\n      \"name\": \"\u041F\u0440\u044B\u0433\u0430\u0442\u044C\"\r\n   },\r\n   \"disability\": \"chin\"\r\n}\r";

        return ("Available commands:" +
                "Example of JSON Human declaration:" + jsonExample +
                "\n* add {element} - adds an element to collection, element - is a JSON string, see above" +
                "\n* show - shows a list of all elements in a collection" +
                "\n* save - save a collection to a source file" +
                "\n* import {path} - adds all of the elements to a collection from a file, path - path to the .csv file" +
                "\n* info - information about collection" +
                "\n* remove {element} - removes an element from collection, element - is a JSON string, see above" +
                "\n* add_if_min {element} - adds an element to collection if it's the smallest, element - is a JSON String, see above" +
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