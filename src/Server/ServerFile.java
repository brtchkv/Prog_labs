package Server;
import java.io.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Collections;
import java.util.LinkedHashSet;

import Client.FileEvent;

import static Server.Server.setCollection;

public class ServerFile implements Runnable{
    private DatagramSocket socket = null;
    private FileEvent fileEvent = null;

    public ServerFile() {

    }

    public LinkedHashSet<Human> createAndListenSocket() {
        try {
            socket = new DatagramSocket(9877);
            byte[] incomingData = new byte[1024 * 1000 * 50];
            while (true) {
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                socket.receive(incomingPacket);
                byte[] data = incomingPacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                fileEvent = (FileEvent) is.readObject();
                System.out.println(new String(fileEvent.getFileData()));
                if (fileEvent.getStatus().equalsIgnoreCase("Error")) {
                    System.out.println("Some issue happened while packing the data @ client side");
                }
//                createAndWriteFile(); // writing the file to hard disk
                InetAddress IPAddress = incomingPacket.getAddress();
                int port = incomingPacket.getPort();
                String reply = "\n\t+++++++++ File has been imported! +++++++++\n".trim();
                byte[] replyBytes = reply.getBytes();
                DatagramPacket replyPacket =
                        new DatagramPacket(replyBytes, replyBytes.length, IPAddress, port);
                socket.send(replyPacket);

                return FileHandler.convertToLinkedHashSetFromString((new String(fileEvent.getFileData())).split("\n"));

            }

        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createAndWriteFile() {
        String outputFile = fileEvent.getDestinationDirectory() + "/" + fileEvent.getFilename();
        if (!new File(fileEvent.getDestinationDirectory()).exists()) {
            new File(fileEvent.getDestinationDirectory()).mkdirs();
        }
        File dstFile = new File(outputFile);
        FileOutputStream fileOutputStream;
        try {

            fileOutputStream = new FileOutputStream(dstFile);
            fileOutputStream.write(fileEvent.getFileData());
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("Output file : " + outputFile + " is successfully saved ");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ServerFile server = new ServerFile();
        server.createAndListenSocket();
    }

    @Override
    public void run(){
        try {
            ServerFile server = new ServerFile();

            setCollection(Collections.synchronizedSet(server.createAndListenSocket()));

        }catch (Exception e){}

    }
}
