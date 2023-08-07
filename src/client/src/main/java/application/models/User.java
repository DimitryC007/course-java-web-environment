package application.models;

public class User {
    private String fullName;
    private String id;
    private String phone;
    private String accountNumber;
    private Integer branchId;
    private Integer employeeId;
    private String role;
    private String username;
    private String password;

    public User(String fullName,
                String id,
                String phone,
                String accountNumber,
                Integer branchId,
                Integer employeeId,
                String role,
                String username,
                String password) {
        this.fullName = fullName;
        this.role = role;
        this.username = username;
        this.password = password;
    }


    public String getFullName()
    {
        return this.fullName;
    }

    public String getId()
    {
        return this.id;
    }

    public String getPhone()
    {
        return this.phone;
    }

    public String getAccountNumber()
    {
        return this.accountNumber;
    }

    public Integer getBranchId()
    {
        return this.branchId;
    }

    public Integer getEmployeeId()
    {
        return this.employeeId;
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