package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import shared.Response;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;


public class SkeletonMain implements Initializable {
    @FXML
    private Label collectionInfo;

    @FXML
    private Label nickname;

    @FXML
    private ComboBox<?> commandsList;

    @FXML
    private TextField humanName;

    @FXML
    private TextField humanAge;

    @FXML
    private TextField height;

    @FXML
    private TextField width;

    @FXML
    private TextField skillName;

    @FXML
    private TextField skillInfo;

    @FXML
    private TextField xCoordinate;

    @FXML
    private TextField yCoordinate;

    @FXML
    private Label lastCommand;

    @FXML
    private Label lastHumanName;

    Timer timer;



    private void init() {
        collectionInfo.setAlignment(Pos.CENTER_LEFT);
        nickname.setAlignment(Pos.CENTER_RIGHT);
        lastCommand.setAlignment(Pos.CENTER_LEFT);
        lastHumanName.setAlignment(Pos.CENTER_LEFT);
        nickname.setText(SkeletonLogin.getNickname());
        collectionInfo.setText("Collection of Vector type. Contains objects of human type.");
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        timer = new Timer();
        timer.schedule(new BackTable(), 0, 3000);
    }

    @FXML
    void clear(ActionEvent event) {
        humanName.clear();
        humanAge.clear();
        skillName.clear();
        skillInfo.clear();
        commandsList.setValue(null);
    }

    @FXML
    void day(ActionEvent event) {
        timer.cancel();
        Stage stageP = (Stage) nickname.getScene().getWindow();
        stageP.close();
        Stage stage = new Stage();
        stage.setTitle("Main");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainUIDay.fxml"));
        Login.loadScene(stage, loader);
    }

    @FXML
    void night(ActionEvent event) {
        timer.cancel();
        Stage stageP = (Stage) nickname.getScene().getWindow();
        stageP.close();
        Stage stage = new Stage();
        stage.setTitle("Main");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainUINight.fxml"));
        Login.loadScene(stage, loader);

    }

    @FXML
    void fun(ActionEvent event) {
        ImageView imageView = new ImageView();
        ButtonType sorry = new ButtonType("Sorry");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Why?!");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(sorry);
        File file = new File("/Users/ivan/OneDrive - ITMO UNIVERSITY/Прога/6/Lab/src/client/cat.jpg");
        Image image = new Image(file.toURI().toString(), 400, 400, true, true);
        imageView.setImage(image);
        alert.setGraphic(imageView);
        alert.showAndWait();
    }

    @FXML
    void showHelp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setContentText("An application for organising your slavery business.\nCreator: Ivan Bratchikov");
        alert.showAndWait();
    }

    @FXML
    void showTable(ActionEvent event) {
        VBox vbox;
        if (BackTable.tableView != null) {
            vbox = new VBox(BackTable.tableView);
        } else {
            TableView tb = new TableView();
            TableColumn firstNameCol = new TableColumn("Name");
            TableColumn age = new TableColumn("Age");
            TableColumn u = new TableColumn("Username");
            tb.getColumns().addAll(firstNameCol, age, u);
            vbox = new VBox(tb);
        }

        vbox.setSpacing(5);
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setTitle("Objects");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void submit(ActionEvent event) {

        boolean command = false;

        try {
            String gson = "";
            if (humanName.getText() == null || humanAge.getText() == null || humanName.getText().isEmpty() || humanAge.getText().isEmpty() || commandsList.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Command");
                alert.setContentText("The fields command, name and age can't be void.\nMake sure they're set.");
                alert.showAndWait();
            } else {
                if ((skillName.getText() == null || skillName.getText().isEmpty()) && skillInfo.getText()!= null && !skillInfo.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Command");
                    alert.setContentText("You can't provide skill info and don't provide skill name\nTry again!");
                    alert.showAndWait();
                } else if ((skillName.getText() == null || skillName.getText().isEmpty()) && (skillInfo.getText() == null || skillInfo.getText().isEmpty())){
                    gson = "{\"name\":\"" + humanName.getText() +"\", \"age\":" + humanAge.getText() + "}";
                    command = true;
                } else if (!(skillName.getText() == null || skillName.getText().isEmpty()) && (skillInfo.getText() == null || skillInfo.getText().isEmpty())){
                    gson = "{\"name\":\"" + humanName.getText() +"\", \"age\":" + humanAge.getText() + ", \"skill\": {" + "\"name\": \"" + skillName.getText() + "\"}"  +"}";
                    command = true;
                } else {
                    gson = "{\"name\":\"" + humanName.getText() +"\", \"age\":" + humanAge.getText() + ", \"skill\": {" + "\"name\": \"" + skillName.getText() + "\""
                            + "\"info\": \"" + skillInfo.getText() +"\"}"  +"}";
                    command = true;
                }
                if (command) {

                    lastCommand.setText(commandsList.getValue().toString());
                    lastHumanName.setText(humanName.getText());

                    SkeletonLogin.client.udpSocket.send(SkeletonLogin.client.createRequest(commandsList.getValue().toString().replaceAll("\\s+", "_").toLowerCase(), gson, SkeletonLogin.getNickname() + " " + server.DataBaseConnection.encryptString(SkeletonLogin.getPassword())));
                    byte[] resp = new byte[8192];
                    DatagramPacket responsePacket = new DatagramPacket(resp, resp.length);
                    SkeletonLogin.client.udpSocket.receive(responsePacket);

                    try (ByteArrayInputStream bais = new ByteArrayInputStream(resp);
                         ObjectInputStream ois = new ObjectInputStream(bais)) {
                        Response response = (Response) ois.readObject();
                        String output = new String(SkeletonLogin.client.decodeResponse(commandsList.getValue().toString(), response));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Command");
                        alert.setContentText(output);
                        alert.showAndWait();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Server Connection");
            alert.setContentText("Disconnected from the server\nTry again later!");
            alert.showAndWait();
        }

    }

    @FXML
    void language1(ActionEvent event) {

    }

    @FXML
    void language2(ActionEvent event) {

    }

    @FXML
    void language3(ActionEvent event) {

    }

    @FXML
    void language4(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {
        Window stageP = nickname.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Login.loadScene(stage, loader);
    }

}