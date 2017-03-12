package edu.kit.pse.bdhkw.client.model.objectStructure;

/**
 * Interface for User.
 * @author Theresa Heine
 * @version 1.0
 */

public interface UserComponent {

    /**
     * Get the name of the user.
     * @return name
     */
    public String getName();

    /**
     * Get unique id of the user.
     */
    public int getUserID();
}
