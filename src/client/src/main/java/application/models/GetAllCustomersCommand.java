package application.models;

public class GetAllCustomersCommand extends Command{
    public GetAllCustomersCommand()
    {
        this.name = this.getClass().getSimpleName();
    }
}
