package application.models;

public class PurchaseProductCommand extends Command{
    public PurchaseProductCommand(PurchaseProductModel purchaseProduct)
    {
        this.name = this.getClass().getSimpleName();
        this.data = purchaseProduct;
    }
}
