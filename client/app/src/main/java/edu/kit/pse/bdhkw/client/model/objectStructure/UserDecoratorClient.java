package edu.kit.pse.bdhkw.client.model.objectStructure;

/**
 * Decorator for UserComponent when User is treated as a member of a group.
 * @author Theresa Heine
 * @version 1.0
 */

public abstract class UserDecoratorClient implements UserComponent {

    private String userName;
    private int userID;
    protected boolean isAdmin;

    /**
     * Constructor for UserDecoratorClient.
     * @param name of the user.
     * @param userID of the user (unique)
     */
    public UserDecoratorClient(String name, int userID) {
        this.userName = name;
        this.userID = userID;
        this.isAdmin = false;
    }

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public int getUserID() {
        return userID;
    }

    /**
     * Get the view of the app depending on the admin status.
     * @return false
     */
    public boolean getView() {
        return false;
    }

    /**
     * Get the value of the admin status.
     * @return true if user is admin
     */
    public boolean isAdmin(){
        return isAdmin;
    }

}
