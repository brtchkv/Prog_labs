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
    Color customnColor = Color.color(Math.random(), Math.random(), Math.random());

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
        h.setSize(4);
        h.setY(400);
        h.setX(500);

        Scene scene = new Scene(root);
        drawHuman(h);
        root.getChildren().add(canvas);
        stage.setTitle("Face");
        stage.setScene(scene);
        stage.show();

    }

    public void drawHuman(Human human){

        gc.beginPath();
        gc.save();
        gc.scale(0.3*human.getSize(),0.3*human.getSize());
        gc.strokeArc(20, 85, 20, 40, 80, 280, ArcType.OPEN); // обводка левого уха
        gc.strokeArc(170, 85, 20, 42, 80, 360, ArcType.OPEN); // обводка правого уха
        gc.setFill(customnColor);
        gc.fillOval(30,35,150,150);    // For face
        gc.fillOval(20, 85,20, 40 );   // заливка левого уха
        gc.fillOval(170, 85,20, 42 );  // заливка правого уха
        gc.strokeOval(30,35,150,150);  // обводка лица
        gc.setFill(Color.BLACK);
        gc.fillOval(60,80,15,25);      // Left Eye
        gc.fillOval(130,80,15,25);     // Right Eye
        gc.setFill(Color.SADDLEBROWN);
        gc.strokeOval(5, 25, 195, 62); // обводка полей шл€пы
        gc.fillOval(5, 25, 195, 62);   // пол€ шл€пы
        gc.setFill(Color.BLACK);
        gc.strokeArc(73, 35, 60, 30, 180, 180, ArcType.OPEN); // обводка выступа шл€пы
        gc.strokeArc(73, 0, 60, 100, 0, 180, ArcType.OPEN); // выступ шл€пы
        gc.setFill(Color.SADDLEBROWN);
        gc.fillArc(73, 0, 60, 100, 0, 180, ArcType.OPEN); // выступ шл€пы
        gc.setFill(Color.BLACK);
        double x[] = {104,94,104,104};
        double y[] = {105,124,124,105};
        gc.fillPolygon(x, y, 4);            // Nose
        gc.arc(105,139,36,22,0,-180);  // нЄбо рта

//        gc.strokeLine(133,148,144,159);           // Smile arc2

        gc.setFill(Color.web("#e35d6a"));           // цвет нЄба
        gc.fill();                                  // заливка рта
        gc.setFill(Color.WHITE);
        gc.fillRect(69,138,73,8);     // зубы
        gc.strokeArc(67.5,116,75,45,0,-180,ArcType.ROUND); // обводка рта
    }
}
