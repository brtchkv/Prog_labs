package client;

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
import java.net.SocketTimeoutException;

public class SkeletonLogin {
    private static String nickname;
    private static String password;
    private static boolean auth = false;
    public static Client client;

    public SkeletonLogin() {
        try {
            client = new Client("localhost", 8080);
        }catch (Exception e){}
    }

    public static void setAuth(boolean auth) {
        SkeletonLogin.auth = auth;
    }

    public static boolean isAuth() {
        return auth;
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
                    SkeletonLogin.setAuth(true);
                    Window stageP = nick.getScene().getWindow();
                    stageP.hide();
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainUI.fxml"));
                    Login.loadScene(stage, loader);
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Main UI");
                alert.setContentText("Can't load the main UI!\n" + e.getMessage());
                alert.showAndWait();
            }
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Server Connection");
            alert.setContentText("Disconnected from the server\nTry again later!");
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
    void registerWindow(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        stage.setTitle("Main");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        Login.loadScene(stage, loader);
    }

}