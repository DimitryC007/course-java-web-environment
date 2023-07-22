package application.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class StoreManagementApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StoreManagementApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Socket socket = null;
        DataInputStream fromNetInputStream;
        DataInputStream consoleInput;
        PrintStream toNetOutputStream;

        try {
            socket = new Socket("localhost", 5000);
            fromNetInputStream = new DataInputStream(socket.getInputStream());
            toNetOutputStream = new PrintStream(socket.getOutputStream());
            consoleInput = new DataInputStream(System.in);

            while (true) {
                toNetOutputStream.println("");
            }
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            try {
                socket.close();
            } catch (Exception ex) {

            }
        }

        launch();
    }
}