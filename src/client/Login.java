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

    @Override
    public void start(Stage stage){
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(currentResource);
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/login.fxml"));
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

    public static void main(String args[]){
        launch(args);
    }
}