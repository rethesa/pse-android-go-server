package edu.kit.pse.bdhkw.client.model;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;

import org.osmdroid.util.GeoPoint;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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



public class GoIntentService extends Service {
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
    private String deviceID;// = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
    private GpsObject ownLocation = new GpsObject();

    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            ownLocation = new GpsObject(new Date(), new GeoPoint(mLastLocation.getLatitude(), mLastLocation.getLongitude()));

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)};

    //Binder was here

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");

        setGroup(intent);

        // Do actual work
        dowork(intent);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
            mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }

        try {
            mLocationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
            mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }

    }


    private void dowork(Intent intent){
        //synchronized (this) {
            if (!set) {
                if (intent.hasExtra("key") == true) {
                    setGroup(intent);
                } else {
                    stopSelf();
                }
            }
            while (group.getGoService().getGoStatus()) {
                try {
                    //notifyAll();
                    Log.i(TAG, group.getGroupName());
                    sendRequest();
                    //wait(positionActualizationInMS);
                    //TimeUnit.SECONDS.wait(15);
                    Thread.sleep(positionActualizationInMS);
                } catch (InterruptedException e) {
                    Log.e("GOINTENTSERVICE", "wOOPS da ging was schief mit dem senden..");
                    e.printStackTrace();
                }
            }
        //}

    }

    private void sendRequest() {
        Log.i(TAG, "Versucht senden");


        BroadcastGpsRequest broadcastGpsRequest = new BroadcastGpsRequest();
        broadcastGpsRequest.setStatusGo(group.getGoService().getGoStatus());
        //broadcastGpsRequest.setCoordinates(ownLocation);
        broadcastGpsRequest.setCoordinates(new GpsObject(new Date(), new GeoPoint(0.0, 0.0)));
        broadcastGpsRequest.setDeviceId(deviceID);
        broadcastGpsRequest.setGroupName(group.getGroupName());

        Log.e("fuck fuck", group.getGroupName() + "," + group.getGoService().getGoStatus());

        Intent intent2 = new Intent(getApplicationContext(), NetworkIntentService.class);
        intent2.putExtra(REQUEST_TAG, broadcastGpsRequest);
        startService(intent2);

        /*
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setSenderDeviceId(deviceID);
        updateRequest.setTargetGroupName(group.getGroupName());

        Intent intent = new Intent(this, NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, updateRequest);
        this.startService(intent);
        */


    }

    private void setGroup(Intent intent){
        groupService = new GroupService(getApplicationContext());
        GroupService groupService = new GroupService(getApplicationContext());
        group = groupService.readOneGroupRow(intent.getExtras().getString("key"));
        deviceID = intent.getExtras().getString("ID");
        set = true;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {

            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
