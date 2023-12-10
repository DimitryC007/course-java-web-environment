package application.models;

public abstract class Customer {
    private String fullName;
    private String id;
    private String phone;

    public Customer(String fullName, String id, String phone) {
        this.fullName = fullName;
        this.id = id;
        this.phone = phone;
    }

    public abstract double getDiscount();
    public abstract String getCustomerType();

}
