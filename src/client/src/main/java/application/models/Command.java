package application.models;

public abstract class Command<TObject> {
    protected String name;
    protected TObject data;

    public String getName()
    {
        return this.name;
    }

    public Object getData()
    {
        return this.data;
    }
}
