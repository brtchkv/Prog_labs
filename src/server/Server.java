package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import ñlient.ClientFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import java.util.Set;
import java.util.logging.Logger;


public class Server implements Runnable {
    final static Logger logger = Logger.getLogger(Server.class.toString());

    private DatagramSocket serverSocket;
    private static Boolean initImport = true;

    private static Set<Human> collection;

    private byte[] in;
    private byte[] out;

    /*
     * Our constructor which instantiates our serverSocket
     */
    public Server() throws SocketException {
        serverSocket = new DatagramSocket(10000);
    }

    public static void setInitImport(Boolean initImport) {
        Server.initImport = initImport;
    }

    public static void setCollection(Set<Human> col) {
        collection = col;
    }

    public void run() {
        while (true) {
            try {
                in = new byte[1024];
                out = new byte[1024];

                /*
                 * Create our inbound datagram packet
                 */
                DatagramPacket receivedPacket = new DatagramPacket(in, in.length);
                serverSocket.receive(receivedPacket);

                /*
                 * Retrieve the IP Address and port number of the datagram packet
                 * we've just received
                 */
                InetAddress IPAddress = receivedPacket.getAddress();
                int port = receivedPacket.getPort();

                /*
                 * Get the data from the packet we've just received
                 * and transform it to uppercase.
                 */
                String text = new String(receivedPacket.getData()).trim();
                logger.info("server Received: " + text
                        + "\nFrom IP: " + IPAddress + "\nPort: " + port);

                try {
                    if (text.contains("import") && initImport) {
                        Thread t0 = new Thread(new ServerFile());
                        t0.start();

                        Thread t1 = new Thread(new ClientFile());
                        t1.start();
                        Thread.sleep(500);

                        if (collection != null) {
                            setInitImport(false);
                            out = "The collection has been imported.".getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(out, out.length, IPAddress, port);
                            serverSocket.send(sendPacket);

                            continue;

                        }else {
                            setInitImport(true);
                            out = ("The collection has not been imported.\n" + FileHandler.getFileName()).getBytes();

                            DatagramPacket sendPacket = new DatagramPacket(out, out.length, IPAddress, port);
                            serverSocket.send(sendPacket);
                            continue;
                        }

                    } else if (text.contains("save") && !initImport){
                            //TODO: save
                    } else if (text.contains("import")){
                        out = "The collection has already been imported!".getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(out, out.length, IPAddress, port);
                        serverSocket.send(sendPacket);
                        continue;

                    }

                }catch (Exception e){
                    out = "Error while transferring the source file!".getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(out, out.length, IPAddress, port);
                    serverSocket.send(sendPacket);
                    continue;
                }


                if (!initImport){
                    Console cs = new Console(collection);
                    cs.execute(text);

                }else {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
                    outputStream.write("Nothing to execute on. Import a collection!".getBytes());

                    byte c[] = outputStream.toByteArray();

                    DatagramPacket sendPacket = new DatagramPacket(c, c.length, IPAddress, port);
                    serverSocket.send(sendPacket);
                    continue;
                }

                /*
                 * Create a DatagramPacket which will return our message back to the last system
                 * that we received from
                 */
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
                outputStream.write("Your command has been received by server.".getBytes());

                byte c[] = outputStream.toByteArray();
                DatagramPacket sendPacket1 = new DatagramPacket(c, c.length, IPAddress, port);
                serverSocket.send(sendPacket1);

            } catch (IOException e) {
                logger.info("Exception thrown: " + e.getLocalizedMessage());
            } catch (Exception e) {
                logger.info("Exception thrown: "+ e.getLocalizedMessage());
            }

        }
    }

}