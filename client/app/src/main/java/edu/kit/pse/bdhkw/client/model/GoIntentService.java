package edu.kit.pse.bdhkw.client.model;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import edu.kit.pse.bdhkw.client.communication.BroadcastGpsRequest;
import edu.kit.pse.bdhkw.client.communication.BroadcastGpsResponse;
import edu.kit.pse.bdhkw.client.communication.Response;
import edu.kit.pse.bdhkw.client.controller.database.ServiceGroup;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

/**
 * Created by Tarek on 13.01.17.
 */

public class GoIntentService extends IntentService {
    private GroupClient group = null;
    private boolean groupIsSet = false;
    private int positionActualizationInMS = 15000;

    public GoIntentService() {
        super(GoIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            if(!groupIsSet) {
                if (intent.hasExtra("groupname") == true) {
                    setGroup(intent);
                } else {
                    this.stopSelf();
                }
            }
            while (group.getGoStatus().getGoStatus()) {
                try {
                    sendRequest();
                    wait(positionActualizationInMS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setGroup(Intent intent) {
        //TODO Theresa fragen, wenn es fertig implementiert ist
        group = new ServiceGroup(getApplicationContext()).readGroupData(intent.getExtras().getString("groupname"));
        groupIsSet = true;
    }

    private void sendRequest() {
        GoThread thread = new GoThread(group, this);
        thread.start();
        //TODO --> get a Response;
        BroadcastGpsResponse gpsResponse = new BroadcastGpsResponse(true);
        if(gpsResponse.getSuccess()) {
            group.setGpsData(gpsResponse.getGpsData());
        }
    }

}
