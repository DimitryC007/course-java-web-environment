package application.client;

import application.interfaces.IResponseListener;
import application.models.Customer;
import application.models.User;
import application.models.UserAuthenticationCommand;
import application.models.UserAuthenticationModel;
import application.network.SocketClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StoreManagementApplicationController implements IResponseListener {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label loginStatusLabel;
    private StoreDashboard storeDashboard;

    public void setStoreDashboard(StoreDashboard storeDashboard) {
        this.storeDashboard = storeDashboard;
    }

    @FXML
    protected void onLoginButtonClick() {
        var user = new UserAuthenticationCommand(new UserAuthenticationModel(usernameField.getText(), passwordField.getText()));
        SocketClient.sendMessage(user, this);
    }

    @Override
    public void onResponseReceived(String response) {
        boolean isAuthenticated = !response.equals("null");
        Platform.runLater(() -> {
            if (!isAuthenticated) {
                loginStatusLabel.setText("Login failed, Credentials are invalid");
                return;
            }
            loginStatusLabel.setText("Login success");
            try {
                loginStatusLabel.setText("Login success");
                TimeUnit.SECONDS.sleep(1);
                var gson = new Gson();
                var user = gson.fromJson(response, User.class);
                this.storeDashboard.start(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}