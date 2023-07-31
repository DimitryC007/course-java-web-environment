package application.client;

import application.events.ResponseListener;
import application.models.UserAuthenticationCommand;
import application.network.SocketClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StoreManagementApplicationController implements ResponseListener {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label loginStatusLabel;
    private StoreDashboard storeDashboard;

    public void setStoreDashboard(StoreDashboard storeDashboard)
    {
        this.storeDashboard = storeDashboard;
    }

    @FXML
    protected void onLoginButtonClick() {
        var user  = new UserAuthenticationCommand(usernameField.getText(),passwordField.getText());
        SocketClient.sendMessage(user,this);
    }

    @Override
    public void onResponseReceived(String responseData) {
        boolean isAuthenticated =  Boolean.parseBoolean(responseData);
        Platform.runLater(() -> {
            if(!isAuthenticated)
            {
                loginStatusLabel.setText("Login failed, Credentials are invalid");
                return;
            }
            loginStatusLabel.setText("Login success");
            try {
                TimeUnit.SECONDS.sleep(1);
                this.storeDashboard.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}