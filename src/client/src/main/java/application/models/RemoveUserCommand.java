package application.models;

public class RemoveUserCommand extends Command<RemoveUserModel>{

    public RemoveUserCommand(RemoveUserModel removeUser)
    {
        this.name = this.getClass().getSimpleName();
        this.data = removeUser;
    }
}
