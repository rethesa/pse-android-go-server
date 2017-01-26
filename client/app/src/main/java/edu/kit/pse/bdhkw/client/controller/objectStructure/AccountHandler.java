package edu.kit.pse.bdhkw.client.controller.objectStructure;

import android.database.sqlite.SQLiteDatabase;

import edu.kit.pse.bdhkw.client.communication.UserRequest;
import edu.kit.pse.bdhkw.client.controller.database.ServiceAllocation;
import edu.kit.pse.bdhkw.client.controller.database.ServiceGroup;
import edu.kit.pse.bdhkw.client.controller.database.ServiceUser;
import edu.kit.pse.bdhkw.client.model.database.DBHelperAllocation;
import edu.kit.pse.bdhkw.client.model.database.DBHelperAppointment;
import edu.kit.pse.bdhkw.client.model.database.DBHelperGroup;
import edu.kit.pse.bdhkw.client.model.database.DBHelperUser;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserComponent;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;

/**
 * hält Informationen darüber wer ich bin
 */

public class AccountHandler {

    private SimpleUser simpleUser;

    private ServiceUser sUser;
    private ServiceAllocation sAlloc;
    private ServiceGroup sGroup;
    private ServiceAllocation sApp;

    /**
     * Register a new user. Create a new simple user object and also save the device id, that not one
     * user can make infinite accounts. Just one account per device id. This will be checked on
     * the server.
     * @param userName name of the user for registration
     * @param deviceId of his mobile phone
     */
    public void registerUser(String userName, String deviceId) {
        simpleUser = new SimpleUser(userName, deviceId);
        //UserRequest userRequest = new UserRequest(simpleUser);
        sUser.insertUserData(simpleUser);
    }

    /**
     * User leaves all groups he is member of so all databases of the user will be deleted.
     * @param user to be deleted.
     */
    public void deleteUserAccount(UserComponent user) {
        //UserRequest userRequest = new UserRequest(user);
        //sAlloc.deleteAll();
        //sGroup.deleteAll();
        //sApp.deleteAll();
        //sUser.deleteAll();
        //TODO
    }

}
