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
        gc.strokeArc(20, 85, 20, 40, 80, 280, ArcType.OPEN); // ������� ������ ���
        gc.strokeArc(170, 85, 20, 42, 80, 360, ArcType.OPEN); // ������� ������� ���
        gc.setFill(customnColor);
        gc.fillOval(30,35,150,150);    // For face
        gc.fillOval(20, 85,20, 40 );   // ������� ������ ���
        gc.fillOval(170, 85,20, 42 );  // ������� ������� ���
        gc.strokeOval(30,35,150,150);  // ������� ����
        gc.setFill(Color.BLACK);
        gc.fillOval(60,80,15,25);      // Left Eye
        gc.fillOval(130,80,15,25);     // Right Eye
        gc.setFill(Color.SADDLEBROWN);
        gc.strokeOval(5, 25, 195, 62); // ������� ����� �����
        gc.fillOval(5, 25, 195, 62);   // ���� �����
        gc.setFill(Color.BLACK);
        gc.strokeArc(73, 35, 60, 30, 180, 180, ArcType.OPEN); // ������� ������� �����
        gc.strokeArc(73, 0, 60, 100, 0, 180, ArcType.OPEN); // ������ �����
        gc.setFill(Color.SADDLEBROWN);
        gc.fillArc(73, 0, 60, 100, 0, 180, ArcType.OPEN); // ������ �����
        gc.setFill(Color.BLACK);
        double x[] = {104,94,104,104};
        double y[] = {105,124,124,105};
        gc.fillPolygon(x, y, 4);            // Nose
        gc.arc(105,139,36,22,0,-180);  // ��� ���

//        gc.strokeLine(133,148,144,159);           // Smile arc2

        gc.setFill(Color.web("#e35d6a"));           // ���� ���
        gc.fill();                                  // ������� ���
        gc.setFill(Color.WHITE);
        gc.fillRect(69,138,73,8);     // ����
        gc.strokeArc(67.5,116,75,45,0,-180,ArcType.ROUND); // ������� ���
    }
}
