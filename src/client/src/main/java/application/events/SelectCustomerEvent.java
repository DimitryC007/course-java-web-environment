package application.events;

import application.models.Customer;
import javafx.event.Event;
import javafx.event.EventType;

public class SelectCustomerEvent extends Event {
    private final Customer customer;

    public static final EventType<SelectCustomerEvent> SELECT_CUSTOMER= new EventType<>(Event.ANY, "SELECT_CUSTOMER_EVENT");

    public SelectCustomerEvent(Customer customer) {
        super(SELECT_CUSTOMER);
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}
