package Client;

import Server.FileHandler;

import java.io.*;
import java.net.*;

///home/s263916/labs_prog/lab6

public class ClientFile implements Runnable{
    private DatagramSocket socket = null;
    private FileEvent event = null;
    private String sourceFilePath;
    private String destinationPath;
    private String hostName = "localHost";

    public ClientFile() {

        setSourceFilePath(FileHandler.getFileName());
    }

    public void createConnection() {
        try {
            socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(hostName);
            byte[] incomingData = new byte[1024];
            event = getFileEvent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(event);
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9877);
            socket.send(sendPacket);
            System.out.println();
            System.out.println("File sent from client:");
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            socket.receive(incomingPacket);
            String response = new String(incomingPacket.getData());
            System.out.println("Response from server: " + response.trim());

        } catch (UnknownHostException e) {
            System.out.println("Error in connection: unknown host!");
        } catch (IOException e) {
            System.out.println("Error in input/output: " + e.getLocalizedMessage());
        } catch (NullPointerException e){ }
    }

    public FileEvent getFileEvent() throws NullPointerException{
        FileEvent fileEvent = new FileEvent();
        String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1);
        String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
        fileEvent.setDestinationDirectory(destinationPath);
        fileEvent.setFilename(fileName);
        fileEvent.setSourceDirectory(sourceFilePath);
        File file = new File(sourceFilePath);
        if (file.isFile()) {
            if (FileHandler.checkFileRead(sourceFilePath)) {
                try {
                    DataInputStream diStream = new DataInputStream(new FileInputStream(file));
                    long len = (int) file.length();
                    byte[] fileBytes = new byte[(int) len];
                    int read = 0;
                    int numRead;
                    while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) {
                        read = read + numRead;
                    }
                    fileEvent.setFileSize(len);
                    if (len > 3583) {
                        fileEvent.setStatus("Error");
                        System.out.println("File exceeds 64KB and thus can't be processed!");
                    } else {
                        fileEvent.setFileData(fileBytes);
                        fileEvent.setStatus("Success");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    fileEvent.setStatus("Error");
                }
            }
        } else {
            System.out.println("Path specified is not pointing to a file");
            fileEvent.setStatus("Error");
        }
        return fileEvent;
    }

    public static void main(String[] args) {
        ClientFile client = new ClientFile();
        client.createConnection();
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    @Override
    public void run(){
        ClientFile client = new ClientFile();
        client.createConnection();
    }
}