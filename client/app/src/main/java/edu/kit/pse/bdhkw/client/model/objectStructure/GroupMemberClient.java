package edu.kit.pse.bdhkw.client.model.objectStructure;

/**
 * This class represents a user as a normal member of a group.
 * @author Theresa Heine
 * @version 1.0
 */
public class GroupMemberClient extends UserDecoratorClient {

    /**
     * Constructor for a member of a group.
     * @param name of the member
     * @param userID of the member
     */
    public GroupMemberClient(String name, int userID) {
        super(name, userID);
    }

    @Override
    public boolean getView() {
        return false;
    }

}
