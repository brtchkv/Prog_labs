package shared;
import client.Login;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class TestMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        initUI(stage);
    }

    private void initUI(Stage stage) {

        Pane root = new Pane();

        Scene scene = new Scene(root);

        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double size = 1.2;
        int xC = 100;
        int yC = 100;
        gc.beginPath();
        gc.save();
        gc.strokeArc((20 + xC), (85 + yC), 20 * size, 40 * size, 70, 280, ArcType.OPEN); // обводка левого уха
        gc.strokeArc(170 + xC, 85 + yC, 20, 42, 80, 360, ArcType.OPEN); // обводка правого уха
        gc.setFill(Color.YELLOW);
        gc.fillOval((30 + xC) - 150 * size/2,35 + yC - 150 * size/2,150 * size,150 * size);    // For face
        gc.fillOval(20 + xC - 20 * size/2, 85 + yC - 40 * size/2,20 * size, 40 * size);   // заливка левого уха
        gc.fillOval(170 + xC, 85 + yC,20, 42 );  // заливка правого уха
        gc.strokeOval(30 + xC,35 + yC,150,150);  // обводка лица
        gc.setFill(Color.BLACK);
        gc.fillOval(60 + xC,80 + yC,15,25);      // Left Eye
        gc.fillOval(130 + xC,80 + yC,15,25);     // Right Eye
        gc.setFill(Color.SADDLEBROWN);
        gc.strokeOval(5 + xC, 25 + yC, 195, 62); // обводка полей шл€пы
        gc.fillOval(5 + xC, 25 + yC, 195, 62);   // пол€ шл€пы
        gc.setFill(Color.BLACK);
        gc.strokeArc(73 + xC, 35 + yC, 60, 30, 180, 180, ArcType.OPEN); // обводка выступа шл€пы
        gc.strokeArc(73 + xC, 0 + yC, 60, 100, 0, 180, ArcType.OPEN); // выступ шл€пы
        gc.setFill(Color.SADDLEBROWN);
        gc.fillArc(73 + xC, 0 + yC, 60, 100, 0, 180, ArcType.OPEN); // выступ шл€пы
        gc.setFill(Color.BLACK);
        double x[] = {104 + (double) xC,94 + (double) xC,104 + (double) xC,104 + (double) xC};
        double y[] = {105 + (double) yC,124 + (double) yC,124 + (double) yC,105 + (double) yC};
        gc.fillPolygon(x , y, 4);           // Nose
        gc.arc(105 + xC,139 + yC,37,22,0,-180);  // нЄбо рта

//        gc.strokeLine(133,148,144,159);           // Smile arc2

        gc.setFill(Color.web("#e35d6a"));           // цвет нЄба
        gc.fill();                                  // заливка рта
        gc.setFill(Color.WHITE);
        gc.fillRect(68.5 + xC,138 + yC,73,8);   // зубы
        gc.strokeArc(67.5 + xC,116 + yC,75,45,0,-180,ArcType.ROUND); // обводка рта
        gc.restore();
        gc.closePath();
        root.getChildren().add(canvas);
        stage.setTitle("Face");
        stage.setScene(scene);
        stage.show();

    }
}