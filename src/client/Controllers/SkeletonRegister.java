package client.Controllers;

import client.Login;
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
import java.util.Locale;
import java.util.ResourceBundle;

import static client.Login.currentResource;
import static client.Login.loadScene;

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
                    alert.setHeaderText(Login.currentResource.getString("warning"));
                    alert.setTitle(Login.currentResource.getString("register"));
                    alert.setContentText(Login.getLocaleMessageFromServer(output));
                    alert.showAndWait();

                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(Login.currentResource.getString("warning"));
                    alert.setTitle(Login.currentResource.getString("register"));
                    alert.setContentText("Can get the response!\n" + e.getMessage());
                    alert.showAndWait();
                }

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(Login.currentResource.getString("warning"));
                alert.setTitle(Login.currentResource.getString("serverConnection"));
                alert.setContentText(currentResource.getString("disconnected"));
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(Login.currentResource.getString("warning"));
            alert.setTitle(Login.currentResource.getString("register"));
            alert.setContentText(currentResource.getString("nicknameAndEmailVoid"));
            alert.showAndWait();
        }
    }

    @FXML
    void showHelp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Login.currentResource.getString("help"));
        alert.setContentText(Login.currentResource.getString("helpText"));
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
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(currentResource);
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/login.fxml"));
        Stage stage = new Stage();
        loadScene(stage, loader);
    }

    @FXML
    void language1(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("ru", "Ru")));
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/register.fxml"));
        Login.loadScene(stage, loader);
    }

    @FXML
    void language2(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("en", "Ca")));
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/register.fxml"));
        Login.loadScene(stage, loader);
    }

    @FXML
    void language3(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("ch", "Ch")));
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/register.fxml"));
        Login.loadScene(stage, loader);
    }

    @FXML
    void language4(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("pl", "Pl")));
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/register.fxml"));
        Login.loadScene(stage, loader);
    }

}
