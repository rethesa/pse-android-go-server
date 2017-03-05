package edu.kit.pse.bdhkw.client.controller.objectStructure;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

import edu.kit.pse.bdhkw.client.communication.DeleteUserRequest;
import edu.kit.pse.bdhkw.client.communication.RegistrationRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserComponent;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.REQUEST_TAG;

/**
 * Create and delte own account of the app.
 * @author Theresa Heine
 */

public class AccountHandler {
//"edu.kit.pse.bdhkw.response.USER"

    private SimpleUser simpleUser;

    private UserService userService;
    private GroupService groupService;
    private static final String TAG = AccountHandler.class.getSimpleName();


    /**
     * Register a new user. Send a registration request to server.
     */
    public void registerUser(Activity activity, String userName) {
        String deviceId = getDeviceId(activity);
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setSenderDeviceId(deviceId);
        registrationRequest.setUserName(userName);
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, registrationRequest);
        activity.startService(intent);
    }

    /**
     * User leaves all groups he is member of so all databases of the user will be deleted.
     * @param activity where method is called
     */
    public void deleteUserAccount(Activity activity) {
        String deviceId = getDeviceId(activity);
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setSenderDeviceId(deviceId);
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, deleteUserRequest);
        activity.startService(intent);
        /*
         * TODO in receiver of activity or fragment
         * groupService.deleteAllGroups();
         * userService.deleteAllUserAndGroups();
         */
    }

    /**
     * Removed from method for easier testing.
     * @param activity of the group where coresponding mehtod is called.
     * @return value of device id
     */
    protected String getDeviceId(Activity activity) {
        String deviceId = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }

}
