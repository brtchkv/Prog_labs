package server;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Vector;
import shared.*;

public class Server {

    private DatagramChannel udpChannel;
    private int port;
    private Vector<Human> storage;
    private String filename;


    public Server(int port) throws IOException {
        this.port = port;
        this.udpChannel = DatagramChannel.open().bind(new InetSocketAddress("192.168.10.10", port));
        System.out.println("!We Have A Lift Off!");
        System.out.println("Server is in the sky at:");
        System.out.println("address: " + InetAddress.getLocalHost());
        System.out.println("port: " + this.port);

    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void loadCollection() {
        System.out.println("Loading the collection...");
        try {
            storage = FileHandler.convertToVector(filename);
            CommandHandler.fileName = filename;
        } catch (Exception e) {
            System.err.println("On no, backup file is not found. Do you even care about accidents?!");
            System.exit(1);
        }
    }

    private void listen() throws Exception {

        System.out.println("-- Running Server at " + InetAddress.getLocalHost() + " --");

        CommandHandler handler;

        while (true) {
            // Receiving udp package
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            buffer.clear();
            InetSocketAddress clientAddress = (InetSocketAddress) udpChannel.receive(buffer);


            Command command;

            try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                 ObjectInputStream ois = new ObjectInputStream(bais);
                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos)) {

                command = (Command) ois.readObject();
                System.out.println("-- Client's input: " + command.getCommand());

                // creating response for client
                /*Thread thread = new Thread(() -> {
                });*/
                handler = new CommandHandler();
                handler.start();
                Response response = new Response(handler.handleCommand(command, storage));

                oos.writeObject(response);
                oos.flush();
                buffer.clear();
                buffer.put(baos.toByteArray());
                buffer.flip();

                udpChannel.send(buffer, clientAddress);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showUsage() {
        System.out.println("Hello stranger,");
        System.out.println("To run server properly you need to follow some rules");
        System.out.println("\tjava Server <port> <path to backup file>");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        int input_port = -1;
        if (args.length == 0) {
            showUsage();
        }

        try {
            input_port = Integer.parseInt(args[0]);
        } catch (IllegalArgumentException e) {
            showUsage();
        }

        Server server = new Server(input_port);

        if (args.length == 2) {
            server.setFilename(args[1]);
        }
        try {
            server.loadCollection();
            server.listen();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}