package edu.kit.pse.bdhkw.client.model;

import android.app.Activity;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.EditText;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.communication.BroadcastGpsRequest;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.Response;
import edu.kit.pse.bdhkw.client.communication.UpdateRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GpsObject;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.REQUEST_TAG;

/**
 * Created by Tarek on 13.01.17.
 */

public class GoIntentService extends IntentService {
    private GroupClient group;
    private GroupService groupService;
    private GpsObject actualPosition;
    private MyLocationNewOverlay mLocationOverlay;
    private MapView map;

    private boolean set = false;

    private int positionActualizationInMS = 15000;
    private static final String TAG = GoIntentService.class.getSimpleName();
    //ContentResolver context = this.getContentResolver();
    private String deviceID;// = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);



    public GoIntentService() {
        super(GoIntentService.class.getSimpleName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            if(!set) {
                if (intent.hasExtra("key") == true) {
                    setGroup(intent);
                } else {
                    this.stopSelf();
                }
            }
            while (group.getGoService().getGoStatus()) {
                try {
                    Log.i(TAG, group.getGroupName());
                    sendRequest();
                    wait(positionActualizationInMS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setGroup(Intent intent) {
        groupService = new GroupService(getApplicationContext());
        GroupService groupService = new GroupService(getApplicationContext());
        group = groupService.readOneGroupRow(intent.getExtras().getString("key"));
        deviceID = intent.getExtras().getString("ID");
       // map = new MapView();//intent.getExtras().getParcelable("Map");

        set = true;
    }

    private void sendRequest() {
        Log.i(TAG, "print some cool stuff");


        this.mLocationOverlay = new

                MyLocationNewOverlay(new GpsMyLocationProvider(this.getBaseContext()), this.map);
        this.mLocationOverlay.enableMyLocation();
        this.mLocationOverlay.enableFollowLocation();
        this.mLocationOverlay.setDrawAccuracyEnabled(true);
        this.mLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                actualPosition.setLongitude(mLocationOverlay.getMyLocation().getLongitude());
                actualPosition.setLatitude(mLocationOverlay.getMyLocation().getLatitude());
            }
        });

        BroadcastGpsRequest broadcastGpsRequest = new BroadcastGpsRequest();
        broadcastGpsRequest.setSenderDeviceId(deviceID);
        broadcastGpsRequest.setTargetGroupName(group.getGroupName());
        broadcastGpsRequest.setStatusGo(group.getGoService().getGoStatus());
        broadcastGpsRequest.setCoordinates(actualPosition);

        Intent intent = new Intent(this, NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, broadcastGpsRequest);
        this.startService(intent);
    }
}



/*
package edu.kit.pse.bdhkw.client.model;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;


 // Created by Tarek on 13.01.17.


public class GoIntentService extends IntentService {
    private GroupClient group;

    public GoIntentService() {
        super(GoIntentService.class.getSimpleName());
    }
    public GoIntentService(GroupClient group) {
        super(GoIntentService.class.getSimpleName());
        this.group = group;
    }

    public void activate(Context context) {
        // send messages to the server in periodic intervals
        Intent intent = new Intent(context, GoIntentService.class);
        context.startService(intent);

        // TODO:
    }

    public void deactivate() {
        // Stop polling
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}

*/
