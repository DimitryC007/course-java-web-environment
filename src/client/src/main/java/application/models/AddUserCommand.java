package application.models;

public class AddUserCommand extends Command<User> {
    public AddUserCommand(User user)
    {
        this.name = this.getClass().getSimpleName();
        this.data = user;
    }
}
