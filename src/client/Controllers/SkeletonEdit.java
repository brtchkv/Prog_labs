package client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class SkeletonEdit {

    @FXML
    private TextField humanName;

    @FXML
    private TextField humanAge;

    @FXML
    private TextField xCoordinate;

    @FXML
    private TextField yCoordinate;

    @FXML
    private TextField skillName;

    @FXML
    private TextField skillInfo;

    @FXML
    private Label labelSize;

    @FXML
    private Slider slider;

    @FXML
    void clear(ActionEvent event) {
        skillName.clear();
        humanName.clear();
        humanAge.clear();
        skillInfo.clear();
        labelSize.setText("0");
        yCoordinate.clear();
        xCoordinate.clear();
        slider.setValue(slider.getMin());
    }

    @FXML
    void submit(ActionEvent event) {

    }

}
