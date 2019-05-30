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
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.util.Duration;

import static client.BackTable.cleanCanvas;
import static client.BackTable.drawHuman;

public class TestMain extends Application{
    public static final double W = 500; // canvas dimensions.
    public static final double H = 500;

    public static final double D = 20;  // diameter.



    public void start(Stage stage){
        final Canvas canvas = new Canvas(W, H);
        DoubleProperty x  = new SimpleDoubleProperty();
        DoubleProperty y  = new SimpleDoubleProperty();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(x, 0),
                        new KeyValue(y, 0)
                ),
                new KeyFrame(Duration.seconds(5),
                        new KeyValue(x, 200),
                        new KeyValue(y, 200)
                )
        );
        timeline.setAutoReverse(false);
        timeline.setCycleCount(Timeline.INDEFINITE);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, 500, 500);
                gc.beginPath();
                gc.save();
                gc.strokeArc(20 + x.doubleValue(), 85 + y.doubleValue(), 20, 40, 70, 280, ArcType.OPEN); // обводка левого уха
                gc.strokeArc(170 + x.doubleValue(), 85 + y.doubleValue(), 20, 42, 80, 360, ArcType.OPEN); // обводка правого уха
                gc.setFill(Color.YELLOW);
                gc.fillOval(30 + x.doubleValue(),35 + y.doubleValue(),150,150);    // For face
                gc.fillOval(20 + x.doubleValue(), 85 + y.doubleValue(),20, 40 );   // заливка левого уха
                gc.fillOval(170 + x.doubleValue(), 85 + y.doubleValue(),20, 42 );  // заливка правого уха
                gc.strokeOval(30 + x.doubleValue(),35 + y.doubleValue(),150,150);  // обводка лица
                gc.setFill(Color.BLACK);
                gc.strokeLine(125 + x.doubleValue(),100 + y.doubleValue(),155 + x.doubleValue(),100 + y.doubleValue());
                gc.strokeLine(50 + x.doubleValue(),100 + y.doubleValue(),80 + x.doubleValue(),100 + y.doubleValue());                gc.setFill(Color.SADDLEBROWN);
                gc.strokeOval(5 + x.doubleValue(), 25 + y.doubleValue(), 195, 62); // обводка полей шл€пы
                gc.fillOval(5 + x.doubleValue(), 25 + y.doubleValue(), 195, 62);   // пол€ шл€пы
                gc.setFill(Color.BLACK);
                gc.strokeArc(73 + x.doubleValue(), 35 + y.doubleValue(), 60, 30, 180, 180, ArcType.OPEN); // обводка выступа шл€пы
                gc.strokeArc(73 + x.doubleValue(), 0 + y.doubleValue(), 60, 100, 0, 180, ArcType.OPEN); // выступ шл€пы
                gc.setFill(Color.SADDLEBROWN);
                gc.fillArc(73 + x.doubleValue(), 0 + y.doubleValue(), 60, 100, 0, 180, ArcType.OPEN); // выступ шл€пы
                gc.setFill(Color.BLACK);
                double x1[] = {104 + (double) x.doubleValue(),94 + (double) x.doubleValue(),104 + (double) x.doubleValue(),104 + (double) x.doubleValue()};
                double y1[] = {105 + (double) y.doubleValue(),124 + (double) y.doubleValue(),124 + (double) y.doubleValue(),105 + (double) y.doubleValue()};
                gc.fillPolygon(x1 , y1, 4);           // Nose
                //gc.arc(105 + x.doubleValue(),139 + y.doubleValue(),37,22,0,-180);  // нЄбо рта
                gc.strokeLine(70 + x.doubleValue(),150 + y.doubleValue(),135 + x.doubleValue(),150 + y.doubleValue());

        //        gc.strokeLine(133,148,144,159);           // Smile arc2

                //gc.setFill(Color.web("#e35d6a"));           // цвет нЄба
               // gc.fill();                                  // заливка рта
               // gc.setFill(Color.WHITE);
                gc.restore();
                gc.closePath();
            }
        };

        stage.setScene(
                new Scene(
                        new Group(
                                canvas
                        )
                )
        );
        stage.show();

        timer.start();
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
