module application.client {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens application.client to javafx.fxml;
    exports application.client;
}