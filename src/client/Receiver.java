package client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

import shared.*;

public class Receiver implements Runnable{
    private final DatagramChannel serverChannel;

    public Receiver(DatagramChannel channel) {
        this.serverChannel = channel;
    }

    private void handleMessage (Message receivedMessage) {
        if (receivedMessage.getSTATUS()) {
            System.out.println("Пришла полная коллекция.");
            final List<Human> collection = Client.getCollection();
            collection.clear();
            collection.addAll(receivedMessage.getList());

            for (int i = 1, collectionSize = collection.size(); i <= collectionSize; i++) {
                final Human humans;
                try {
                    humans = collection.get(i - 1);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Задудосили");
                    return; }
                //TODO:add i to collection of humans
            }

        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                final ByteBuffer receivedBuffer = ByteBuffer.allocate(Message.PACKET_SIZE);
                receivedBuffer.clear();
                System.out.println("Жду сообщении?... ");
                serverChannel.receive(receivedBuffer);
                System.out.println("Получено " + receivedBuffer.position() + " баи?т.");
                final ByteArrayInputStream bais = new ByteArrayInputStream(receivedBuffer.array());
                final ObjectInputStream ois = new ObjectInputStream(bais);
                final Message message = (Message) ois.readObject();
                handleMessage(message);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Ошибка при получении данных");
                System.err.println(e.getMessage());
                break;
            } }
    }
}
