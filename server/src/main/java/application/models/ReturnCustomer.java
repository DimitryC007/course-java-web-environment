package application.models;

public class ReturnCustomer extends Customer {

    public ReturnCustomer(String fullName, String id, String phone) {
        super(fullName, id, phone);
    }
    private double discount = 0.1;
    private String customerType = "ReturnCustomer";
    @Override
    public double getDiscount() {
        return discount;
    }

    @Override
    public String getCustomerType() {
        return customerType;
    }
}
