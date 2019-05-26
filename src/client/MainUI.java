package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class MainUI extends Application {
    @Override
    public void start(Stage stage){
        FXMLLoader loader = new FXMLLoader();
        loader.setResources(Login.currentResource);
        loader.setLocation(getClass().getClassLoader().getResource("client/UI/MainUIDay.fxml"));
        Login.loadScene(stage, loader);
        stage.setResizable(false);
    }

    public static void main(String args[]){
        launch(args);
    }
}
