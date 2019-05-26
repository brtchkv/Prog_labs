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
        gc.strokeArc(15, 100, 20, 40, 80, 280, ArcType.OPEN); // ������� ������ ���
        gc.strokeArc(165, 100, 20, 42, 80, 360, ArcType.OPEN); // ������� ������� ���
        gc.setFill(Color.YELLOW);
        gc.fillOval(25,50,150,150);    // For face
        gc.fillOval(15, 100,20, 40 );  // ������� ������ ���
        gc.fillOval(165, 100,20, 42 ); // ������� ������� ���
        gc.strokeOval(25,50,150,150);  // ������� ����
        gc.setFill(Color.BLACK);
        gc.fillOval(55,95,15,25);      // Left Eye
        gc.fillOval(125,95,15,25);     // Right Eye
        gc.setFill(Color.SADDLEBROWN);
        gc.strokeOval(0, 40, 195, 62); // ������� ����� �����
        gc.fillOval(0, 40, 195, 62);   // ���� �����
        gc.setFill(Color.BLACK);
        gc.strokeArc(68, 50, 60, 30, 180, 180, ArcType.OPEN); // ������� ������� �����
        gc.strokeArc(68, 15, 60, 100, 0, 180, ArcType.OPEN); // ������ �����
        gc.setFill(Color.SADDLEBROWN);
        gc.fillArc(68, 15, 60, 100, 0, 180, ArcType.OPEN); // ������ �����
        gc.setFill(Color.BLACK);
        double x[] = {100,90,100,100};
        double y[] = {118,137,137,118};
        gc.fillPolygon(x, y, 4);            // Nose
        gc.arc(100,154,36,22,0,-180);  // ��� ���

//        gc.strokeLine(35,94,35,115);              // ����� ��� ��������
//        gc.setFill(Color.CORAL);
//        gc.fillOval(25,115,20,20);                // ������
//        gc.strokeLine(82,100,82,125);             // ����� ��� ��������
//        gc.setFill(Color.CORAL);
//        gc.fillOval(72,125,20,20);                // ������
//        gc.strokeLine(133,148,144,159);           // Smile arc2

        gc.setFill(Color.web("#e35d6a"));           // ���� ���
        gc.fill();                                  // ������� ���
        gc.setFill(Color.WHITE);
        gc.fillRect(64,154,73,8);     // ����
        gc.strokeArc(62.5,131,75,45,0,-180,ArcType.ROUND); // ������� ���
        root.getChildren().add(canvas);
        stage.setTitle("Face");
        stage.setScene(scene);
        stage.show();

    }
}
