package application.client;

import application.events.IResponseListener;
import application.models.*;
import application.network.SocketClient;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class AdminViewController implements IResponseListener {
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField idField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField accountIdField;
    @FXML
    private TextField branchIdField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private RadioButton shiftManagerRadio;
    @FXML
    private RadioButton cashierRadio;
    @FXML
    private RadioButton sellerRadio;

    @FXML
    private ListView<User> usersList;
    private Gson gson;

    public void setUsersList() {
        gson = new Gson();
        SocketClient.sendMessage(new GetAllUsersCommand(), this);
    }

    @Override
    public void onResponseReceived(String response) {
        var users = gson.fromJson(response, User[].class);
        if (users != null) {
            ObservableList<User> observableUsers = FXCollections.observableArrayList(users);
            usersList.setItems(observableUsers);
            usersList.setCellFactory(new Callback<>() {
                @Override
                public ListCell<User> call(ListView<User> listView) {
                    return new UserCell();
                }
            });
        }
    }

    @FXML
    protected void onAddUserClick() {
        if (!isValidUser()) {
            return;
        }

        var userToAdd = new User(
                fullNameField.getText(),
                idField.getText(),
                phoneField.getText(),
                accountIdField.getText(),
                Integer.parseInt(branchIdField.getText()),
                getEmployeeId(),
                usernameField.getText(),
                passwordField.getText(),
                getRole());

        clearInputFields();
        usersList.getItems().add(userToAdd);
        SocketClient.sendMessage(new AddUserCommand(userToAdd), null);
    }

    private int getEmployeeId()
    {
        var users = usersList.getItems();
        int maxId = -1;
        for (var user:users)
        {
            if(user.getEmployeeId() > maxId)
            {
                maxId = user.getEmployeeId();
            }
        }
        return maxId + 1;
    }

    private boolean isValidUser()
    {
        return !isNullOrEmpty(fullNameField.getText())
                && !isNullOrEmpty(idField.getText())
                && !isNullOrEmpty(phoneField.getText())
                && !isNullOrEmpty(accountIdField.getText())
                && !isNullOrEmpty(branchIdField.getText())
                && !isNullOrEmpty(usernameField.getText())
                && !isNullOrEmpty(passwordField.getText())
                && (shiftManagerRadio.isSelected() || cashierRadio.isSelected() || sellerRadio.isSelected());
    }

    private void clearInputFields() {
        fullNameField.clear();
        idField.clear();
        phoneField.clear();
        accountIdField.clear();
        branchIdField.clear();
        usernameField.clear();
        passwordField.clear();
        shiftManagerRadio.setSelected(false);
        cashierRadio.setSelected(false);
        sellerRadio.setSelected(false);
    }

    private boolean isNullOrEmpty(String str)
    {
        return str == null || str.isEmpty();
    }

    private String getRole() {
        if (this.shiftManagerRadio.isSelected()) {
            return "shiftManager";
        }
        if (this.cashierRadio.isSelected()) {
            return "cashier";
        }
        if (this.sellerRadio.isSelected()) {
            return "seller";
        }
        return "NotValidRole";
    }

    private class UserCell extends ListCell<User> {
        private final Button removeButton;

        public UserCell() {
            removeButton = new Button("Remove");
            removeButton.setOnAction(event -> {
                User user = getItem();
                if (user != null) {
                    usersList.getItems().remove(user);
                    var removeUser = new RemoveUserModel(user.getId());
                    SocketClient.sendMessage(new RemoveUserCommand(removeUser), null);
                }
            });
        }

        @Override
        protected void updateItem(User user, boolean empty) {
            super.updateItem(user, empty);

            if (empty || user == null) {
                setGraphic(null);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("FullName: " + user.getFullName());
                stringBuilder.append("\nId: " + user.getId());
                stringBuilder.append("\nPhone: " + user.getPhone());
                stringBuilder.append("\nAccount: " + user.getAccountId());
                stringBuilder.append("\nBranchId: " + user.getBranchId());
                stringBuilder.append("\nEmployeeId: " + user.getEmployeeId());
                stringBuilder.append("\nRole: " + user.getRole());
                stringBuilder.append("\nUsername: " + user.getUsername());
                stringBuilder.append("\nPassword: ******");

                removeButton.setDisable(false);
                setGraphic(new VBox(new Label(stringBuilder.toString()), removeButton));
            }
        }
    }
}