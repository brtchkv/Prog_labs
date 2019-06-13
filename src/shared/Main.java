package shared;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Rectangle rectBound = new Rectangle();
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root,400,400);
            Canvas c = new Canvas(400,400);

            root.setCenter(c);
            c.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                boolean shouldDraw = false;
                double dX,dY;

                @Override
                public void handle(MouseEvent arg0) {
                    if (arg0.getEventType() == MouseEvent.MOUSE_PRESSED) {
                        shouldDraw = rectBound.contains(arg0.getX(), arg0.getY());
                        if (shouldDraw) {
                            dX = arg0.getX();
                            dY = arg0.getY();
                        }
                    } else if (arg0.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                        if (shouldDraw) {
                            double x = (arg0.getX() + rectBound.getX() - dX),
                                    y = (arg0.getY() + rectBound.getY() - dY);

                            c.getGraphicsContext2D().clearRect(rectBound.getX(),
                                    rectBound.getY(), rectBound.getWidth(),//50
                                    rectBound.getHeight());

                            rectBound.setY(y);
                            rectBound.setX(x);

                            dX = arg0.getX();
                            dY = arg0.getY();
                        }
                    }
                }
            });

            c.getGraphicsContext2D().setFill(Color.AQUAMARINE);
            rectBound.xProperty().addListener(new ChangeListener<Number>(){//since they go together
                @Override
                public void changed(ObservableValue<? extends Number> arg0,
                                    Number arg1, Number arg2) {
                    c.getGraphicsContext2D().fillRect(rectBound.getX(),rectBound.getY(),
                            rectBound.getWidth(),rectBound.getHeight()); //just an example
                }

            });

            primaryStage.setScene(scene);
            primaryStage.show();
            rectBound.setWidth(50);rectBound.setHeight(50);
            rectBound.setY(50);rectBound.setX(10);//because of the listener x is last
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


