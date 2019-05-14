package shared;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SkeletonLogin {
    private static String nickname;
    private static String password;

    @FXML
    private TextField nick;

    @FXML
    private PasswordField pass;

    @FXML
    void clear(ActionEvent event) {
        nick.clear();
        pass.clear();
    }

    public static String getNickname() {
        return nickname;
    }

    public static String getPassword() {
        return password;
    }

    @FXML
    void login(ActionEvent event) {
        this.nickname = nick.getCharacters().toString();
        this.password = pass.getCharacters().toString();
        System.out.println(nickname + password);
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
        assert pass != null : "fx:id=\"pass\" was not injected: check your FXML file 'register.fxml'.";

    }

    @FXML
    void registerWindow(ActionEvent event) {
        Window stageP = nick.getScene().getWindow();
        stageP.hide();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        Login.loadScene(stage, loader);
    }

}