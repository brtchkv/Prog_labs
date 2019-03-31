package Server;

import sun.plugin2.message.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

//class ServerRunnable implements Runnable {
//    private final List<InetSocketAddress> clients = new ArrayList<>(); private final DatagramSocket socket;
//    public ServerRunnable() throws SocketException {
//        socket = new DatagramSocket(10000);
//    }
//    public void sendCollectionToAll() {
//        if (!clients.isEmpty()) {
//            System.out.println("��������� ��������� ���� ��������...");
//            clients.forEach(this::sendCollectionToClient);
//            System.out.println("�������� ���������.");
//        } else {
//            System.err.println("� ����������-�� � ������: �������� ���� ���...");
//        } }
//    private void sendCollectionToClient(InetSocketAddress to) {
//        //����������� ��������� � ���������
//        final Message message = new Message(true, EntryPoint.getCollection());
//        final byte[] bytesToSend;
//        try {
//            bytesToSend = message.toBytes();
//        } catch (IOException e) {
//            System.err.println("������ ��� �������������� ��������� � ������ ���?�.");
//            return; }
//        final ByteBuffer buffer = ByteBuffer.allocate(Message.BA_SIZE);
//        buffer.clear();
//        try {
//            buffer.put(bytesToSend);
//        } catch (BufferOverflowException e) {
//            System.err.println("������� ������� ���������!");
//        }
//        buffer.flip();
//        System.out.println("��������� ��������� �� " + to.toString());
//        int sentBytes = 0;
//        try {
//            sentBytes = EntryPoint.getServerChannel().send(buffer, to);
//        } catch (IOException e) {
//            System.out.println("������ ��� �������� �� " + to.toString());
//            System.err.println(e.getMessage());
//        }
//        System.out.println("���������� " + sentBytes + " ���?�.");
//    }
//    private void handlePacket (DatagramPacket packet) {
//        final ByteArrayInputStream bais = new ByteArrayInputStream(packet.getData());
//        System.out.println("�������� "+ packet.getLength() + " ���?�.");
//        final ObjectInputStream ois;
//        try {
//            ois  = new ObjectInputStream(bais);
//        } catch (IOException e) {
//            System.err.println("������ ��� �������������� ������ " +
//                    "���?� � ����� ��������.");
//            return; }
//        final Message receivedMessage;
//        try {
//            receivedMessage = (Message) ois.readObject();
//        } catch (ClassNotFoundException | IOException e) {
//            System.err.println("������ ��� ������ ������� �� ������.");
//            System.err.println(e.getMessage());
//            return;
//        }
//        final InetSocketAddress from = new InetSocketAddress(packet.getAddress(), packet.getPort() + 1);
//        if (!clients.contains(from) || !receivedMessage.getSTATUS()) {
//            if (!clients.contains(from))
//                System.out.println("������ ��������� �� ������ �������: " + packet.getSocketAddress().toString());
//            else System.out.println("�����?� ������ �� ��������� �� " + packet.getSocketAddress().toString());
//            clients.add(from);
//            sendCollectionToClient(from);
//        } else {
//            System.out.println("������ ������ ��������� �� " + packet.getSocketAddress().toString());
//            EntryPoint.changeCollection(receivedMessage.getList());
//        }
//    }
//    @Override
//    public void run() {
//        //noinspection InfiniteLoopStatement
//        while (true) {
//            final byte[] bytes = new byte[Message.BA_SIZE];
//            final DatagramPacket packet = new DatagramPacket(bytes, Message.BA_SIZE);
//            System.out.println("������ �����? �����...");
//            try {
//                socket.receive(packet);
//            } catch (IOException e) {
//                System.err.println("������ ��� ��������� ������: ");
//                System.err.println(e.getMessage());
//                continue;
//            }
//            System.out.println("������ ����� ���������� �� " + packet.getSocketAddress().toString());
//            handlePacket(packet);
//        } }
//}