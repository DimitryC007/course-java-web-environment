package application.models;

public class UserAuthenticationCommand extends Command<UserAuthenticationModel> {
    public UserAuthenticationCommand(String username, String password) {
        this.name = this.getClass().getSimpleName();
        this.data = new UserAuthenticationModel(username, password);
    }
}
