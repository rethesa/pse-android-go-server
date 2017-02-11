package edu.kit.pse.bdhkw.client.model.objectStructure;

/**
 * Created by Theresa on 20.12.2016.
 */

/**
 * This class represents a user as a normal member of a group.
 */
public class GroupMemberClient extends UserDecoratorClient {



    public GroupMemberClient(String name, int userID) {
        super(name, userID);
    }

    @Override
    public boolean getView() {
        return false;
    }

}
