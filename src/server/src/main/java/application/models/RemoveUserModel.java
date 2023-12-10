package application.models;

import java.io.Serializable;

public class RemoveUserModel {
    String id;

    public RemoveUserModel(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
