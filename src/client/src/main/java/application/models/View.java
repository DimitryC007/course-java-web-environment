package application.models;

import javafx.scene.Parent;

import java.io.IOException;

public interface View {
    Parent getContent(User user) throws IOException;
}
