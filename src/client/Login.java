package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {


    @Override
    public void start(Stage stage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        loadScene(stage, loader);
    }

    static void loadScene(Stage stage, FXMLLoader loader) {
        Parent root;

        try {
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
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String args[]){
        launch(args);
    }
}