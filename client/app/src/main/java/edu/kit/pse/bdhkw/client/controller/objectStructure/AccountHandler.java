package edu.kit.pse.bdhkw.client.controller.objectStructure;

import edu.kit.pse.bdhkw.client.controller.database.ServiceAllocation;
import edu.kit.pse.bdhkw.client.controller.database.ServiceAppointment;
import edu.kit.pse.bdhkw.client.controller.database.ServiceGroup;
import edu.kit.pse.bdhkw.client.controller.database.ServiceUser;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.common.model.UserComponent;

/**
 * hält Informationen darüber wer ich bin
 */

public class AccountHandler {

    private SimpleUser simpleUser;

    private ServiceUser sUser;
    private ServiceAllocation sAlloc;
    private ServiceGroup sGroup;
    private ServiceAppointment sApp;

    /**
     * Register a new user. Create a new simple user object and save this user on user.db.
     * @param userName name of the user for registration
     * @param userId unique 9 digit integer starting with 2 or more.
     */
    public void registerUser(String userName, int userId) {
        simpleUser = new SimpleUser(userName, userId);
        //to add to user.db we need a position as well
        sUser.insertUserData(simpleUser);
    }

    /**
     * User leaves all groups he is member of so all databases of the user will be deleted.
     * @param user to be deleted.
     */
    public void deleteUserAccount(UserComponent user) {
        sAlloc.deleteAllAllocations();
        sGroup.deleteAllGroups();
        sApp.deleteAllAppointments();
        sUser.deleteAllUsers();
    }

}
