package client;

import client.Controllers.SkeletonLogin;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import shared.*;

import java.io.*;
import java.net.DatagramPacket;
import java.util.TimerTask;
import java.util.Vector;

public class BackTable extends TimerTask {
    public static Vector<Human> storageOld = new Vector<>();
    public static Vector<Human> storage;
    public static TableView tableView;
    private static GraphicsContext gc;
    public static boolean newUselessWindow = false;

    public BackTable(GraphicsContext gc){
        tableView = new TableView();
        this.createTable();
        this.gc = gc;
    }

    public void run(){
            DatagramPacket toSend;
            byte[] sending;
            Command c = new Command("show", null, SkeletonLogin.getNickname() + " " + server.DataBaseConnection.encryptString(SkeletonLogin.getPassword()));

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
                oos.writeObject(c);
                oos.flush();
                sending = outputStream.toByteArray();
                toSend = new DatagramPacket(sending, sending.length, Client.getServerAddress(), Client.getPort());
                Client.getUdpSocket().send(toSend);
            } catch (IOException e) {
                System.out.println("Can't create a DatagramPacket\n" + e.getMessage());
            }

            try {
                if (Client.getUdpSocket() != null) {
                    byte[] buf = new byte[8192];
                    DatagramPacket collectionResponse = new DatagramPacket(buf, buf.length);
                    if (Client.isAuth()) {
                        Client.getUdpSocket().receive(collectionResponse);
                        try (ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                             ObjectInputStream ois = new ObjectInputStream(bais)) {
                            Response response = (Response) ois.readObject();
                            ByteArrayInputStream bs = new ByteArrayInputStream((byte[]) response.getResponse());
                            ObjectInputStream os = new ObjectInputStream(bs);
                            storage = (Vector<Human>) os.readObject();
                            if (!storage.equals(storageOld) && !newUselessWindow) {
                                tableView.getItems().clear();
                                gc.clearRect(0, 0, 351, 434);
                                storage.stream().forEach(x -> tableView.getItems().add(x));
                                storage.stream().forEach(x -> drawHuman(x));
                                storage.stream().forEach(x -> {
                                    if (!storageOld.stream().anyMatch(y -> y.equals(x))){
                                        System.out.println("����� ������� " + x.toString());
                                    }
                                });
                                tableView.refresh();
                                storageOld = storage;
                            } else if (!storage.equals(storageOld)) {
                                newUselessWindow = false;
                                storage.stream().forEach(x -> tableView.getItems().add(x));
                                storage.stream().forEach(x -> drawHuman(x));
                                tableView.refresh();
                                storageOld = storage;
                            }
                        } catch (Exception e) {
                            System.out.println("Can't refresh the table!" + e.getMessage());
                        }
                    }
                }

            } catch (Exception e) { }

    }


    public void createTable(){
        TableColumn<String, Human> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, Human> column2 = new TableColumn<>("Age");
        column2.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<String,Human> column3 = new TableColumn<>("Owner");
        column3.setCellValueFactory(new PropertyValueFactory<>("owner"));

        TableColumn<String,Human> column4 = new TableColumn<>("Size");
        column4.setCellValueFactory(new PropertyValueFactory<>("size"));

        TableColumn<String,Human> column5 = new TableColumn<>("X");
        column5.setCellValueFactory(new PropertyValueFactory<>("X"));

        TableColumn<String,Human> column6 = new TableColumn<>("Y");
        column6.setCellValueFactory(new PropertyValueFactory<>("Y"));

        TableColumn<String,Human> column7 = new TableColumn<>("Creation Date");
        column7.setCellValueFactory(new PropertyValueFactory<>("dateTime"));


        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().addAll(column4,column5,column6, column7);

    }

    public static void drawHuman(Human human){

        int xC = human.getX();
        int yC = human.getY();

        gc.beginPath();
        gc.save();
        gc.scale(0.4 * human.getSize(),0.4 * human.getSize());
        gc.strokeArc(20 + xC, 85 + yC, 20, 40, 70, 280, ArcType.OPEN); // ������� ������ ���
        gc.strokeArc(170 + xC, 85 + yC, 20, 42, 80, 360, ArcType.OPEN); // ������� ������� ���
        gc.setFill(Login.customColor);
        gc.fillOval(30 + xC,35 + yC,150,150);    // For face
        gc.fillOval(20 + xC, 85 + yC,20, 40 );   // ������� ������ ���
        gc.fillOval(170 + xC, 85 + yC,20, 42 );  // ������� ������� ���
        gc.strokeOval(30 + xC,35 + yC,150,150);  // ������� ����
        gc.setFill(Color.BLACK);
        gc.fillOval(60 + xC,80 + yC,15,25);      // Left Eye
        gc.fillOval(130 + xC,80 + yC,15,25);     // Right Eye
        gc.setFill(Color.SADDLEBROWN);
        gc.strokeOval(5 + xC, 25 + yC, 195, 62); // ������� ����� �����
        gc.fillOval(5 + xC, 25 + yC, 195, 62);   // ���� �����
        gc.setFill(Color.BLACK);
        gc.strokeArc(73 + xC, 35 + yC, 60, 30, 180, 180, ArcType.OPEN); // ������� ������� �����
        gc.strokeArc(73 + xC, 0 + yC, 60, 100, 0, 180, ArcType.OPEN); // ������ �����
        gc.setFill(Color.SADDLEBROWN);
        gc.fillArc(73 + xC, 0 + yC, 60, 100, 0, 180, ArcType.OPEN); // ������ �����
        gc.setFill(Color.BLACK);
        double x[] = {104 + (double) xC,94 + (double) xC,104 + (double) xC,104 + (double) xC};
        double y[] = {105 + (double) yC,124 + (double) yC,124 + (double) yC,105 + (double) yC};
        gc.fillPolygon(x , y, 4);           // Nose
        gc.arc(105 + xC,139 + yC,37,22,0,-180);  // ��� ���

//        gc.strokeLine(133,148,144,159);           // Smile arc2

        gc.setFill(Color.web("#e35d6a"));           // ���� ���
        gc.fill();                                  // ������� ���
        gc.setFill(Color.WHITE);
        gc.fillRect(68.5 + xC,138 + yC,73,8);   // ����
        gc.strokeArc(67.5 + xC,116 + yC,75,45,0,-180,ArcType.ROUND); // ������� ���
        gc.restore();
        gc.closePath();

    }

    public static void cleanCanvas(){
        gc.clearRect(0, 0, 351, 434);
        storageOld.stream().forEach(x -> drawHuman(x));
    }

}
