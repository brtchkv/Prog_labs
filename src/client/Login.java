package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login extends Application {

    public static ResourceBundle currentResource = ResourceBundle.getBundle("client.Localisation.MyResources",
            new Locale("en", "En"));
    //public static Color customColor = Color.color(Math.random(), Math.random(), Math.random());

    @Override
    public void start(Stage stage){
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(currentResource);
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/login.fxml"));
        stage.setTitle(currentResource.getString("login"));
        loadScene(stage, loader);
    }

    public static void loadScene(Stage stage, FXMLLoader loader) {
        Parent root;

        try {
            if (currentResource != loader.getResources()) {
                currentResource = loader.getResources();
            } else if (loader.getResources() == null){
                loader.setResources(currentResource);
            }
            root = loader.load();
        } catch (IOException ioe) {
            System.out.println("Can't load the login.fxml");
            ioe.printStackTrace();
            return;
        }
        stage.setScene(new Scene(root, Color.TRANSPARENT));
        stage.sizeToScene();
        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        if (loader.getLocation().toString().contains("client/UI/MainUINight.fxml")){
            stage.getScene().getStylesheets().add("client/UI/dark.css");
        }
        stage.show();
        stage.setResizable(false);
    }

    public static String getLocaleMessageFromServer(String serverOutput){

        if (serverOutput.contains("You need to register first!")){
            return currentResource.getString("registerFirst");
        } else if (serverOutput.contains("Wrong Password!")){
            return currentResource.getString("wrongPass");
        } else if (serverOutput.contains("Can't log in")){
            return currentResource.getString("cantLogin");
        } else if (serverOutput.contains("User is not authorized")){
            return currentResource.getString("userNotAuth");
        } else if (serverOutput.contains("was successfully added")){
            return String.format(currentResource.getString("added"), serverOutput.split(" ")[2]);
        } else if (serverOutput.contains("Collection already stores this object.")){
            return currentResource.getString("alreadyExists");
        } else if (serverOutput.contains("name isn't the smallest: Can't add to a collection!")){
            return String.format(currentResource.getString("nameIsntTheSmallest"), serverOutput.split("'")[0]);
        } else if (serverOutput.contains("has been deleted :(")){
            return String.format(currentResource.getString("deleted"), serverOutput.split(" ")[2]);
        } else if (serverOutput.contains("Try adding instead")){
            return currentResource.getString("cantDelete");
        } else if (serverOutput.contains("objects. :(")){
            return String.format(currentResource.getString("deletedN"), serverOutput.split(" ")[1]);
        } else if (serverOutput.contains("has been updated")){
            return String.format(currentResource.getString("updated"), serverOutput.split(" ")[1]);
        } else if (serverOutput.contains("Can't update!")){
            return String.format(currentResource.getString("cantUpdate"), serverOutput.split(" ")[1]);
        } else if (serverOutput.contains("Email registration is approved!")){
            return currentResource.getString("emailSent");
        } else if (serverOutput.contains("You've already registered!")){
            return currentResource.getString("userRegistered");
        } else if (serverOutput.contains("Can't register you")){
            return currentResource.getString("cantRegister");
        }

        return "";
    }

    public static void main(String args[]){
        launch(args);
    }
}