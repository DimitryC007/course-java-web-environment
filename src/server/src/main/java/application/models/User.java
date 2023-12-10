package application.models;

public class User {
    private String fullName;
    private String id;
    private String phone;
    private String accountId;
    private int branchId;
    private int employeeId;
    private String role;
    private String username;
    private String password;


    public String getFullName() {
        return this.fullName;
    }

    public String getId() {
        return this.id;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public int getBranchId() {
        return this.branchId;
    }

    public int getEmployeeId() {
        return this.employeeId;
    }

    public String getRole() {
        return this.role;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}