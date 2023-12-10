package application.authentication;

import application.helpers.ResourceFileName;
import application.helpers.FileHelper;
import application.models.User;
import application.models.UserAuthenticationModel;
import com.google.gson.Gson;

public class Authentication {
    public static User isAuthenticated(String username,String password)
    {
        var json = FileHelper.Read(ResourceFileName.USERS);
        var gson = new Gson();
        var users = gson.fromJson(json, User[].class);

        for (var user:users) {
            if(user.getUsername().equals(username)  && user.getPassword().equals(password))
            {
                return user;
            }
        }
        return null;
    }
}
