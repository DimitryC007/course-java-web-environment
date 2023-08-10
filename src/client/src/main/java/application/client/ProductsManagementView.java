package application.client;

import application.models.User;
import application.models.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class ProductsManagementView implements View {
    public Parent getContent(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StoreManagementApplication.class.getResource("products-view.fxml"));
        Parent parent = fxmlLoader.load();
        ProductManagementViewController controller = fxmlLoader.getController();
        controller.setLoggedInUser(user);
        controller.setProductsList();
        controller.setCustomersList();

        return parent;
    }
}
