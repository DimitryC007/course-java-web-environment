package application.client;

import application.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StoreDashboard {

    private AdminView adminView;

    private ProductsManagementView productsManagementView;
    private Stage stage;

    public StoreDashboard(Stage stage, AdminView adminView, ProductsManagementView productsManagementView) {
        this.stage = stage;
        this.adminView = adminView;
        this.productsManagementView = productsManagementView;
    }

    public void start(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StoreManagementApplication.class.getResource("store-dashboard.fxml"));
        Parent content = fxmlLoader.load();
        StoreDashboardController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setLoggedInUser(user);
        controller.setAdminView(this.adminView);
        controller.setProductsManagementView(this.productsManagementView);



        Scene scene = new Scene(content, 1000, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
