package application.customerService;

import application.helpers.FileHelper;
import application.helpers.ResourceFileName;
import application.models.*;
import com.google.gson.Gson;
import java.util.List;
import java.util.ArrayList;

public class CustomerService {
    public static List<Customer> getAllCustomers() {
        var json = FileHelper.Read(ResourceFileName.CUSTOMERS);
        var gson = new Gson();
        var customers = gson.fromJson(json, CustomerResource[].class);
        return ConvertCustomers(customers);
    }

    private static List<Customer> ConvertCustomers(CustomerResource[] customers)
    {   List<Customer> parsedCustomers = new ArrayList<Customer>();
        for (var customer:customers)
        {   var customerType = customer.getCustomerType();
            if(customerType.equals("NewCustomer"))
            {
                parsedCustomers.add(new NewCustomer(customer.getFullName(),customer.getId(),customer.getPhone()));
            } else if (customerType.equals("ReturnCustomer")) {
                parsedCustomers.add(new ReturnCustomer(customer.getFullName(),customer.getId(),customer.getPhone()));
            } else if (customerType.equals("VipCustomer")) {
                parsedCustomers.add(new VipCustomer(customer.getFullName(),customer.getId(),customer.getPhone()));
            }
        }
        return parsedCustomers;
    }
}
