package application.client;

import application.interfaces.IResponseListener;
import application.models.*;
import application.network.SocketClient;
import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StoreDashboardController implements IResponseListener {
    private AdminView adminView;
    private ProductsManagementView productsManagementView;
    private User user;
    private Stage stage;

    @FXML
    private AnchorPane mainContent;
    @FXML
    private Button adminButton;

    @FXML
    private Button productsManagementButton;

    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
        if (!this.user.getRole().equals("admin")) {
            this.adminButton.setDisable(true);
        }
    }

    public void setProductsManagementView(ProductsManagementView productsManagementView) {
        this.productsManagementView = productsManagementView;
    }

    public void setLoggedInUser(User user) {
        this.user = user;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void onAdminButtonClick() {
        showContent(this.adminView);
    }

    @FXML
    protected void onDownloadLogsClick() {
        SocketClient.sendMessage(new GetAllLogsCommand(), this);
    }

    @FXML
    protected void onDownloadPurchaseHistoryClick() {
        SocketClient.sendMessage(new GetAllPurchaseHistoryCommand(), this);
    }

    @FXML
    protected void onProductsManagementButtonClick() {
        showContent(this.productsManagementView);
    }

    private void showContent(View view) {
        try {
            Parent content = view.getContent(this.user);

            // Replace the existing content with the new scene
            mainContent.getChildren().clear();
            mainContent.getChildren().add(content);
        } catch (IOException ex) {
            // Handle the exception if the FXML file cannot be loaded
            ex.printStackTrace();
        }
    }

    @Override
    public void onResponseReceived(String response) {
        if(response.equals("[]"))
        {
            showAutoCloseAlert();
            return;
        }
        var gson = new Gson();
        var logs = gson.fromJson(response, Log[].class);
        if(logs[0].getCommandId() != null) {
            StringBuilder logsBuilder = new StringBuilder();
            for (var log : logs) {
                logsBuilder.append(log.getDate() + " | " + log.getCommandId() + " | " + log.getCommandName() + "\n");
            }
            saveTxtFile(logsBuilder.toString());
            return;
        }

        var purchases = gson.fromJson(response, PurchaseProductModel[].class);
        StringBuilder purchaseBuilder = new StringBuilder();
        for (var purchase : purchases) {
            purchaseBuilder.append(
                    "customerId: " + purchase.getCustomerId() + " | " +
                    "customer full name: " + purchase.getCustomerFullName() + " | " +
                    "customer type: " + purchase.getCustomerType() +  " | " +
                    "purchase price: " + purchase.getPurchasePrice() + " | " +
                    "product name: " + purchase.getProductName() + " | " +
                    "seller id: " + purchase.getSellerId() + " | " +
                    "seller name: " + purchase.getSellerName() + "\n");
        }
        saveTxtFile(purchaseBuilder.toString());
    }

    private void showAutoCloseAlert()
    {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No purchases yet");
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(new Label("This alert will close automatically in 2 seconds."));
            alert.show();

            try {
                TimeUnit.SECONDS.sleep(2);
                alert.close();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void saveTxtFile(String txt) {
        Platform.runLater(() -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Log File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Txt Files", "*.txt"));

            File selectedFile = fileChooser.showSaveDialog(this.stage);

            if (selectedFile != null) {
                try (FileWriter fileWriter = new FileWriter(selectedFile)) {
                    fileWriter.write(txt);
                    fileWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
