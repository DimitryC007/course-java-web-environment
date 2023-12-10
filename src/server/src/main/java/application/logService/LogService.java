package application.logService;

import application.helpers.FileHelper;
import application.helpers.ResourceFileName;
import application.models.Command;
import application.models.Log;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class LogService {

    public static Log[] getAllLogs()
    {
        var json = FileHelper.Read(ResourceFileName.LOG);
        var gson = new Gson();
        return gson.fromJson(json, Log[].class);
    }

    public static void log(Command command) {

        var json = FileHelper.Read(ResourceFileName.LOG);
        var gson = new Gson();
        var logs = gson.fromJson(json, Log[].class);

        List<Log> updatedLogs = new ArrayList<>();

        for (Log log : logs) {
            updatedLogs.add(log);
        }

        updatedLogs.add(new Log(command));
        Log[] updatedLogsArray = updatedLogs.toArray(new Log[0]);

        String updatedJson = gson.toJson(updatedLogsArray);

        FileHelper.write(ResourceFileName.LOG, updatedJson);
    }
}
