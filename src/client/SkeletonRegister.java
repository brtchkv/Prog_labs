package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import server.DataBaseConnection;
import shared.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

public class SkeletonRegister {
    private static String nickname;
    private static String emailAddress;

    @FXML
    private TextField nick;

    @FXML
    private TextField email;

    @FXML
    void clear(ActionEvent event) {
        nick.clear();
        email.clear();
    }

    @FXML
    void register(ActionEvent event) {
        this.nickname = nick.getCharacters().toString();
        this.emailAddress = email.getCharacters().toString();
        if (nickname != null && emailAddress != null && !nickname.isEmpty() && !emailAddress.isEmpty()) {
            try {
                SkeletonLogin.client.udpSocket.send(SkeletonLogin.client.createRequest("register", null, nickname + " " + emailAddress + " " + DataBaseConnection.getToken()));
                byte[] resp = new byte[8192];
                DatagramPacket responsePacket = new DatagramPacket(resp, resp.length);

                SkeletonLogin.client.udpSocket.receive(responsePacket);

                try (ByteArrayInputStream bais = new ByteArrayInputStream(resp);
                     ObjectInputStream ois = new ObjectInputStream(bais)) {
                    Response response = (Response) ois.readObject();

                    String output = new String(SkeletonLogin.client.decodeResponse("register", response));

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Register");
                    alert.setContentText(output);
                    alert.showAndWait();

                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Register");
                    alert.setContentText("Can get the response!\n" + e.getMessage());
                    alert.showAndWait();
                }

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Server Connection");
                alert.setContentText("Disconnected from the server\nTry again later!");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Register");
            alert.setContentText("Make sure nickname and email fields are filled!");
            alert.showAndWait();
        }
    }

    @FXML
    void showHelp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setContentText("An application for organising your slavery business.\nCreator: Ivan Bratchikov");
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        assert nick != null : "fx:id=\"nick\" was not injected: check your FXML file 'register.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'register.fxml'.";

    }

    @FXML
    void loginWindow(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Login.loadScene(stage, loader);
    }

}
