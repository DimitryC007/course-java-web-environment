package application.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StoreDashboard {

    private AdminView adminView;
    private Stage stage;

    public StoreDashboard(Stage stage, AdminView adminView) {
        this.stage = stage;
        this.adminView = adminView;
    }

    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StoreManagementApplication.class.getResource("store-dashboard.fxml"));
        Parent content = fxmlLoader.load();
        StoreDashboardController controller = fxmlLoader.getController();
        controller.setAdminView(this.adminView);
        Scene scene = new Scene(content, 1000, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
