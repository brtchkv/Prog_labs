package client;

import client.Controllers.SkeletonLogin;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.util.Duration;
import shared.*;

import java.io.*;
import java.net.DatagramPacket;
import java.util.Optional;
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
                                storageOld.stream().forEach(x -> {
                                    if (!storage.stream().anyMatch(y -> y.getId() == x.getId())){
                                        try {
                                            drawSleepyHuman(x, x.getX(), x.getY());
                                            Thread.sleep(500);
                                            cleanCanvasDrawExcept(x);
                                        } catch (InterruptedException e) { }
                                    }

                                });
                                //gc.clearRect(0, 0, 351, 434);
                                storage.stream().forEach(x -> tableView.getItems().add(x));
                                //storage.stream().forEach(x -> drawHuman(x, x.getX(), x.getY()));
                                storage.stream().forEach(x -> {
                                    if (storageOld.stream().anyMatch(y -> y.getId() == x.getId())){
                                        try {
                                            Human human = storageOld.stream().filter(y -> y.getId() == x.getId()).findFirst().get();
                                            if (human.getX() != x.getX() || human.getY() != x.getY()) {
                                                moveHuman(x, human.getX(), human.getY(), x.getX(), x.getY());
                                            }
                                        }catch (Exception e) {}
                                    } else {
                                        try {
                                            drawSleepyHuman(x, x.getX(), x.getY());
                                            Thread.sleep(500);
                                        } catch (InterruptedException e) { }
                                        drawHuman(x, x.getX(), x.getY());
                                    }
                                });
                                tableView.refresh();
                                storageOld = storage;
                            } else if (!storage.equals(storageOld)) {
                                newUselessWindow = false;
                                storage.stream().forEach(x -> tableView.getItems().add(x));
                                storage.stream().forEach(x -> drawHuman(x, x.getX(), x.getY()));
                                tableView.refresh();
                                storageOld = storage;
                            }
                        } catch (Exception e) {
                            System.out.println("Can't refresh the table!" + e.getMessage());
                            e.printStackTrace();
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

    public static void drawHuman(Human human, int xC, int yC){

        gc.beginPath();
        gc.save();
        gc.scale(0.4 * human.getSize(),0.4 * human.getSize());
        gc.strokeArc(20 + xC, 85 + yC, 20, 40, 70, 280, ArcType.OPEN); // обводка левого уха
        gc.strokeArc(170 + xC, 85 + yC, 20, 42, 80, 360, ArcType.OPEN); // обводка правого уха
        gc.setFill(Color.valueOf(server.DataBaseConnection.encryptString(human.getOwner()).substring(0,6)));
        gc.fillOval(30 + xC,35 + yC,150,150);    // For face
        gc.fillOval(20 + xC, 85 + yC,20, 40 );   // заливка левого уха
        gc.fillOval(170 + xC, 85 + yC,20, 42 );  // заливка правого уха
        gc.strokeOval(30 + xC,35 + yC,150,150);  // обводка лица
        gc.setFill(Color.BLACK);
        gc.fillOval(60 + xC,80 + yC,15,25);      // Left Eye
        gc.fillOval(130 + xC,80 + yC,15,25);     // Right Eye
        gc.setFill(Color.SADDLEBROWN);
        gc.strokeOval(5 + xC, 25 + yC, 195, 62); // обводка полей шл€пы
        gc.fillOval(5 + xC, 25 + yC, 195, 62);   // пол€ шл€пы
        gc.setFill(Color.BLACK);
        gc.strokeArc(73 + xC, 35 + yC, 60, 30, 180, 180, ArcType.OPEN); // обводка выступа шл€пы
        gc.strokeArc(73 + xC, 0 + yC, 60, 100, 0, 180, ArcType.OPEN); // выступ шл€пы
        gc.setFill(Color.SADDLEBROWN);
        gc.fillArc(73 + xC, 0 + yC, 60, 100, 0, 180, ArcType.OPEN); // выступ шл€пы
        gc.setFill(Color.BLACK);
        double x[] = {104 + (double) xC,94 + (double) xC,104 + (double) xC,104 + (double) xC};
        double y[] = {105 + (double) yC,124 + (double) yC,124 + (double) yC,105 + (double) yC};
        gc.fillPolygon(x , y, 4);           // Nose
        gc.arc(105 + xC,139 + yC,37,22,0,-180);  // нЄбо рта

//        gc.strokeLine(133,148,144,159);           // Smile arc2

        gc.setFill(Color.web("#e35d6a"));           // цвет нЄба
        gc.fill();                                  // заливка рта
        gc.setFill(Color.WHITE);
        gc.fillRect(68.5 + xC,138 + yC,73,8);   // зубы
        gc.strokeArc(67.5 + xC,116 + yC,75,45,0,-180,ArcType.ROUND); // обводка рта
        gc.restore();
        gc.closePath();

    }

    public static void cleanCanvas(){
        gc.clearRect(0, 0, 351, 434);
        storageOld.stream().forEach(x -> drawHuman(x, x.getX(), x.getY()));
    }

    public static void moveHuman(Human human, double fromX, double fromY, double toX, double toY){
        DoubleProperty x  = new SimpleDoubleProperty();
        DoubleProperty y  = new SimpleDoubleProperty();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(x, fromX),
                        new KeyValue(y, fromY)
                ),
                new KeyFrame(Duration.seconds(4),
                        new KeyValue(x, toX),
                        new KeyValue(y, toY)
                )
        );
        timeline.setAutoReverse(false);
        timeline.setCycleCount(1);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                cleanCanvasDrawExcept(human);
                drawHuman(human, x.intValue(), y.intValue());
            }
        };

        timer.start();
        timeline.play();
    }

    public static void cleanCanvasDrawExcept(Human human){
        gc.clearRect(0, 0, 351, 434);
        storageOld.stream().filter(x -> x.getId() != human.getId()).forEach(x -> drawHuman(x, x.getX(), x.getY()));
    }

    public static void drawSleepyHuman(Human human, int x, int y) {
        gc.beginPath();
        gc.save();
        gc.scale(0.4 * human.getSize(),0.4 * human.getSize());
        gc.strokeArc(20 + x, 85 + y, 20, 40, 70, 280, ArcType.OPEN); // обводка левого уха
        gc.strokeArc(170 + x, 85 + y, 20, 42, 80, 360, ArcType.OPEN); // обводка правого уха
        gc.setFill(Color.valueOf(server.DataBaseConnection.encryptString(human.getOwner()).substring(0,6)));
        gc.fillOval(30 + x,35 + y,150,150);    // For face
        gc.fillOval(20 + x, 85 + y,20, 40 );   // заливка левого уха
        gc.fillOval(170 + x, 85 + y,20, 42 );  // заливка правого уха
        gc.strokeOval(30 + x,35 + y,150,150);  // обводка лица
        gc.setFill(Color.BLACK);
        gc.strokeLine(125 + x,100 + y,155 + x,100 + y);
        gc.strokeLine(50 + x,100 + y,80 + x,100 + y);
        gc.setFill(Color.SADDLEBROWN);
        gc.strokeOval(5 + x, 25 + y, 195, 62); // обводка полей шл€пы
        gc.fillOval(5 + x, 25 + y, 195, 62);   // пол€ шл€пы
        gc.setFill(Color.BLACK);
        gc.strokeArc(73 + x, 35 + y, 60, 30, 180, 180, ArcType.OPEN); // обводка выступа шл€пы
        gc.strokeArc(73 + x, 0 + y, 60, 100, 0, 180, ArcType.OPEN); // выступ шл€пы
        gc.setFill(Color.SADDLEBROWN);
        gc.fillArc(73 + x, 0 + y, 60, 100, 0, 180, ArcType.OPEN); // выступ шл€пы
        gc.setFill(Color.BLACK);
        double x1[] = {104 + (double) x,94 + (double) x,104 + (double) x,104 + (double) x};
        double y1[] = {105 + (double) y,124 + (double) y,124 + (double) y,105 + (double) y};
        gc.fillPolygon(x1 , y1, 4);           // Nose
        gc.strokeLine(70 + x,150 + y,135 + x,150 + y);
        gc.restore();
        gc.closePath();
    }

}
