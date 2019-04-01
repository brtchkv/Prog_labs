package server;


import shared.*;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

public class Server {
    private static String Name = "humans";
    private static String Host = "localhost";
    private static final int port = 6666;
    private static final Vector<Human> collection = new Vector<>();
    private static DatagramChannel serverChannel;
    private static ServerRunnable serverRunnable;

    public static int getPort() {
        return port;
    }
    public static Vector<Human> getCollection() {
        return collection;
    }
    public static DatagramChannel getServerChannel() {
        return serverChannel;
    }

    public static void changeCollection (List<Human> newCollection) {
        collection.removeAllElements();
        collection.addAll(newCollection);
        serverRunnable.sendCollectionToAll();
    }

    public static void main(String[] args) {

        //открываем канал для исходящих сообщении?
        try {
            serverChannel = DatagramChannel.open();
        } catch (IOException e) {
            System.err.println("Error in input/output: " + e.getLocalizedMessage());
            return;
        }
        //занимаем порт для входящих сообщении?
        try {
            serverRunnable = new ServerRunnable();
        } catch (IOException e) {
            System.err.println("Error while accessing the port:");
            System.err.println(e.getMessage());
            return;
        }
        //поток-сервер
        final Thread serverThread = new Thread(serverRunnable);
        serverThread.setDaemon(true);
        serverThread.start();

        //Сохраняем данные и закрываем соединение при завершении программы
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //TODO:save
            }));
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            String command;
            try {
                System.out.println();
                System.out.println("Feed me with your command:");
                System.out.print("> ");
                command = scanner.nextLine();
                System.out.println();

            }catch(NoSuchElementException | IllegalStateException ex){
                command = "exit";
            }
            switch (command.trim().toLowerCase()) {
                case "\\q": case "close": case "exit": case "quit":case "q":
                    System.out.println("Shutting down ...");
                    System.exit(0);
                    return;
                case "save":
                    //TODO:сохранить коллекцию на сервере ?!
                    break;
                case "load":
                    //TODO:загрузить коллекцию ?!
                    break;
                default:
                    System.out.println("There're only 3 commands:\n\t*)save\n\t*)exit\n\t*)load");
            } }
    }
}
