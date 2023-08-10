package application.client;

import application.models.View;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StoreDashboardController {
    AdminView adminView;
    ProductsManagementView productsManagementView;
    @FXML
    private AnchorPane mainContent;
    @FXML
    private Button adminButton;

    @FXML
    private Button productsManagementButton;

    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
    }

    public void setProductsManagementView(ProductsManagementView productsManagementView) {
        this.productsManagementView = productsManagementView;
    }

    @FXML
    protected void onAdminButtonClick() {
        showContent(this.adminView);
    }

    @FXML
    protected void onProductsManagementButtonClick() {
        showContent(this.productsManagementView);
    }

    private void showContent(View view) {
        try {
            Parent content = view.getContent();

            // Replace the existing content with the new scene
            mainContent.getChildren().clear();
            mainContent.getChildren().add(content);
        } catch (IOException ex) {
            // Handle the exception if the FXML file cannot be loaded
            ex.printStackTrace();
        }
    }
}
