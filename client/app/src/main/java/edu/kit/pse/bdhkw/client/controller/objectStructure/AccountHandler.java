package edu.kit.pse.bdhkw.client.controller.objectStructure;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.RegistrationRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserComponent;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.REQUEST_TAG;
import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.RESPONSE_TAG;

/**
 * hält Informationen darüber wer ich bin
 */

public class AccountHandler {

    private SimpleUser simpleUser;

    private UserService userService;
    private GroupService groupService;

    /**
     * Register a new user. Create a new simple user object.
     * @param userName name of the user for registration
     */
    public void registerUser(Activity activity, String userName) {
        userService = new UserService(activity.getApplicationContext());
        String deviceId = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.i(AccountHandler.class.getSimpleName(), "deviceId = " + deviceId);
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setSenderDeviceId(deviceId);
        Log.i(AccountHandler.class.getSimpleName(), "bis vor NetworkIntentService");
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, registrationRequest);
        activity.startService(intent);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ObjectResponse objResp = intent.getParcelableExtra(RESPONSE_TAG);
                boolean suc = objResp.getSuccess();
                Log.i(AccountHandler.class.getSimpleName(), String.valueOf(suc));
            }
        };

        //simpleUser = new SimpleUser(userName, userId);
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
