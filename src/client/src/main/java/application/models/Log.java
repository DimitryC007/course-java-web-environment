package application.models;

import java.util.Date;

public class Log {
    private String commandName;
    private String commandId;
    private Date date;

    public String getCommandName()
    {
        return this.commandName;
    }

    public String getCommandId()
    {
        return this.commandId;
    }

    public Date getDate()
    {
        return this.date;
    }
}
