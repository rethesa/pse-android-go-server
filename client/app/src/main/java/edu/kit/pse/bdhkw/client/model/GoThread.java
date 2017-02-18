package edu.kit.pse.bdhkw.client.model;

import android.content.Intent;
import android.provider.Settings;

import edu.kit.pse.bdhkw.client.communication.BroadcastGpsRequest;
import edu.kit.pse.bdhkw.client.communication.UpdateRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.model.GoIntentService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.REQUEST_TAG;

/**
 * Created by Schokomonsterchen on 10.02.2017.
 */

public class GoThread extends Thread {
    private GroupClient group;
    private GoIntentService goIntentService;
    private String deviceID = Settings.Secure.getString(goIntentService.getApplicationContext().getContentResolver(),
            Settings.Secure.ANDROID_ID);;


    public GoThread(GroupClient group, GoIntentService goIntentService) {
        this.group = group;
        this.goIntentService = goIntentService;
    }

    public void run() {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setSenderDeviceId(deviceID);
        updateRequest.setTargetGroupName(group.getGroupName());

        Intent intent = new Intent(goIntentService, NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, updateRequest);
        goIntentService.startService(intent);
    }

    private SimpleUser thisClient() {
        //TODO Theresa fragen, woher ich mich = SimpleUser-Object bekomme
        return new SimpleUser("hi", 0);
    }

}
