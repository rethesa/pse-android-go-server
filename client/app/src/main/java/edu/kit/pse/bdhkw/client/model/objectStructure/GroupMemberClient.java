package edu.kit.pse.bdhkw.client.model.objectStructure;

/**
 * Created by Theresa on 20.12.2016.
 */

/**
 * This class represents a user as a normal member of a group.
 */
public class GroupMemberClient extends UserDecoratorClient {

    /**
     * Instantiate a new group member.
     *
     * @param name the name of the user
     */
    public GroupMemberClient(String name) {
        super(name);
    }

    @Override
    public boolean getView() {
        return false;
    }

}
