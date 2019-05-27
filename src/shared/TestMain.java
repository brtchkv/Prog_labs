package shared;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class TestMain extends Application {

    Canvas canvas = new Canvas(1000, 800);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Color customnColor;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initUI(stage);
    }

    private void initUI(Stage stage) {

        Pane root = new Pane();

        Human h = new Human("Alice", 12);
        h.setSize(1);
        h.setY(10);
        h.setX(200);

        Human h2 = new Human("Alice", 12);
        h2.setSize(4);
        h2.setY(100);
        h2.setX(500);

        Scene scene = new Scene(root);
        root.getChildren().add(canvas);
        stage.setTitle("Face");
        stage.setScene(scene);
        stage.show();

    }

}
