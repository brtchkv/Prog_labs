package client;

import shared.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Random;

public class Sender {
    private final DatagramSocket socket;
    private final InetSocketAddress serverAddress;
    private final int port;

    public int getPort() {
        return port;
    }
    public Sender(InetSocketAddress serverAddress) throws SocketException {
        port = new Random().nextInt(666)+13666;
        socket = new DatagramSocket(port);
        this.serverAddress = serverAddress;
    }
    public void sendMessage (Message message) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream(Message.PACKET_SIZE);
            final ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(message);
            final byte[] sendBuf = baos.toByteArray();
            final DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, serverAddress);
            System.out.println("ќтправл€ю сообщение...");
            socket.send(packet);
            System.out.println("ќтправлено " + packet.getLength() +  " баи?т.");
        } catch (IOException e) {
            System.err.println("ќшибка при отправке сообщени€");
            System.err.println(e.getMessage());
        } }
}
