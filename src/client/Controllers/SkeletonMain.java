package client.Controllers;

import client.BackTable;
import client.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
import java.text.DateFormat;
import java.util.*;

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

    @FXML
    private Label date;

    @FXML
    private Canvas canvasForSelection;

    @FXML
    private TableView table;

    @FXML
    private AnchorPane pane;

    private GraphicsContext gc;

    private GraphicsContext gc2;

    private Human selected;

    Date currentDate;
    DateFormat df;

    Timer timer;

    private int size = 0;
    private int x = 0;
    private int y = 0;
    private double dX = 0;
    private double dY = 0;
    private boolean drag = false;

    private void init() {
        collectionInfo.setAlignment(Pos.CENTER_LEFT);
        nickname.setAlignment(Pos.CENTER_RIGHT);
        lastCommand.setAlignment(Pos.CENTER_LEFT);
        lastHumanName.setAlignment(Pos.CENTER_LEFT);
        nickname.setText(SkeletonLogin.getNickname());
        collectionInfo.setText(currentResource.getString("collectionInfo"));
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        timer = new Timer();
        gc = canvas.getGraphicsContext2D();
        canvasForSelection.setOpacity(0.5);
        gc2 = canvasForSelection.getGraphicsContext2D();
        timer.schedule(new BackTable(gc, gc2, table), 0, 3000);
        commandsList.getItems().addAll(
                Login.currentResource.getString("add"),
                Login.currentResource.getString("remove"),
                Login.currentResource.getString("remove_greater"),
                Login.currentResource.getString("remove_lower"),
                Login.currentResource.getString("add_if_min"),
                Login.currentResource.getString("update"),
                Login.currentResource.getString("filter"));
        labelSize.setText("0.00");
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            labelSize.setText(String.format("%.2f", newValue));
            size = newValue.intValue();
        });
        currentDate = new Date();
        df = DateFormat.getDateInstance(DateFormat.SHORT, currentResource.getLocale());
        date.setText(df.format(currentDate));
        table.setDisable(true);
        table.setOpacity(0);
        pane.setCursor(Cursor.MOVE);
    }


    @FXML
    void getHuman(MouseEvent event) {

        gc2.clearRect(0, 0, 351, 380);

        BackTable.storageOld.forEach(h -> {

            if((Math.abs( -h.getY() + ((int) event.getX())) < 35*h.getSize()) && Math.abs( -h.getX() + ((int) event.getY())) < 35*h.getSize()){

                gc2.beginPath();
                gc2.save();
                gc2.setFill(Color.BLACK);
                gc2.setStroke(Color.BLACK);
                gc2.scale(0.4 * h.getSize(),0.4 * h.getSize());
                gc2.strokeRect(h.getX() + 3, h.getY(), 200, 190);
                gc2.restore();
                gc2.closePath();

                showHumanOnPanel(h);

            }
        });

    }

    private void clearRemoteControl() {
        humanName.clear();
        humanAge.clear();
        skillName.clear();
        skillInfo.clear();
        commandsList.setValue(null);
        slider.setValue(slider.getMin());
        labelSize.setText("0.00");
        xCoordinate.clear();
        yCoordinate.clear();
        BackTable.cleanCanvas();
        selected = null;
        currentDate = new Date();
        df = DateFormat.getDateInstance(DateFormat.SHORT, currentResource.getLocale());
        date.setText(df.format(currentDate));
        gc2.clearRect(0, 0, 351, 380);
    }

    @FXML
    void drag(MouseEvent event) {
        pane.setCursor(Cursor.HAND);
        if (selected != null && event.isDragDetect()) {

            dX = event.getX();
            dY = event.getY();
            pane.setCursor(Cursor.MOVE);
            drag = true;

        }

    }

    @FXML
    void mouseReleased(MouseEvent event) {
        pane.setCursor(Cursor.DEFAULT);
        if (selected != null) {
            if ((Math.abs(event.getX() - dX) > 25 || Math.abs(event.getY() - dY) > 25) && drag) {
                BackTable.moveHuman(selected, dX, dY, event.getX(), event.getY(), selected.getSize(), (int) slider.getValue());
                xCoordinate.setText(String.valueOf((int) event.getX()));
                yCoordinate.setText(String.valueOf((int) event.getY()));
                BackTable.newUselessWindow = true;
            }
        }
        drag = false;
    }


    @FXML
    void scroll(ScrollEvent event) {
        if (selected != null) {
            double zoomFactor = -1;
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 2.0 + zoomFactor;
            }
            if (slider.getValue() + zoomFactor == 0){
                zoomFactor = 0;
            }
            slider.setValue(slider.getValue() + zoomFactor);
            BackTable.newUselessWindow = true;
        }
    }

    @FXML
    void clear(ActionEvent event) {
        clearRemoteControl();
        table.getItems().clear();
        BackTable.storage.stream().forEach(x -> table.getItems().add(x));
        table.refresh();
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
        BackTable.cleanCanvas();
        Stage stageP = (Stage) nickname.getScene().getWindow();
        stageP.close();
        Stage stage = new Stage();
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
        table.setDisable(false);
        table.setOpacity(1);
    }

    @FXML
    void getHumanFromTable(MouseEvent event) {
        if (!table.isDisabled()){
            Human h = (Human) table.getSelectionModel().getSelectedItem();
            if (h != null) {
                showHumanOnPanel(h);
            }
        }
    }

    @FXML
    void hideCanvas(ActionEvent event) {
        table.setDisable(true);
        table.setOpacity(0);
    }

    @FXML
    void submit(ActionEvent event) {

        boolean command = false;
        boolean numberRight = true;

        try {
            String gson = "";

            String commandInEnglish2 = "";
            String key2;
            Enumeration<String> keys2 = currentResource.getKeys();
            while (keys2.hasMoreElements()){
                key2 = keys2.nextElement();
                String commandTemp = (commandsList.getValue() != null) ? commandsList.getValue() : "";
                if (commandTemp.equals(currentResource.getString(key2))){
                    commandInEnglish2 = key2;
                }
            }


            if (commandInEnglish2.equals("filter")){

                if (!table.isDisabled()){
                    table.getItems().clear();
                    String filterName = (humanName.getText() != null) ? humanName.getText() : "";
                    String filterAge = (humanAge.getText() != null) ? humanAge.getText() : "";
                    String filterXLocation = (xCoordinate.getText() != null) ? xCoordinate.getText() : "";
                    String filterYLocation = (yCoordinate.getText() != null) ? yCoordinate.getText() : "";
                    String filterSize = (size == 0) ? "" : String.valueOf(size);
                    String filterSkillName = (skillName.getText() != null) ? skillName.getText() : "";
                    String filterSkillInfo = (skillInfo.getText() != null) ? skillInfo.getText() : "";
                    BackTable.storageOld.stream().filter(y ->
                               y.getName().contains(filterName)
                            && String.valueOf(y.getAge()).contains(filterAge)
                            && String.valueOf(y.getX()).contains(filterXLocation)
                            && String.valueOf(y.getY()).contains(filterYLocation)
                            && String.valueOf(y.getSize()).contains(filterSize)
                            && y.getSkills().get(0).getName().contains(filterSkillName)
                            && y.getSkills().get(0).getAction().contains(filterSkillInfo))
                            .forEach( x -> table.getItems().add(x));
                    table.refresh();
                    throw new Exception();

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(Login.currentResource.getString("command"));
                    alert.setHeaderText(Login.currentResource.getString("warning"));
                    alert.setContentText(currentResource.getString("showTable"));
                    alert.showAndWait();
                    throw new Exception();

                }
            }

            if (humanName.getText() == null || humanAge.getText() == null || humanName.getText().isEmpty() || humanAge.getText().isEmpty() || commandsList.getValue() == null || size == 0){
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
                    gson = "{\"name\":\"" + humanName.getText() +"\", \"age\":" + humanAge.getText() + ", \"size\":" + size + ", \"x\": " + x + ", \"y\": " + y + "%s"+ "}";
                    command = true;
                } else if (!(skillName.getText() == null || skillName.getText().isEmpty()) && (skillInfo.getText() == null || skillInfo.getText().isEmpty())){
                    gson = "{\"name\":\"" + humanName.getText() +"\", \"age\":" + humanAge.getText() + ", \"size\":" + size + ", \"x\": " + x + ", \"y\": "
                            + y + ", \"skill\": {" + "\"name\": \"" + skillName.getText() + "\"}" + "%s" +"}";
                    command = true;
                } else {
                    gson = "{\"name\":\"" + humanName.getText() + "\", \"age\":" + humanAge.getText() + ", \"size\":" + size + ", \"x\": " + x + ", \"y\": "
                            + y + ", \"skill\": {" + "\"name\": \"" + skillName.getText() + "\","
                            + "\"info\": \"" + skillInfo.getText() + "\"}"  + "%s"+ "}";
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

                    if (commandInEnglish.equals("update") || commandInEnglish.equals("remove")){

                        if (selected != null){
                            String prepareId = ", \"id\": \"" + selected.getId() + "\"";
                            gson = String.format(gson, prepareId);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle(Login.currentResource.getString("command"));
                            alert.setHeaderText(Login.currentResource.getString("message"));
                            alert.setContentText(currentResource.getString("notSelectHuman"));
                            alert.showAndWait();
                            throw new Exception();
                        }
                    } else {
                        gson = String.format(gson, "");
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
                        alert.setHeaderText(Login.currentResource.getString("warning"));
                        alert.setTitle(Login.currentResource.getString("command"));
                        alert.setContentText(Login.getLocaleMessageFromServer(output));
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
        } catch (Exception e){}

    }

    private void showHumanOnPanel(Human selected) {
        this.selected = selected;
        humanName.setText(selected.getName());
        humanAge.setText(String.valueOf(selected.getAge()));
        xCoordinate.setText(String.valueOf(selected.getX()));
        yCoordinate.setText(String.valueOf(selected.getY()));
        slider.setValue(selected.getSize());
        if (selected.getSkills().iterator().hasNext()) {
            Skill skill = selected.getSkills().iterator().next();
            if (!skill.getName().toLowerCase().equals("null")) {
                skillName.setText(skill.getName());
                if (!skill.getAction().toLowerCase().equals("null")) {
                    skillInfo.setText(skill.getAction());
                } else {skillInfo.clear();}
            } else {skillName.clear();}
        } else {skillInfo.clear();skillName.clear();}

        currentDate = new Date(selected.getDateTime().toEpochSecond() * 1000);
        df = DateFormat.getDateInstance(DateFormat.SHORT, currentResource.getLocale());
        date.setText(df.format(currentDate));
    }

    @FXML
    void language1(ActionEvent event) {
        timer.cancel();
        BackTable.storageOld.clear();
        Stage stageToClose = (Stage) nickname.getScene().getWindow();
        stageToClose.close();
        BackTable.newUselessWindow = true;
        Stage stage = new Stage();
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
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(currentResource);
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/login.fxml"));
        loadScene(stage, loader);
    }

}
