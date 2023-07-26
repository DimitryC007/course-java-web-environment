module application.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens application.models to com.google.gson;

    opens application.client to javafx.fxml;
    exports application.client;
}