package client.Controllers;

import client.Client;
import client.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import shared.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.util.Locale;
import java.util.ResourceBundle;


public class SkeletonLogin {
    private static String nickname;
    private static String password;
    public static Client client;

    public SkeletonLogin() {
        try {
            client = new Client("localhost", 8080);
        }catch (Exception e){}
    }

    @FXML
    private TextField nick;

    @FXML
    private PasswordField pass;

    @FXML
    void clear(ActionEvent event) {
        nick.clear();
        pass.clear();
    }

    public static String getNickname() {
        return nickname;
    }

    public static String getPassword() {
        return password;
    }

    @FXML
    void login(ActionEvent event) {
        this.nickname = nick.getCharacters().toString();
        this.password = pass.getCharacters().toString();
        if (nickname != null && password != null && !nickname.isEmpty() && !password.isEmpty()) {
            try {
                client.udpSocket.setSoTimeout(1000);
                client.udpSocket.send(client.createRequest("login", null, nickname + " " + server.DataBaseConnection.encryptString(password)));
                byte[] resp = new byte[8192];
                DatagramPacket responsePacket = new DatagramPacket(resp, resp.length);

                client.udpSocket.receive(responsePacket);

                try (ByteArrayInputStream bais = new ByteArrayInputStream(resp);
                     ObjectInputStream ois = new ObjectInputStream(bais)) {
                    Response response = (Response) ois.readObject();
                    String output = new String(client.decodeResponse("login", response));
                    if (output.equals("~~~~~ Successfully logged in! ~~~~~~")) {
                        client.setUsername(nickname);
                        Window stageP = nick.getScene().getWindow();
                        stageP.hide();
                        Stage stage = new Stage();
                        stage.setTitle(Login.currentResource.getString("main"));
                        FXMLLoader loader = new FXMLLoader();
                        loader.setResources(Login.currentResource);
                        loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUIDay.fxml"));
                        Login.loadScene(stage, loader);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText(Login.currentResource.getString("warning"));
                        alert.setTitle(Login.currentResource.getString("login"));
                        alert.setContentText(output);
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(Login.currentResource.getString("main"));
                    alert.setHeaderText(Login.currentResource.getString("warning"));
                    alert.setContentText(Login.currentResource.getString("cantLoadMainUI") + e.getMessage());
                    alert.showAndWait();
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(Login.currentResource.getString("warning"));
                alert.setTitle(Login.currentResource.getString("serverConnection"));
                alert.setContentText(Login.currentResource.getString("disconnected"));
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Login.currentResource.getString("login"));
            alert.setHeaderText(Login.currentResource.getString("warning"));
            alert.setContentText(Login.currentResource.getString("nicknameAndPasswordVoid"));
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
    void registerWindow(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        stage.setTitle(Login.currentResource.getString("register"));
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(Login.currentResource);
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/register.fxml"));
        Login.loadScene(stage, loader);
    }

    @FXML
    void language1(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        stage.setTitle(Login.currentResource.getString("login"));
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("ru", "Ru")));
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/login.fxml"));
        Login.loadScene(stage, loader);
    }

    @FXML
    void language2(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        stage.setTitle(Login.currentResource.getString("login"));
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("en", "Ca")));
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/login.fxml"));
        Login.loadScene(stage, loader);
    }

    @FXML
    void language3(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        stage.setTitle(Login.currentResource.getString("login"));
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("ch", "Ch")));
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/login.fxml"));
        Login.loadScene(stage, loader);
    }

    @FXML
    void language4(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        stage.setTitle(Login.currentResource.getString("login"));
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("pl", "Pl")));
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/login.fxml"));
        Login.loadScene(stage, loader);
    }

}