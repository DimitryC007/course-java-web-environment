package application.client;

import application.models.User;
import application.models.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class AdminView implements View {

    public Parent getContent(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StoreManagementApplication.class.getResource("admin-view.fxml"));
        Parent parent = fxmlLoader.load();
        AdminViewController controller = fxmlLoader.getController();
        controller.setUsersList();
        controller.setLoggedInUser(user);

        return parent;
    }
}
