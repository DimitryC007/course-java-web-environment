package application.network;

import application.interfaces.IResponseListener;
import application.models.Command;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.google.gson.Gson;

public class SocketClient {
    private static Socket socket = null;
    private static DataInputStream fromNetInputStream;
    private static PrintStream toNetOutputStream;
    private static Map<UUID, IResponseListener> responseListeners = new HashMap<>();

    public static void connect(int port) {
        try {
            socket = new Socket("localhost", port);
            fromNetInputStream = new DataInputStream(socket.getInputStream());
            toNetOutputStream = new PrintStream(socket.getOutputStream());

            // Start a background thread to listen for responses from the server
            new Thread(() -> {
                listenForResponses();
            }).start();

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public static void disconnect() {
        try {
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendMessage(Command command, IResponseListener responseListener) {
        try {
            UUID requestId = UUID.randomUUID();

            responseListeners.put(requestId, responseListener);

            var gson = new Gson();
            var json = gson.toJson(command.getData());
            toNetOutputStream.println(requestId + "_" + command.getName() + "_" + json);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void listenForResponses() {
        try {
            while (true) {
                String response = fromNetInputStream.readLine();
                String[] parts = response.split("_", 2);
                UUID requestId = UUID.fromString(parts[0]);
                String responseData = parts[1];

                IResponseListener responseListener = responseListeners.get(requestId);

                if (responseListener != null) {
                    responseListener.onResponseReceived(responseData);
                    responseListeners.remove(requestId);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
