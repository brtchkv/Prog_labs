package client;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import shared.*;

import java.io.*;
import java.net.DatagramPacket;
import java.util.TimerTask;
import java.util.Vector;

public class BackTable extends TimerTask {
    public static Vector<Human> storage;
    public static TableView tableView;
    public static String size;

    {
        tableView = new TableView();
        this.createTable();
    }


    public void run(){
        DatagramPacket toSend = null;

        byte[] sending;
        Command c = new Command("show", null, SkeletonLogin.getNickname() + " " + server.DataBaseConnection.encryptString(SkeletonLogin.getPassword()));

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(outputStream)){
            oos.writeObject(c);
            oos.flush();
            sending = outputStream.toByteArray();
            toSend = new DatagramPacket(sending, sending.length, Client.getServerAddress(), Client.getPort());
        } catch (IOException e) {
            System.out.println("Can't create a DatagramPacket\n"+ e.getMessage());

        }


        try {
            if (Client.getUdpSocket() != null) {
                byte[] buf = new byte[8192];
                DatagramPacket collectionResponse = new DatagramPacket(buf, buf.length);
                if (Client.isAuth()) {
                    if (toSend != null) {
                        Client.getUdpSocket().send(toSend);
                        Client.getUdpSocket().receive(collectionResponse);
                        try (ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                             ObjectInputStream ois = new ObjectInputStream(bais)) {
                            Response response = (Response) ois.readObject();
                            ByteArrayInputStream bs = new ByteArrayInputStream((byte[])response.getResponse());
                            ObjectInputStream os = new ObjectInputStream(bs);
                            tableView.getItems().clear();
                            storage = (Vector<Human>) os.readObject();
                            size = String.valueOf(storage.stream().count());
                            storage.stream().forEach(x -> tableView.getItems().add(x));
                            tableView.refresh();
                        }catch (Exception e){
                            System.out.println("Can't refresh the table!" + e.getMessage());
                        }
                    }
                }
            }

        }catch (Exception e) {
            //System.out.println("Can't update the table!" + e.getMessage());
        }

    }


    public void createTable(){
        TableColumn<String, Human> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, Human> column2 = new TableColumn<>("Age");
        column2.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<String,Human> column3 = new TableColumn<>("Owner");
        column3.setCellValueFactory(new PropertyValueFactory<>("owner"));


        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);

    }
}
