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
                    buffer = _import(storage, (Vector<Human>) data);
                    break;
                case "info":
                    buffer = info(storage);
                    break;
                case "remove":
                    buffer = remove(storage, (String) data);
                    break;
                case "help":
                    buffer = help();
                    break;
                default:
                    buffer = "Незиветная команда, попробуйте еще раз".getBytes();
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
            System.err.println("Произошла ошибка");
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
            return "Element successfully added".getBytes();
        } else {
            return "Duplicate element".getBytes();
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
                return "This element is not the smallest".getBytes();
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
    public byte[] _import(Vector<Human> storage, Vector<Human> importing) {
        for (Human human: importing) {
            add(storage, human);
        }

        return "Команда import выполнена".getBytes();
    }

    /**
     * <p>Выводит информацию о коллекции</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     */
    public byte[] info(Vector<Human> storage) {
        return ("Информация о коллекции\n" +
                "Тип коллекции: " + storage.getClass() + "\n" +
                "Количество элементов в коллекции: " + storage.size()).getBytes();
    }

    /**
     * <p>Удаляет элемент из коллекции</p>
     *
     * @param storage - ссылка на коллекцию с объектами
     * @param name - уникальное имя объекта
     */
    public byte[] remove(Vector<Human> storage, String name) {
        for (Human human: storage) {
            if (human.getName().toLowerCase().equals(name.toLowerCase())) {
                System.out.println("Удален человек по имени \"" + human.getName() + "\"");
                storage.remove(human);
                return ("Удален человек по имени \"" + human.getName() + "\"").getBytes();
            }
        }

        return"Такого объекта не было найдено".getBytes();
    }

    /**
     * <p>Выводит информацию о всех доступных командах</p>
     */
    public byte[] help() {

        return ("Доступные команды:" +
                "\nadd {element} - добавляет элемент в коллекцию, element - строка в формате json" +
                "\nshow - выводит список всех элементов коллекции" +
                "\nsave - сохраняет текущую в исходный файл" +
                "\nimport {path} - добавляет в коллекцию все элементы из файла в формате json, path - путь до .json файла" +
                "\ninfo - выводит информацию о коллекции" +
                "\nremove {name} - удаляет элемент из коллекции, name - уникальное имя" +
                "\nadd_if_min {element} - добавляет элемент в коллекцию если он минимальный, element - строка в формате json" +
                "\nhelp - выводит список доступных команд").getBytes();
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