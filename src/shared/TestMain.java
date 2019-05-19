package shared;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        TableView tableView = new TableView();

        TableColumn firstNameCol = new TableColumn("Name");
        TableColumn age = new TableColumn("Age");
        TableColumn u = new TableColumn("Username");
        tableView.getColumns().addAll(firstNameCol, age, u);

        VBox vbox = new VBox(tableView);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
