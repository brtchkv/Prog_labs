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
//                System.out.println("������ ������ ���������, ������� ����� ���������������.");
            //������ ����� �������
            final InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getByName(SERVER); //args[0]
            } catch (UnknownHostException e) {
                System.err.println("�� ����� ����� �������.");
                System.err.println(e.getMessage());
                return;
            }

                //��������� ������� � �������
                String sentence = new ConsoleClient().execute();
                out = sentence.getBytes();


                final InetSocketAddress serverAddress = new InetSocketAddress(inetAddress, SERVER_PORT);
                //������� ���������������� �����������
                try {
                    sender = new Sender(serverAddress);
                } catch (SocketException e) {
                    System.err.println("������ ��� ������� ������ ����.");
                    System.err.println(e.getMessage());
                    return;
                }


                //�������� ���������������� ����?����
                final DatagramChannel serverChannel;
                try {
                    serverChannel = DatagramChannel.open(); //����� ��� ������� � ��������
                    //���������� ����� �� ����, �������? �� 1 ������ �����,
                    //� �������� �����?� ������
                    SocketAddress s = new InetSocketAddress(InetAddress.getLocalHost(),getSender().getPort() + 1);
                    serverChannel.bind(s);
                    //���������� ������� ��������� � ������� �� ������
                    ByteBuffer f = ByteBuffer.wrap(out);
                    f.flip();
                    serverChannel.send(f,s);
                } catch (IOException e) {
                    System.err.println("������ ��� ������� ������� �������? �����");
                    System.err.println(e.getMessage());
                    return;
                }


                //�������������� �����, ���������? �������? �����
                final Thread receiverThread = new Thread(new Receiver(serverChannel));
                receiverThread.setDaemon(true);
                receiverThread.start();


//        } else {
//            System.err.println("������ ���� ����� ���� �� ���� ��������.");
//        }
    }
}
