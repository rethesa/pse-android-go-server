package edu.kit.pse.bdhkw.client.model;

import android.content.Intent;

import edu.kit.pse.bdhkw.client.communication.BroadcastGpsRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

/**
 * Created by Schokomonsterchen on 10.02.2017.
 */

public class GoThread extends Thread {
    private GroupClient group;
    private GoIntentService goIntentService;

    public GoThread(GroupClient group, GoIntentService goIntentService) {
        this.group = group;
        this.goIntentService = goIntentService;
    }

    public void run() {
        BroadcastGpsRequest gpsRequest = new BroadcastGpsRequest(thisClient());
        Intent intent = new Intent(goIntentService, NetworkIntentService.class);
        intent.putExtra("gpsRequest", gpsRequest);
        goIntentService.startService(intent);
        //TODO gpsRequest im NetworkIntentService abfangen
    }

    private SimpleUser thisClient() {
        //TODO Theresa fragen, woher ich mich = SimpleUser-Object bekomme
        return new SimpleUser("hi", 0);
    }

}
