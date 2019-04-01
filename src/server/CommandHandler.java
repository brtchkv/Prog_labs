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
     * <p>���� ����������� ������� � ��������� �</p>
     * @param com - �������
     * @param storage - ������ �� ��������� � ���������
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
                    buffer = "���������� �������, ���������� ��� ���".getBytes();
            }
            return buffer;
        }
    }

    /**
     * <p>���������� ��� ������, ������������ � ���������</p>
     *
     * @param storage - ������ �� ��������� � ���������
     */
    public byte[] show(Vector<Human> storage) {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(outputStream)){
            oos.writeObject(storage);
            oos.flush();
            return outputStream.toByteArray();
        } catch (IOException e) {
            System.err.println("��������� ������");
        }

        return null;

    }

    /**
     * <p>��������� ������ ������ ��������� � ���� ��������</p>
     *
     * @param storage - ������ �� ��������� � ���������
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
     * <p>��������� ������� � ���������</p>
     *
     * @param storage - ������ �� ��������� � ���������
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
     * <p>��������� ������� � ��������� ���� �� �������� ����������</p>
     *
     * @param storage - ������ �� ��������� � ���������
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
     * <p>����������� ��� ������� �� ��������� json �����</p>
     *
     * @param storage - ������ �� ��������� � ��������
     */
    public byte[] _import(Vector<Human> storage, Vector<Human> importing) {
        for (Human human: importing) {
            add(storage, human);
        }

        return "������� import ���������".getBytes();
    }

    /**
     * <p>������� ���������� � ���������</p>
     *
     * @param storage - ������ �� ��������� � ���������
     */
    public byte[] info(Vector<Human> storage) {
        return ("���������� � ���������\n" +
                "��� ���������: " + storage.getClass() + "\n" +
                "���������� ��������� � ���������: " + storage.size()).getBytes();
    }

    /**
     * <p>������� ������� �� ���������</p>
     *
     * @param storage - ������ �� ��������� � ���������
     * @param name - ���������� ��� �������
     */
    public byte[] remove(Vector<Human> storage, String name) {
        for (Human human: storage) {
            if (human.getName().toLowerCase().equals(name.toLowerCase())) {
                System.out.println("������ ������� �� ����� \"" + human.getName() + "\"");
                storage.remove(human);
                return ("������ ������� �� ����� \"" + human.getName() + "\"").getBytes();
            }
        }

        return"������ ������� �� ���� �������".getBytes();
    }

    /**
     * <p>������� ���������� � ���� ��������� ��������</p>
     */
    public byte[] help() {

        return ("��������� �������:" +
                "\nadd {element} - ��������� ������� � ���������, element - ������ � ������� json" +
                "\nshow - ������� ������ ���� ��������� ���������" +
                "\nsave - ��������� ������� � �������� ����" +
                "\nimport {path} - ��������� � ��������� ��� �������� �� ����� � ������� json, path - ���� �� .json �����" +
                "\ninfo - ������� ���������� � ���������" +
                "\nremove {name} - ������� ������� �� ���������, name - ���������� ���" +
                "\nadd_if_min {element} - ��������� ������� � ��������� ���� �� �����������, element - ������ � ������� json" +
                "\nhelp - ������� ������ ��������� ������").getBytes();
    }

    /**
     * <p>��������� ���������</p>
     *
     * @param storage - ������ �� ��������� � ���������
     */
    private static void sortCollection(Vector<Human> storage) {
        Collections.sort(storage);
    }
}