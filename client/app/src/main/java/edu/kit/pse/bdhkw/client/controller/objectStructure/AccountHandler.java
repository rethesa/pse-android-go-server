package edu.kit.pse.bdhkw.client.controller.objectStructure;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

import edu.kit.pse.bdhkw.client.communication.RegistrationRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserComponent;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.REQUEST_TAG;


/**
 * hält Informationen darüber wer ich bin
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
    public void registerUser(Activity activity) {
        String deviceId = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setSenderDeviceId(deviceId);
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, registrationRequest);
        activity.startService(intent);
    }

    /**
     *  groupService = new GroupService(activity.getApplicationContext());
     userService = new UserService(activity.getApplicationContext());
     String deviceId = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
     Settings.Secure.ANDROID_ID);

     UpdateRequest updateRequest = new UpdateRequest();
     updateRequest.setSenderDeviceId(deviceId);
     updateRequest.setTargetGroupName(this.getGroupName());
     Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
     intent.putExtra(REQUEST_TAG, updateRequest);
     activity.startService(intent);
     Log.i(GroupClient.class.getSimpleName(), "kam bis vor receiver");
     BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
    ObjectResponse objectResponse = intent.getParcelableExtra(RESPONSE_TAG);
    String name = (String) objectResponse.getObject("group_name");
    Appointment appointment = (Appointment) objectResponse.getObject("appointment_object");
    LinkedList<String> memberList = (LinkedList<String>) objectResponse.getObject("member_list");

    Log.i(GroupClient.class.getSimpleName(), name );

    }
    };
     */

    /**
     * User leaves all groups he is member of so all databases of the user will be deleted.
     * @param user to be deleted.
     */
    public void deleteUserAccount(UserComponent user) {
        groupService.deleteAllGroups();
        userService.deleteAllUserAndGroups();
    }

}
