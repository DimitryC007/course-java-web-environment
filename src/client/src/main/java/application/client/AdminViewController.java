package application.client;

import application.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class AdminViewController {
    @FXML
    private ListView<User> usersList;

    public void setUsersList() {
        //TODO: take from users.json
        List<User> users = new ArrayList<>();
        users.add(new User("John Doe", "Admin", "john", "pass123"));
        users.add(new User("Alice Smith", "User", "alice", "pass456"));
        users.add(new User("Bob Johnson", "Manager", "bob", "pass789"));

        ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
        usersList.setItems(observableUsers);
        usersList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<User> call(ListView<User> listView) {
                return new UserCell();
            }
        });
    }

    // Custom cell class to display user information and a remove button
    private class UserCell extends ListCell<User> {
        private final Button removeButton;

        public UserCell() {
            removeButton = new Button("Remove");
            removeButton.setOnAction(event -> {
                User user = getItem();
                if (user != null) {
                    usersList.getItems().remove(user);
                    //TODO: remove from users.json
                }
            });
        }

        @Override
        protected void updateItem(User user, boolean empty) {
            super.updateItem(user, empty);

            if (empty || user == null) {
                setGraphic(null);
            } else {
                String userInfo = "FullName: " + user.getFullName() + ", Role: " + user.getRole() +
                        ", Username: " + user.getUsername() + ", Password: " + user.getPassword();
                removeButton.setDisable(false);
                setGraphic(new VBox(new Label(userInfo), removeButton));
            }
        }
    }
}