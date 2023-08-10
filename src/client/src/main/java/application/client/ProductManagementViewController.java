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

import java.util.concurrent.TimeUnit;

public class ProductManagementViewController implements IResponseListener {
    @FXML
    private ListView<Product> productsList;
    @FXML
    private ListView<Customer> customersList;
    @FXML
    private Label productIdLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label priceLabel;

    @FXML
    private Label fullNameLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label customerTypeLabel;
    @FXML
    private Label discountLabel;

    @FXML
    private Button purchaseButton;

    @FXML
    private Label purchaseLabel;

    private Product selectedProduct;
    private Customer selectedCustomer;
    private User user;

    @FXML
    private void onPurchaseClick() throws InterruptedException {
        var purchaseProductModel = new PurchaseProductModel(this.selectedCustomer,this.user,this.selectedProduct);
        SocketClient.sendMessage(new PurchaseProductCommand(purchaseProductModel), null);
        purchaseLabel.setText("Purchase success");

        resetPurchase();
    }

    private void resetPurchase()
    {
        this.selectedProduct = null;
        this.selectedCustomer = null;

        this.fullNameLabel.setText("");
        this.idLabel.setText("");
        this.phoneLabel.setText("");
        this.customerTypeLabel.setText("");
        this.discountLabel.setText("");
        this.productIdLabel.setText("");
        this.categoryLabel.setText("");
        this.productNameLabel.setText("");
        this.priceLabel.setText("");
        this.purchaseButton.setDisable(true);
    }

    private void setPurchaseEnable()
    {
        purchaseLabel.setText("");
        if(this.selectedProduct != null && selectedCustomer != null)
        {
            this.purchaseButton.setDisable(false);
        }
    }

    private EventHandler<SelectProductEvent> selectProductEventHandler = event -> {
        this.selectedProduct = event.getProduct();
        this.productIdLabel.setText(String.valueOf(this.selectedProduct.getProductId()));
        this.categoryLabel.setText(String.valueOf(this.selectedProduct.getCategory()));
        this.productNameLabel.setText(String.valueOf(this.selectedProduct.getProductName()));
        this.priceLabel.setText(String.valueOf(this.selectedProduct.getPrice()));
        this.setPurchaseEnable();
    };

    private EventHandler<SelectCustomerEvent> selectCustomerEventHandler = event -> {
        this.selectedCustomer = event.getCustomer();
        this.fullNameLabel.setText(String.valueOf(this.selectedCustomer.getFullName()));
        this.idLabel.setText(String.valueOf(this.selectedCustomer.getId()));
        this.phoneLabel.setText(String.valueOf(this.selectedCustomer.getPhone()));
        this.customerTypeLabel.setText(String.valueOf(this.selectedCustomer.getCustomerType()));
        this.discountLabel.setText(String.valueOf(this.selectedCustomer.getDiscount()));
        this.setPurchaseEnable();
    };

    public void setProductsList() {
        SocketClient.sendMessage(new GetAllProductsCommand(this.user), this);
        productsList.addEventHandler(SelectProductEvent.SELECT_PRODUCT, selectProductEventHandler);
    }

    public void setCustomersList() {
        SocketClient.sendMessage(new GetAllCustomersCommand(), this);
        customersList.addEventHandler(SelectCustomerEvent.SELECT_CUSTOMER, selectCustomerEventHandler);
    }

    public void setLoggedInUser(User user)
    {
        this.user = user;
    }

    @Override
    public void onResponseReceived(String response) {
        var gson = new Gson();
        var products = gson.fromJson(response, Product[].class);
        if (products[0].getProductId() > 0) {
            ObservableList<Product> observableProducts = FXCollections.observableArrayList(products);
            productsList.setItems(observableProducts);
            productsList.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Product> call(ListView<Product> listView) {
                    return new ProductManagementViewController.ProductCell();
                }
            });
            return;
        }
        var customers = gson.fromJson(response, Customer[].class);
        if (customers != null) {
            ObservableList<Customer> observableCustomers = FXCollections.observableArrayList(customers);
            customersList.setItems(observableCustomers);
            customersList.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
                @Override
                public ListCell<Customer> call(ListView<Customer> listView) {
                    return new ProductManagementViewController.CustomerCell();
                }
            });
            return;
        }
    }

    private class ProductCell extends ListCell<Product> {
        private final Button purchaseButton;

        public ProductCell() {
            purchaseButton = new Button("Select");
            purchaseButton.setOnAction(event -> {
                Product product = getItem();
                if (product != null) {
                    SelectProductEvent purchaseEvent = new SelectProductEvent(product);
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
                stringBuilder.append("\nPrice: " + product.getPrice());

                purchaseButton.setDisable(false);
                setGraphic(new VBox(new Label(stringBuilder.toString()), purchaseButton));
            }
        }
    }

    private class CustomerCell extends ListCell<Customer> {
        private final Button selectCustomerButton;

        public CustomerCell() {
            selectCustomerButton = new Button("Select");
            selectCustomerButton.setOnAction(event -> {
                Customer customer = getItem();
                if (customer != null) {
                    SelectCustomerEvent selectCustomerEvent = new SelectCustomerEvent(customer);
                    fireEvent(selectCustomerEvent);
                    event.consume();
                }
            });
        }

        @Override
        protected void updateItem(Customer customer, boolean empty) {
            super.updateItem(customer, empty);

            if (empty || customer == null) {
                setGraphic(null);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Customer Type: " + customer.getCustomerType());
                stringBuilder.append("\nFullName: " + customer.getFullName());
                stringBuilder.append("\nId: " + customer.getId());
                stringBuilder.append("\nPhone: " + customer.getPhone());
                stringBuilder.append("\nDiscount: " + customer.getDiscount() + "%");

                selectCustomerButton.setDisable(false);
                setGraphic(new VBox(new Label(stringBuilder.toString()), selectCustomerButton));
            }
        }
    }
}
