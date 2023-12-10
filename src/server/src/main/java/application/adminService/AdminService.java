package application.adminService;

import application.helpers.FileHelper;
import application.helpers.ResourceFileName;
import application.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AdminService {

    public static void removeUser(String id) {

        var json = FileHelper.Read(ResourceFileName.USERS);
        var gson = new Gson();
        var users = gson.fromJson(json, User[].class);

        if (users == null || users.length == 0) {
            return;
        }

        List<User> updatedUsers = new ArrayList<>();

        for (User user : users) {
            if (!user.getId().equals(id)) {
                updatedUsers.add(user);
            }
        }

        User[] updatedUsersArray = updatedUsers.toArray(new User[0]);
        String updatedJson = gson.toJson(updatedUsersArray);

        FileHelper.write(ResourceFileName.USERS, updatedJson);
    }

    public static User[] getAllUsers() {
        var json = FileHelper.Read(ResourceFileName.USERS);
        var gson = new Gson();
        return gson.fromJson(json, User[].class);
    }

    public static void addUser(User userToAdd) {
        var json = FileHelper.Read(ResourceFileName.USERS);
        var gson = new Gson();
        var users = gson.fromJson(json, User[].class);

        List<User> updatedUsers = new ArrayList<>();

        for (User user : users) {
            updatedUsers.add(user);
        }

        updatedUsers.add(userToAdd);
        User[] updatedUsersArray = updatedUsers.toArray(new User[0]);

        String updatedJson = gson.toJson(updatedUsersArray);

        FileHelper.write(ResourceFileName.USERS, updatedJson);
    }
}
