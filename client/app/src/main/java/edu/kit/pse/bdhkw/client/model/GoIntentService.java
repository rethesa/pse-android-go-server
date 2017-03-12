package edu.kit.pse.bdhkw.client.model;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.osmdroid.util.GeoPoint;

import java.util.Date;

import edu.kit.pse.bdhkw.client.communication.BroadcastGpsRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GpsObject;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.REQUEST_TAG;

/**
 * Created by Tarek on 13.01.17.
 */



public class GoIntentService extends IntentService {
    private static final String TAG = "BOOMBOOMTESTGPS";

    private final int positionActualizationInMS = 15000;
    private GroupClient group;
    private boolean set = false;
    private String deviceID;// = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    private GpsObject ownLocation;// = startService(new Intent(getApplicationContext(), GpsService.class));
    private GpsService gps;

    // intent service ---------------------------------
    public GoIntentService() {
        super(GoIntentService.class.getSimpleName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.e("onHandleIntent", "bla");
        // INSERT THE WORK TO BE DONE HERE
        setGroup(intent);
        // Do actual work
        dowork(intent);

    }
    // intent service ---------------------------------

    private void dowork(Intent intent) {
        //synchronized (this) {
        if (!set) {
            if (intent.hasExtra("key")) {
                setGroup(intent);
            } else {
                stopSelf();
            }
        }
        while (group.getGoService().getGoStatus()) {
            try {
                //gps = new GpsService(getApplicationContext());
                gps = new GpsService(getApplicationContext());

                //notifyAll();
                Log.i(TAG, group.getGroupName());
                if (gps != null && gps.getLocation() != null) {
                    if (gps.canGetLocation()) {
                        ownLocation = new GpsObject(new Date(), new GeoPoint(gps.getLatitude(), gps.getLocation().getLongitude()));
                    } else {
                        Log.e(TAG, "failed getting gps");
                    }
                    sendRequest();
                }
                //wait(positionActualizationInMS);
                //TimeUnit.SECONDS.wait(15);
                gps.stopUsingGPS();
                setGroup(intent);

                Thread.sleep(positionActualizationInMS);
            } catch (InterruptedException e) {
                Log.e("GOINTENTSERVICE", "wOOPS da ging was schief mit dem senden..");
                gps.stopUsingGPS();
                e.printStackTrace();
            }
        }
        stopSelf();
        //}

    }

    private void sendRequest() {
        Log.i(TAG, "Versucht senden");

        BroadcastGpsRequest broadcastGpsRequest = new BroadcastGpsRequest();
        broadcastGpsRequest.setStatusGo(group.getGoService().getGoStatus());
        //broadcastGpsRequest.setCoordinates(ownLocation);
        broadcastGpsRequest.setCoordinates(ownLocation);
        broadcastGpsRequest.setSenderDeviceId(deviceID);
        broadcastGpsRequest.setTargetGroupName(group.getGroupName());


        Intent intent = new Intent(getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, broadcastGpsRequest);
        getApplicationContext().startService(intent);
        Log.e("after", "sending");

    }

    private void setGroup(Intent intent) {
        Log.e("setGroup", "lese aus");

        GroupService groupService = new GroupService(getApplicationContext());
        //GroupService groupService = new GroupService(getApplicationContext());
        //groupnameKey = intent.getExtras().getString("key");
        group = groupService.readOneGroupRow(intent.getExtras().getString("key"));

        deviceID = intent.getExtras().getString("ID");
        Log.e("key_service", group.getGroupName());
        Log.e("ID_service", deviceID);
        set = true;
    }

}
