package application.models;

public class UserAuthenticationModel {
    private String username;
    private String password;

    public UserAuthenticationModel(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
