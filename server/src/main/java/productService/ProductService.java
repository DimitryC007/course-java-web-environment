package productService;

import application.helpers.FileHelper;
import application.helpers.ResourceFileName;
import application.models.Product;
import com.google.gson.Gson;
import application.models.User;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public static List<Product> getAllProducts(User user) {
        var json = FileHelper.Read(ResourceFileName.PRODUCTS);
        var gson = new Gson();
        var products = gson.fromJson(json, Product[].class);
        return getProductsByLoggedUser(products, user);
    }

    private static List<Product> getProductsByLoggedUser(Product[] products, User user) {

        List<Product> productsByLoggedUser = new ArrayList<>();
        for (var product : products) {
            if(user.getRole().equals("admin") || user.getBranchId() == product.getBranchId())
            {
                productsByLoggedUser.add(product);
            }
        }

        return productsByLoggedUser;
    }
}
