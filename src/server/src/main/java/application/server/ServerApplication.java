package application.server;

import application.adminService.AdminService;
import application.authentication.Authentication;
import application.customerService.CustomerService;
import application.logService.LogService;
import application.models.*;
import application.purchaseService.PurchaseService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;
import productService.ProductService;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        try {
            connect();
        } catch (Exception ex) {

        }
    }

    public static void connect() throws IOException {
        final ServerSocket server = new ServerSocket(5000);
        final Socket socket = server.accept();
        System.out.println("server start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String clientAddress = "";
                try {
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    PrintStream outputStream = new PrintStream(socket.getOutputStream());

                    while (true) {
                        var message = inputStream.readLine();
                        var command = getCommand(message);
                        logCommand(command);
                        Gson gson = new Gson();

                        if (command.getCommandName().equals("UserAuthenticationCommand")) {
                            var userAuthenticationModel = gson.fromJson(command.getCommandData(), UserAuthenticationModel.class);
                            var user = Authentication.isAuthenticated(userAuthenticationModel.getUsername(), userAuthenticationModel.getPassword());
                            outputStream.println(command.getCommandId() + "_" + gson.toJson(user));
                        }
                        if (command.getCommandName().equals("RemoveUserCommand")) {
                            var removeUser = gson.fromJson(command.getCommandData(), RemoveUserModel.class);
                            AdminService.removeUser(removeUser.getId());
                        }
                        if (command.getCommandName().equals("AddUserCommand")) {
                            var addUser = gson.fromJson(command.getCommandData(), User.class);
                            AdminService.addUser(addUser);
                        }
                        if (command.getCommandName().equals("GetAllUsersCommand")) {
                            var users = AdminService.getAllUsers();
                            outputStream.println(command.getCommandId() + "_" + gson.toJson(users));
                        }
                        if (command.getCommandName().equals("GetAllProductsCommand")) {
                            var loggedUser = gson.fromJson(command.getCommandData(), User.class);
                            var products = ProductService.getAllProducts(loggedUser);
                            outputStream.println(command.getCommandId() + "_" + gson.toJson(products));
                        }
                        if (command.getCommandName().equals("GetAllCustomersCommand")) {
                            var customers = CustomerService.getAllCustomers();
                            outputStream.println(command.getCommandId() + "_" + gson.toJson(customers));
                        }
                        if (command.getCommandName().equals("PurchaseProductCommand")) {
                            var purchaseProductModel = gson.fromJson(command.getCommandData(), PurchaseProductModel.class);
                            PurchaseService.purchaseProduct(purchaseProductModel);
                        }
                        if (command.getCommandName().equals("GetAllLogsCommand")) {
                            var logs = LogService.getAllLogs();
                            outputStream.println(command.getCommandId() + "_" + gson.toJson(logs));
                        }
                        if (command.getCommandName().equals("GetAllPurchaseHistoryCommand")) {
                            var purchaseHistory = PurchaseService.getAllPurchaseHistory();
                            outputStream.println(command.getCommandId() + "_" + gson.toJson(purchaseHistory));
                        }
                    }

                } catch (Exception ex) {

                }
            }
        }).start();
    }

    private static Command getCommand(String message) {
        var messageArr = message.split("_", 3);
        return new Command(messageArr[0], messageArr[1], messageArr[2]);
    }

    private static void logCommand(Command command) {
        System.out.println("Received from client commandId: " + command.getCommandId());
        System.out.println("Received from client commandName: " + command.getCommandName());
        System.out.println("Received from client commandData: " + command.getCommandData());
        LogService.log(command);
    }
}
