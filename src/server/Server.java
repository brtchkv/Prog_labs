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
    private DataBaseConnection db;


    public Server(int port) throws IOException {
        this.port = port;
        this.udpChannel = DatagramChannel.open().bind(new InetSocketAddress("localhost", port));
        System.out.println("-- Yahoo! We Have A Lift Off! --");
        System.out.println("-- UDP Server settings --");
        System.out.println("-- UDP address: " + InetAddress.getLocalHost() + " --");
        System.out.println("-- UDP port: " + this.port + " --");
        db = new DataBaseConnection();
        storage = new Vector<>();
        System.out.println("Added " + db.loadPersons(storage) + " humans from the Database.");

    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void loadCollection() {
        if (storage == null || storage.size() == 0) {
            System.out.println("Loading the collection from the back-up file...");
            try {
                storage = FileHandler.convertToVector(filename, "all");
                CommandHandler.fileName = filename;
            } catch (Exception e) {
                System.err.println("On no, backup file is not found. Do you even care ?!");
                System.exit(1);
            }
        }
    }

    private void listen() throws Exception {

        System.out.println("-- Running Server at " + InetAddress.getLocalHost() + " --");

        CommandHandler handler;

        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            buffer.clear();
            InetSocketAddress clientAddress = (InetSocketAddress) udpChannel.receive(buffer);

            Command command;

            try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                 ObjectInputStream ois = new ObjectInputStream(bais)) {

                command = (Command) ois.readObject();

                if (command.getCommand().equals("login")){
                    System.out.println("-- Client's input: " + command.getCommand() + " " + ((String)command.getCredentials()).split(" ")[0]);
                } else if (command.getCommand().equals("show")){ }
                  else {System.out.println("-- Client's input: " + command.getCommand());}

                handler = new CommandHandler(command, storage, db, udpChannel,clientAddress);
                handler.start();

//                Response response = new Response(handler.handleCommand(command, storage, db));
//
//                oos.writeObject(response);
//                oos.flush();
//                buffer.clear();
//                buffer.put(baos.toByteArray());
//                buffer.flip();
//
//                udpChannel.send(buffer, clientAddress);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showUsage() {
        System.out.println("Hello stranger,");
        System.out.println("To run the server properly you need to follow some rules");
        System.out.println("\tjava -jar Server.jar <port> <path to backup file>");
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
            try {
                server.loadCollection();
            } catch (Exception e){
                System.out.println("Can't import your back-up file!");
            }
        }
        try {
            server.listen();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}