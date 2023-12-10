package application.purchaseService;

import application.helpers.FileHelper;
import application.helpers.ResourceFileName;
import application.models.Customer;
import application.models.CustomerResource;
import application.models.PurchaseProductModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PurchaseService {


    public static PurchaseProductModel[] getAllPurchaseHistory() {
        var json = FileHelper.Read(ResourceFileName.PURCHASE_HISTORY);
        var gson = new Gson();
        return gson.fromJson(json, PurchaseProductModel[].class);
    }

    public static boolean purchaseProduct(PurchaseProductModel purchaseProductModel) {

        var json = FileHelper.Read(ResourceFileName.PURCHASE_HISTORY);
        var gson = new Gson();
        var purchaseHistoryList = gson.fromJson(json, PurchaseProductModel[].class);

        List<PurchaseProductModel> updatedPurchaseHistory = new ArrayList<>();

        for (PurchaseProductModel purchaseHistory : purchaseHistoryList) {
            updatedPurchaseHistory.add(purchaseHistory);
        }

        updatedPurchaseHistory.add(purchaseProductModel);
        PurchaseProductModel[] updatedPurchaseHistoryArray = updatedPurchaseHistory.toArray(new PurchaseProductModel[0]);

        String updatedJson = gson.toJson(updatedPurchaseHistoryArray);

        FileHelper.write(ResourceFileName.PURCHASE_HISTORY, updatedJson);

        return true;
    }
}
