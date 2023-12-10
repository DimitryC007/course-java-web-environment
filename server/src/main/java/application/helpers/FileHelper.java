package application.helpers;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class FileHelper {
    private static Map<String, String> resourceContentCache = new HashMap<>();
    public static String Read(String resourceName) {

        if (resourceContentCache.containsKey(resourceName)) {
            return resourceContentCache.get(resourceName);
        }

        try {
            URL resourceURL = FileHelper.class.getClassLoader().getResource(resourceName);
            if (resourceURL != null) {
                URLConnection connection = resourceURL.openConnection();
                connection.setUseCaches(false);
                // Open an InputStream to read the resource file content
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream()))) {
                    // Read the JSON data from the resource file
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();
                }
            } else {
                System.out.println("Resource not found: " + resourceName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void write(String resourceName, String content) {
        resourceContentCache.put(resourceName,content);
    }
}
