package application.events;

import application.models.Product;
import javafx.event.Event;
import javafx.event.EventType;

public class ProductPurchaseEvent extends Event {
    private final Product product;

    public static final EventType<ProductPurchaseEvent> PURCHASE_EVENT = new EventType<>(Event.ANY, "PRODUCT_PURCHASE_EVENT");

    public ProductPurchaseEvent(Product product) {
        super(PURCHASE_EVENT);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
