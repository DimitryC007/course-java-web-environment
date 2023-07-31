package application.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StoreDashboard {
    private Stage stage;

    public StoreDashboard(Stage stage) {
        this.stage = stage;
    }

    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StoreManagementApplication.class.getResource("store-dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
