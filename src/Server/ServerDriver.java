package Server;

import java.net.SocketException;
import java.util.logging.Logger;

public class ServerDriver {
    final static Logger loggerS = Logger.getLogger(Server.class.toString());

    public static void main(String[] args) throws SocketException {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println();
                if (FileHandler.getSavePermission()) {
                    System.out.println("Saved");
                } else {
                    System.err.println("********* Fatal Quit: Lost collection data! **********");
                }

            } catch (Exception e) {
                System.err.println("********* Fatal Quit: Lost collection data! **********");
            }
        }));

        loggerS.info("Server Running");
        new Thread(new Server()).start();
    }
}
