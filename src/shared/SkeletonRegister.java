package shared;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SkeletonRegister {
    private static String nickname;
    private static String emailAddress;

    @FXML
    private TextField nick;

    @FXML
    private TextField email;

    @FXML
    void clear(ActionEvent event) {
        nick.clear();
        email.clear();
    }

    public static String getNickname() {
        return nickname;
    }

    public static String getEmailAddress() {
        return emailAddress;
    }

    @FXML
    void register(ActionEvent event) {
        this.nickname = nick.getCharacters().toString();
        this.emailAddress = email.getCharacters().toString();
        System.out.println(nickname + emailAddress);
    }

    @FXML
    void showHelp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setContentText("An application for organising your slavery business.\nCreator: Ivan Bratchikov");
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        assert nick != null : "fx:id=\"nick\" was not injected: check your FXML file 'register.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'register.fxml'.";

    }

    @FXML
    void loginWindow(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Login.loadScene(stage, loader);
    }

}
