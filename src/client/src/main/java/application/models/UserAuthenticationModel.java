package application.models;

import java.io.Serializable;

public class UserAuthenticationModel implements Serializable {
    private String username;
    private String password;

    public UserAuthenticationModel(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }
}
