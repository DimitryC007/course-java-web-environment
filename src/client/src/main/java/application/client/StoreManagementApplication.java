package application.client;

import application.network.SocketClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class StoreManagementApplication extends Application {
    public static void main(String[] args) {
        SocketClient.connect(5000);
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        var adminView = new AdminView();
        var dashboardStore = new StoreDashboard(stage, adminView);


        FXMLLoader fxmlLoader = new FXMLLoader(StoreManagementApplication.class.getResource("app-start.fxml"));
        Parent content = fxmlLoader.load();
        StoreManagementApplicationController controller = fxmlLoader.getController();
        controller.setStoreDashboard(dashboardStore);
        Scene scene = new Scene(content, 320, 240);
        stage.setScene(scene);
        stage.show();
    }
}