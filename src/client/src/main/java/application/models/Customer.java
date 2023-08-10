package application.models;

public class Customer {
    private String fullName;
    private String id;
    private String phone;
    private String customerType;
    private double discount;

    public String getFullName() {
        return this.fullName;
    }

    public String getId() {
        return this.id;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getCustomerType() {
        return this.customerType;
    }

    public double getDiscount() {
        return this.discount;
    }
}
