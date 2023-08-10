package application.models;

public class GetAllProductsCommand extends Command{
    public GetAllProductsCommand(User user)
    {
        this.name = this.getClass().getSimpleName();
        this.data = user;
    }
}

