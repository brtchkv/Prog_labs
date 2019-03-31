import ñlient.Client;
import server.Server;
import server.FileHandler;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class main {

    final static Logger loggerC = Logger.getLogger(Client.class.toString());
    final static Logger loggerS = Logger.getLogger(Server.class.toString());

    public static void main(String[] args) throws SocketException, UnknownHostException {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println();
                if (FileHandler.getSavePermission()) {
                    System.out.println("Saved");
                }else {
                    System.err.println("********* Fatal Quit: Lost collection data! **********");
                }

            } catch (Exception e) {
                System.err.println("********* Fatal Quit: Lost collection data! **********");
            }
        }));

        loggerS.info("server Running");
        new Thread(new Server()).start();

        loggerC.info("Starting ñlient...");
        new Thread(new Client()).start();

    }


}
