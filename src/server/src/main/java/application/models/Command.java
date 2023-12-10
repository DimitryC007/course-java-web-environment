package application.models;

public class Command {
    private String commandId;
    private String commandName;
    private String commandData;

    public Command(String commandId,String commandName,String commandData)
    {
        this.commandId = commandId;
        this.commandName = commandName;
        this.commandData = commandData;
    }

    public String getCommandId()
    {
        return this.commandId;
    }

    public String getCommandName()
    {
        return this.commandName;
    }

    public String getCommandData()
    {
        return this.commandData;
    }
}

