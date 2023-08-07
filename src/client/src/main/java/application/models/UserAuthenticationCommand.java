package application.models;

public class UserAuthenticationCommand extends Command<UserAuthenticationModel> {
    public UserAuthenticationCommand(UserAuthenticationModel userAuthentication) {
        this.name = this.getClass().getSimpleName();
        this.data = userAuthentication;
    }
}
