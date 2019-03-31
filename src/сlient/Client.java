package ñlient;

import server.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;


public class Client implements Runnable{
    final static Logger logger = Logger.getLogger(Server.class.toString());

    private DatagramSocket clientSocket;
    private InetAddress IPAddress;

    /*
     * Our byte arrays that we'll use to read in and send out to our UDP server
     */
    private byte[] outData;
    private byte[] inData;

    /*
     * Our ñlient.ñlient constructor which instantiates our clientSocket
     * and get's our IPAddress
     */
    public Client() throws SocketException, UnknownHostException{
        clientSocket = new DatagramSocket();
        IPAddress = InetAddress.getByName("localhost");
        //inFromUser = new BufferedReader(new InputStreamReader(System.in));
    }

    private void shutdown(){
        clientSocket.close();
    }

    public void run() {
        logger.info("ñlient Started, Listening for Input:");
        Console console = new Console();
        /*
         * Start a while loop that will run until we kill the program, this will continuously
         * poll for user input and send it to the server.
         */
        while(true){
            try {

                inData = new byte[1024];
                outData = new byte[1024];
                /*
                 * First we read in the users input from the console.
                 */
                String sentence = console.execute();
                //String sentence = inFromUser.readLine().trim();
                outData = sentence.getBytes();

                /*
                 * Next we create a datagram packet which will allow us send our message back to our datagram server
                 */
                DatagramPacket out = new DatagramPacket(outData, outData.length, IPAddress, 10000);
                clientSocket.send(out);


                /*
                 * Once we've sent our message we create a second datagram packet which will
                 * let us receive a response.
                 */
                DatagramPacket in = new DatagramPacket(inData, inData.length);
                clientSocket.receive(in);



                /*
                 * Finally we log the response from the server using
                 */

                System.out.println();
                String modifiedSentence = new String(in.getData()).trim();
                logger.info("ñlient Received: " + modifiedSentence + "\n");

            } catch (UnknownHostException e) {
                logger.warning("Error in connection: unknown host!");
            } catch (IOException e) {
                logger.warning( "Error in input/output: " + e.getLocalizedMessage());
            }
        }
    }

}