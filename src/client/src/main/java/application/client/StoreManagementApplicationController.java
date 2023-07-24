package application.client;

import application.models.UserAuthenticationCommand;
import application.network.SocketClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StoreManagementApplicationController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label loginStatusLabel;


    @FXML
    protected void onLoginButtonClick() {
        var user  = new UserAuthenticationCommand(usernameField.getText(),passwordField.getText());
        SocketClient.SendMessage(user);
    }
}