package application.models;

public class NewCustomer extends Customer {

    public NewCustomer(String fullName, String id, String phone) {
        super(fullName, id, phone);
    }
    private double discount = 0.2;
    private String customerType = "NewCustomer";
    @Override
    public double getDiscount() {
        return discount;
    }

    @Override
    public String getCustomerType() {
        return customerType;
    }


}
