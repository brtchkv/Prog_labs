package client.Controllers;

import client.BackTable;
import client.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import shared.Human;
import shared.Response;
import shared.Skill;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.URL;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;

import static client.Login.currentResource;
import static client.Login.loadScene;


public class SkeletonMain implements Initializable {

    public static boolean dark = false;

    @FXML
    private Label collectionInfo;

    @FXML
    private Label nickname;

    @FXML
    private ComboBox<String> commandsList;

    @FXML
    private TextField humanName;

    @FXML
    private DatePicker date;

    @FXML
    private TextField humanAge;

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

    @FXML
    private Slider slider;

    @FXML
    private Label labelSize;

    @FXML
    private Canvas canvas;

    private GraphicsContext gc;

    private Human selected;

    Timer timer;

    private int size = 1;
    private int x = 0;
    private int y = 0;

    private void init() {
        collectionInfo.setAlignment(Pos.CENTER_LEFT);
        nickname.setAlignment(Pos.CENTER_RIGHT);
        lastCommand.setAlignment(Pos.CENTER_RIGHT);
        lastHumanName.setAlignment(Pos.CENTER_RIGHT);
        nickname.setText(SkeletonLogin.getNickname());
        collectionInfo.setText(currentResource.getString("collectionInfo"));
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        timer = new Timer();
        gc = canvas.getGraphicsContext2D();
        timer.schedule(new BackTable(gc), 0, 3000);
        commandsList.getItems().addAll(
                Login.currentResource.getString("add"),
                Login.currentResource.getString("remove"),
                Login.currentResource.getString("remove_greater"),
                Login.currentResource.getString("remove_lower"),
                Login.currentResource.getString("add_if_min"));
        labelSize.setText("1.00");
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelSize.setText(String.format("%.2f", newValue));
            size = newValue.intValue();
        });
        date.setPromptText(LocalDate.now().toString());
    }


    @FXML
    void getHuman(MouseEvent event) {
        BackTable.cleanCanvas();
        BackTable.storage.forEach(x -> {
            if((Math.abs(x.getY() - ((int) event.getY())) < 30*0.7*x.getSize()) && Math.abs( x.getX() - ((int) event.getX())) < 30*0.7*x.getSize()){
                selected = x;
                humanName.setText(x.getName());
                humanAge.setText(String.valueOf(x.getAge()));
                if (x.getSkills().iterator().hasNext()) {
                    Skill skill = x.getSkills().iterator().next();
                    if (!skill.getName().toLowerCase().equals("null")) {
                        skillName.setText(skill.getName());
                        if (!skill.getAction().toLowerCase().equals("null")) {
                            skillInfo.setText(skill.getAction());
                        } else {skillInfo.clear();}
                    } else {skillName.clear();}
                } else {skillInfo.clear();skillName.clear();}
                gc.beginPath();
                gc.save();
                gc.scale(0.4 * x.getSize(),0.4 * x.getSize());
                gc.strokeRect(x.getX(), x.getY(), 200, 200);
                gc.restore();
                gc.closePath();
            }
        });
    }

    @FXML
    void clear(ActionEvent event) {
        humanName.clear();
        humanAge.clear();
        skillName.clear();
        skillInfo.clear();
        commandsList.setValue(null);
        slider.setValue(slider.getMin());
        labelSize.setText("1.00");
        xCoordinate.clear();
        yCoordinate.clear();
        BackTable.cleanCanvas();
        date.setValue(null);
    }

    @FXML
    void day(ActionEvent event) {
        dark = false;
        timer.cancel();
        BackTable.storageOld.clear();
        BackTable.newUselessWindow = true;
        Stage stageP = (Stage) nickname.getScene().getWindow();
        stageP.close();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(Login.currentResource);
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUIDay.fxml"));
        Login.loadScene(stage, loader);
        dark = false;
    }

    @FXML
    void night(ActionEvent event) {
        dark = true;
        timer.cancel();
        BackTable.newUselessWindow = true;
        BackTable.storageOld.clear();
        Stage stageP = (Stage) nickname.getScene().getWindow();
        stageP.close();
        Stage stage = new Stage();
        stage.setTitle(Login.currentResource.getString("main"));
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(Login.currentResource);
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUINight.fxml"));
        Login.loadScene(stage, loader);
        dark = true;
    }

    @FXML
    void fun(ActionEvent event) {
        ImageView imageView = new ImageView();
        ButtonType sorry = new ButtonType(currentResource.getString("sorry"));
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(currentResource.getString("why"));
        alert.setHeaderText(Login.currentResource.getString("warning"));
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(sorry);
        File file = new File("/Users/ivan/OneDrive - ITMO UNIVERSITY/Прога/6/Lab/src/client/UI/cat.jpg");
        Image image = new Image(file.toURI().toString(), 400, 400, true, true);
        imageView.setImage(image);
        alert.setGraphic(imageView);
        alert.showAndWait();
    }

    @FXML
    void showHelp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Login.currentResource.getString("help"));
        alert.setContentText(Login.currentResource.getString("helpText"));
        alert.showAndWait();
    }

    @FXML
    void showTable(ActionEvent event) {
        VBox vbox;
        if (BackTable.tableView != null) {
            vbox = new VBox(BackTable.tableView);
        } else {
            TableView tb = new TableView();
            TableColumn firstNameCol = new TableColumn(currentResource.getString("login"));
            TableColumn age = new TableColumn(currentResource.getString("age"));
            TableColumn u = new TableColumn(currentResource.getString("username"));
            tb.getColumns().addAll(firstNameCol, age, u);
            vbox = new VBox(tb);
        }

        vbox.setSpacing(5);
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setTitle(currentResource.getString("objects"));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void submit(ActionEvent event) {

        boolean command = false;
        boolean numberRight = true;

        try {

            String gson = "";
            if (humanName.getText() == null || humanAge.getText() == null || humanName.getText().isEmpty() || humanAge.getText().isEmpty() || commandsList.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(Login.currentResource.getString("command"));
                alert.setHeaderText(Login.currentResource.getString("warning"));
                alert.setContentText(currentResource.getString("sheerVoid"));
                alert.showAndWait();
            } else {
                try {
                    if (xCoordinate.getText() != null && !xCoordinate.getText().isEmpty()){
                        x = Integer.parseInt(xCoordinate.getText());
                    }
                    if (yCoordinate.getText() != null && !yCoordinate.getText().isEmpty()){
                        y = Integer.parseInt(yCoordinate.getText());
                    }

                    Integer.parseInt(humanAge.getText());
                }catch (NumberFormatException e){
                    numberRight = false;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(Login.currentResource.getString("command"));
                    alert.setHeaderText(Login.currentResource.getString("warning"));
                    alert.setContentText(currentResource.getString("notANumber"));
                    alert.showAndWait();
                }

                if ((skillName.getText() == null || skillName.getText().isEmpty()) && skillInfo.getText()!= null && !skillInfo.getText().isEmpty()) {
                    command = false;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(Login.currentResource.getString("warning"));
                    alert.setTitle(Login.currentResource.getString("command"));
                    alert.setContentText(currentResource.getString("skillNameMissing"));
                    alert.showAndWait();
                } else if ((skillName.getText() == null || skillName.getText().isEmpty()) && (skillInfo.getText() == null || skillInfo.getText().isEmpty())){
                    gson = "{\"name\":\"" + humanName.getText() +"\", \"age\":" + humanAge.getText() + ", \"size\":" + size + ", \"x\": " + x + ", \"y\": " + y +"}";
                    command = true;
                } else if (!(skillName.getText() == null || skillName.getText().isEmpty()) && (skillInfo.getText() == null || skillInfo.getText().isEmpty())){
                    gson = "{\"name\":\"" + humanName.getText() +"\", \"age\":" + humanAge.getText() + ", \"size\":" + size + ", \"x\": " + x + ", \"y\": "
                            + y + ", \"skill\": {" + "\"name\": \"" + skillName.getText() + "\"}" + "}";
                    command = true;
                } else {
                    gson = "{\"name\":\"" + humanName.getText() + "\", \"age\":" + humanAge.getText() + ", \"size\":" + size + ", \"x\": " + x + ", \"y\": "
                            + y + ", \"skill\": {" + "\"name\": \"" + skillName.getText() + "\","
                            + "\"info\": \"" + skillInfo.getText() + "\"}"  + "}";
                    command = true;
                }

                if (command && numberRight) {

                    lastCommand.setText(commandsList.getValue());
                    lastHumanName.setText(humanName.getText());

                    String commandInEnglish = "";
                    String key;
                    Enumeration<String> keys = currentResource.getKeys();
                    while (keys.hasMoreElements()){
                        key = keys.nextElement();
                        String commandTemp = commandsList.getValue();
                        if (commandTemp.equals(currentResource.getString(key))){
                            commandInEnglish = key;
                        }
                    }

                    SkeletonLogin.client.udpSocket.send(SkeletonLogin.client.createRequest(commandInEnglish, gson, SkeletonLogin.getNickname() + " " + server.DataBaseConnection.encryptString(SkeletonLogin.getPassword())));
                    byte[] resp = new byte[8192];
                    DatagramPacket responsePacket = new DatagramPacket(resp, resp.length);
                    SkeletonLogin.client.udpSocket.receive(responsePacket);

                    try (ByteArrayInputStream bais = new ByteArrayInputStream(resp);
                         ObjectInputStream ois = new ObjectInputStream(bais)) {
                        Response response = (Response) ois.readObject();
                        String output = new String(SkeletonLogin.client.decodeResponse(commandsList.getValue(), response));
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(Login.currentResource.getString("command"));
                        alert.setContentText(output);
                        alert.showAndWait();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Login.currentResource.getString("serverConnection"));
            alert.setContentText(Login.currentResource.getString("disconnected"));
            alert.setHeaderText(Login.currentResource.getString("warning"));
            alert.showAndWait();
        }

    }

    @FXML
    void language1(ActionEvent event) {
        timer.cancel();
        BackTable.storageOld.clear();
        Stage stageToClose = (Stage) nickname.getScene().getWindow();
        stageToClose.close();
        BackTable.newUselessWindow = true;
        Stage stage = new Stage();
        stage.setTitle(Login.currentResource.getString("main"));
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("ru", "Ru")));
        if (dark) {
            loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUINight.fxml"));
        } else {
            loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUIDay.fxml"));
        }
        Login.loadScene(stage, loader);
    }

    @FXML
    void language2(ActionEvent event) {
        timer.cancel();
        BackTable.storageOld.clear();
        Window stageP = nickname.getScene().getWindow();
        stageP.hide();
        BackTable.newUselessWindow = true;
        Stage stage = new Stage();
        stage.setTitle(Login.currentResource.getString("main"));
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("en", "Ca")));
        if (dark) {
            loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUINight.fxml"));
        } else {
            loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUIDay.fxml"));
        }
        Login.loadScene(stage, loader);
    }

    @FXML
    void language3(ActionEvent event) {
        timer.cancel();
        BackTable.storageOld.clear();
        Stage stageToClose = (Stage) nickname.getScene().getWindow();
        stageToClose.close();
        BackTable.newUselessWindow = true;
        Stage stage = new Stage();
        stage.setTitle(Login.currentResource.getString("main"));
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("ch", "Ch")));
        if (dark) {
            loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUINight.fxml"));
        } else {
            loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUIDay.fxml"));
        }
        Login.loadScene(stage, loader);
    }

    @FXML
    void language4(ActionEvent event) {
        timer.cancel();
        BackTable.storageOld.clear();
        Stage stageToClose = (Stage) nickname.getScene().getWindow();
        stageToClose.close();
        BackTable.newUselessWindow = true;
        Stage stage = new Stage();
        stage.setTitle(Login.currentResource.getString("main"));
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(ResourceBundle.getBundle("client.Localisation.MyResources",
                new Locale("pl", "Pl")));
        if (dark) {
            loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUINight.fxml"));
        } else {
            loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUIDay.fxml"));
        }
        Login.loadScene(stage, loader);
    }

    @FXML
    void logOut(ActionEvent event) {
        timer.cancel();
        BackTable.storageOld.clear();
        Stage stageToClose = (Stage) nickname.getScene().getWindow();
        stageToClose.close();
        BackTable.newUselessWindow = true;
        Stage stage = new Stage();
        stage.setTitle(Login.currentResource.getString("login"));
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(currentResource);
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/login.fxml"));
        loadScene(stage, loader);
    }

}