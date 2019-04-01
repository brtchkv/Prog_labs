package client;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;
import java.util.Vector;

import shared.*;

public class Client {
    private static final int SERVER_PORT = 6666;
    private static final String SERVER = "localhost";
    private static Sender sender;
    private static final List<Human> collection = new Vector<>();
    private static byte[] in;
    private static byte[] out;

    public static Sender getSender() {
        return sender;
    }

    public static List<Human> getCollection() {
        return collection;
    }

    public static void main(String[] args) {
        //if (args.length >= 1) {
//            if (args.length > 1)
//                System.out.println("«аданы лишние аргументы, которые будут проигнорированы.");
            //парсим адрес сервера
            final InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getByName(SERVER); //args[0]
            } catch (UnknownHostException e) {
                System.err.println("Ќе пон€л адрес сервера.");
                System.err.println(e.getMessage());
                return;
            }

                //принимаем команду с клиента
                String sentence = new ConsoleClient().execute();
                out = sentence.getBytes();


                final InetSocketAddress serverAddress = new InetSocketAddress(inetAddress, SERVER_PORT);
                //пробуем инициализировать отправител€
                try {
                    sender = new Sender(serverAddress);
                } catch (SocketException e) {
                    System.err.println("ќшибка при попытке зан€ть порт.");
                    System.err.println(e.getMessage());
                    return;
                }


                //пытаемс€ инициализировать прие?мник
                final DatagramChannel serverChannel;
                try {
                    serverChannel = DatagramChannel.open(); //канал дл€ общени€ с сервером
                    //отправл€ть ответ на порт, которыи? на 1 больше порта,
                    //с которого прише?л запрос
                    SocketAddress s = new InetSocketAddress(InetAddress.getLocalHost(),getSender().getPort() + 1);
                    serverChannel.bind(s);
                    //отправл€ем команду считанную с клиента на сервер
                    ByteBuffer f = ByteBuffer.wrap(out);
                    f.flip();
                    serverChannel.send(f,s);
                } catch (IOException e) {
                    System.err.println("ќшибка при попытке открыть сетевои? канал");
                    System.err.println(e.getMessage());
                    return;
                }


                //инициализируем поток, слушающии? сетевои? канал
                final Thread receiverThread = new Thread(new Receiver(serverChannel));
                receiverThread.setDaemon(true);
                receiverThread.start();


//        } else {
//            System.err.println("ƒолжен быть задан хот€ бы один аргумент.");
//        }
    }
}
