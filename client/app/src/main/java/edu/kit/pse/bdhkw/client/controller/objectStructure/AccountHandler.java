package edu.kit.pse.bdhkw.client.controller.objectStructure;

import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserComponent;

/**
 * hält Informationen darüber wer ich bin
 */

public class AccountHandler {

    private SimpleUser simpleUser;

    private UserService sUser;
    private GroupService sGroup;

    /**
     * Register a new user. Create a new simple user object.
     * @param userName name of the user for registration
     * @param userId unique 9 digit integer starting with 2 or more.
     */
    public void registerUser(String userName, int userId) {
        simpleUser = new SimpleUser(userName, userId);
    }

    /**
     * User leaves all groups he is member of so all databases of the user will be deleted.
     * @param user to be deleted.
     */
    public void deleteUserAccount(UserComponent user) {
        sGroup.deleteAllGroups();
        sUser.deleteAllUserAndGroups();
    }

}
