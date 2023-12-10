package application.models;

import java.util.Date;

public class Log {
   private String commandName;
   private String commandId;
   private Date date;

    public Log(Command command)
    {
        this.commandId = command.getCommandId();
        this.commandName = command.getCommandName();
        this.date = new Date();
    }
}
