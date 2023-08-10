package application.client;

import application.interfaces.IResponseListener;
import application.network.SocketClient;
import com.google.gson.Gson;
import application.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import application.events.*;
import javafx.util.Callback;

public class ProductManagementViewController implements IResponseListener {
    @FXML
    private ListView<Product> productsList;
    @FXML
    private ListView<Product> customersList;
    @FXML
    private ProductManagementViewController productManagementViewController;
    private Gson gson;
    private Product product;

    private EventHandler<ProductPurchaseEvent> purchaseEventHandler = event -> {
        this.product = event.getProduct();
    };

    public void setProductsList() {
        gson = new Gson();
        SocketClient.sendMessage(new GetAllProductsCommand(), this);
        productsList.addEventHandler(ProductPurchaseEvent.PURCHASE_EVENT, purchaseEventHandler);
    }

    @Override
    public void onResponseReceived(String response) {
        var products = gson.fromJson(response, Product[].class);
        if (products != null) {
            ObservableList<Product> observableUsers = FXCollections.observableArrayList(products);
            productsList.setItems(observableUsers);
            productsList.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Product> call(ListView<Product> listView) {
                    return new ProductManagementViewController.ProductCell();
                }
            });
        }
    }

    private class ProductCell extends ListCell<Product> {
        private final Button purchaseButton;

        public ProductCell() {
            purchaseButton = new Button("Purchase");
            purchaseButton.setOnAction(event -> {
                Product product = getItem();
                if (product != null) {
                    ProductPurchaseEvent purchaseEvent = new ProductPurchaseEvent(product);
                    fireEvent(purchaseEvent);
                    event.consume();
                }
            });
        }

        @Override
        protected void updateItem(Product product, boolean empty) {
            super.updateItem(product, empty);

            if (empty || product == null) {
                setGraphic(null);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ProductId: " + product.getProductId());
                stringBuilder.append("\nProductName: " + product.getProductName());
                stringBuilder.append("\nCategory: " + product.getCategory());

                purchaseButton.setDisable(false);
                setGraphic(new VBox(new Label(stringBuilder.toString()), purchaseButton));
            }
        }
    }
}
