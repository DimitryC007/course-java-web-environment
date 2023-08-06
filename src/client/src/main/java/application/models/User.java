package application.models;

public class User {
    private String fullName;
    private String role;
    private String username;
    private String password;

    public User(String fullName, String role, String username, String password) {
        this.fullName = fullName;
        this.role = role;
        this.username = username;
        this.password = password;
    }


    public String getFullName()
    {
        return this.fullName;
    }

    public String getRole()
    {
        return this.role;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", fullName, role, username, password);
    }
}