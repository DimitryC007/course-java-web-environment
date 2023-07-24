package application.network;

import application.helpers.Base64Helper;
import application.models.Command;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;

public class SocketClient {
    private static Socket socket = null;
    private static DataInputStream fromNetInputStream;
    private static DataInputStream consoleInput;
    private static PrintStream toNetOutputStream;

    public static void connect(int port) {
        try {
            socket = new Socket("localhost", port);
            fromNetInputStream = new DataInputStream(socket.getInputStream());
            toNetOutputStream = new PrintStream(socket.getOutputStream());
            consoleInput = new DataInputStream(System.in);

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public static void disconnect() {
        try {
            socket.close();
        } catch (Exception ex) {

        }
    }

    public static void SendMessage(Command command) {
        var base64String = Base64Helper.objectToBase64(command.getData());
        toNetOutputStream.println(command.getName() + "_" + base64String);
    }
}


