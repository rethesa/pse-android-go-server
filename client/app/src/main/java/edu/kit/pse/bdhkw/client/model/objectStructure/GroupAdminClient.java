package edu.kit.pse.bdhkw.client.model.objectStructure;

/**
 * This class represents an admin of a group.
 * @author Theresa Heine
 * @version 1.0
 */
public class GroupAdminClient extends UserDecoratorClient {

    /**
     * Constructor of the group admin.
     * @param name of the admin
     * @param userId of the admin
     */
    public GroupAdminClient(String name, int userId) {
        super(name, userId);
        isAdmin = true;
    }

    @Override
    public boolean getView() {
        return true;
    }
}
