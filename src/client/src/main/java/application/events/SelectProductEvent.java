package application.events;

import application.models.Product;
import javafx.event.Event;
import javafx.event.EventType;

public class SelectProductEvent extends Event {
    private final Product product;

    public static final EventType<SelectProductEvent> SELECT_PRODUCT = new EventType<>(Event.ANY, "SELECT_PRODUCT_EVENT");

    public SelectProductEvent(Product product) {
        super(SELECT_PRODUCT);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
