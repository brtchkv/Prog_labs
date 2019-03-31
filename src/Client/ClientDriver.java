package Client;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class ClientDriver {
    final static Logger loggerC = Logger.getLogger(Client.class.toString());

    public static void main(String[] args) throws SocketException, UnknownHostException {

        loggerC.info("Starting Client...");
        new Thread(new Client()).start();

    }
}
