package edu.kit.pse.bdhkw.client.model;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
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
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;

    //private final IBinder mBinder = new MyBinder();
    //private Messenger outMessenger;

    private int positionActualizationInMS = 15000;
    private GroupClient group;
    private GroupService groupService;
    private boolean set = false;
    private String deviceID;// = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    private GpsObject ownLocation;// = startService(new Intent(getApplicationContext(), GpsService.class));
    private GpsService gps;

    //Binder was here

    // intent service ---------------------------------
    public GoIntentService() {
        super(GoIntentService.class.getSimpleName());
    }


    @Override
    protected void onHandleIntent(Intent intent)
    {
        //Intent intent2 = new Intent(getApplicationContext(), GpsService.class);
        //getApplicationContext().startService(intent2);

        gps = new GpsService(getApplicationContext());



        Log.e("onHandleIntent", "bla");
        // INSERT THE WORK TO BE DONE HERE
        setGroup(intent);
        // Do actual work
        dowork(intent);

    }
    // intent service ---------------------------------

    private void dowork(Intent intent){
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
                    //notifyAll();
                    Log.i(TAG, group.getGroupName());
                    if(gps.canGetLocation()){
                        ownLocation = new GpsObject(new Date(), new GeoPoint(gps.getLatitude(), gps.getLongitude()));
                    } else {
                        Log.e(TAG, "failed getting gps");
                    }
                    sendRequest();
                    //wait(positionActualizationInMS);
                    //TimeUnit.SECONDS.wait(15);
                    Thread.sleep(positionActualizationInMS);
                } catch (InterruptedException e) {
                    Log.e("GOINTENTSERVICE", "wOOPS da ging was schief mit dem senden..");
                    gps.stopUsingGPS();
                    e.printStackTrace();
                }
            }
        //}

    }

    private void sendRequest() {
        Log.i(TAG, "Versucht senden");

        //LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Criteria criteria = new Criteria();
        //String provider = locationManager.getBestProvider(criteria, false);
        //Location locationa = locationManager.getLastKnownLocation(provider);


        BroadcastGpsRequest broadcastGpsRequest = new BroadcastGpsRequest();
        broadcastGpsRequest.setStatusGo(group.getGoService().getGoStatus());
        //broadcastGpsRequest.setCoordinates(ownLocation);
        broadcastGpsRequest.setCoordinates(ownLocation);
        broadcastGpsRequest.setSenderDeviceId(deviceID);
        broadcastGpsRequest.setTargetGroupName(group.getGroupName());

        //Log.e("fuck fuck", group.getGroupName() + "," + group.getGoService().getGoStatus());
        //Log.e("fuck fuck fuck", deviceID + "  ID  ");

        Intent intent = new Intent(getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, broadcastGpsRequest);
        getApplicationContext().startService(intent);
        Log.e("after", "sending");

    }

    private void setGroup(Intent intent){
        Log.e("setGroup", "lese aus");

        groupService = new GroupService(getApplicationContext());
        GroupService groupService = new GroupService(getApplicationContext());
        group = groupService.readOneGroupRow(intent.getExtras().getString("key"));
        deviceID = intent.getExtras().getString("ID");
        Log.e("key_service", group.getGroupName());
        Log.e("ID_service", deviceID);
        set = true;
    }


    /*
    @Override
    public IBinder onBind(Intent arg0) {
        Bundle extras = arg0.getExtras();
        Log.d("service","onBind");
        // Get messager from the Activity
        if (extras != null) {
            Log.d("service","onBind with extra");
            outMessenger = (Messenger) extras.get("MESSENGER");
        }
        return mBinder;
    }

    public class MyBinder extends Binder {
        public GoIntentService getService() {
            return GoIntentService.this;
        }
    }

    public void setMyLocation(GpsObject location){
        ownLocation = location;
    }
    */

}



/*
public class GoIntentService extends IntentService {
    private GroupClient group;
    private GroupService groupService;

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
        set = true;
        //TODO: go wird nicht hier aktiviert. Das ist Pfusch!!!
        //group.activateGoService();
    }

    private void sendRequest() {
        Log.i(TAG, "print some cool stuff");
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setSenderDeviceId(deviceID);
        updateRequest.setTargetGroupName(group.getGroupName());

        Intent intent = new Intent(this, NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, updateRequest);
        this.startService(intent);
    }
}
*/
