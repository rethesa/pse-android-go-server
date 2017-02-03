package edu.kit.pse.bdhkw.client.model.objectStructure;

/**
 * Created by Theresa on 20.12.2016.
 */

public class GroupAdminClient extends UserDecoratorClient {

    public GroupAdminClient(String name, int userId) {
        super(name, userId);
        isAdmin = true;
    }

    @Override
    public boolean getView() {
        return false;
    }
}
