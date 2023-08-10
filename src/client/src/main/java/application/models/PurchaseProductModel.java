package application.models;

public class PurchaseProductModel {
    private String customerId;
    private String customerFullName;
    private String customerType;
    private double purchasePrice;
    private String productName;
    private int productId;

    private String sellerName;
    private int sellerId;

    public PurchaseProductModel(Customer customer,User user,Product product)
    {
        this.customerId = customer.getId();
        this.customerFullName = customer.getFullName();
        this.customerType = customer.getCustomerType();
        this.purchasePrice = product.getPrice() - (product.getPrice() * customer.getDiscount());
        this.productName = product.getProductName();
        this.productId = product.getProductId();
        this.sellerName = user.getFullName();
        this.sellerId = user.getEmployeeId();
    }
}
