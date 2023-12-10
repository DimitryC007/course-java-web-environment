package application.models;

public class VipCustomer extends Customer {

    public VipCustomer(String fullName, String id, String phone) {
        super(fullName, id, phone);
    }
    private double discount = 0.5;
    private String customerType = "VipCustomer";
    @Override
    public double getDiscount() {
        return discount;
    }

    @Override
    public String getCustomerType() {
        return customerType;
    }
}
