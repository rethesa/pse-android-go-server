package edu.kit.pse.bdhkw.client.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import edu.kit.pse.bdhkw.client.communication.BroadcastGpsRequest;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.Response;
import edu.kit.pse.bdhkw.client.communication.SerializableLinkedList;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.model.GoIntentService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GpsObject;
import edu.kit.pse.bdhkw.client.model.objectStructure.SerializableLocation;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.RESPONSE_TAG;

/**
 * Created by Schokomonsterchen on 13.01.2017.
 */

public class GroupMapGoFragment extends GroupMapFragment {

    private BroadcastReceiver broadcastReceiver;
    private static final String TAG = GroupMapGoFragment.class.getSimpleName();


    //private IBinder mbinder;

    @Override
    protected View defineView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(edu.kit.pse.bdhkw.R.layout.group_map_go_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        startService();
    }

    @Override
    protected void go(MapView mapView) {
        // wofür ist das?
        getGroup().deactivateGoService(this.getActivity());
        Log.d(TAG + " FUUUUUUUUUUUUUU", "gruppenstatus von mir ist: " + getGroup().getGoService().getGoStatus());
        //stoppt den go intent service
        //getActivity().stopService(getIntent());

        if(getActivity().stopService(new Intent(getActivity().getApplicationContext(), GoIntentService.class))){
            Log.d(TAG, "stopen war erfolgreich");
        } else {
            Log.d(TAG, "stopen war NICHT erfolgreich");

        }

        /*
        if(intent != null){
            getActivity().stopService(intent);
        }
        */
        //getActivity().unbindService(mServerConn);
        //wechselt fragment
        GroupMapNotGoFragment groupMapNotGoFragment = new GroupMapNotGoFragment();
        groupMapNotGoFragment.setActuallView(((GeoPoint) mapView.getMapCenter()), mapView.getZoomLevel());
        getFragmentManager().beginTransaction()
                .replace(edu.kit.pse.bdhkw.R.id.group_container, groupMapNotGoFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        onDestroy();
    }

    /*
    protected ServiceConnection mServerConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };
    */

    @Override
    protected void startService() {
        setMyLocation();
        Log.e(TAG, "started service");

        Intent intent = new Intent(getActivity(), GoIntentService.class);
        intent.putExtra("key", getGroup().getGroupName());
        String deviceID = Settings.Secure.getString(getActivity().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        intent.putExtra("ID", deviceID);
        Log.e("key", intent.getExtras().getString("key"));
        Log.e("ID", intent.getExtras().getString("ID"));


        //getActivity().bindService(intent, mServerConn, Context.BIND_AUTO_CREATE);
        getActivity().startService(intent);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IntentFilter intentFilter = new IntentFilter(NetworkIntentService.BROADCAST_RESULT + "_" + BroadcastGpsRequest.class.getSimpleName());
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Response response = intent.getParcelableExtra(RESPONSE_TAG);
                try {
                    boolean successful = response.getSuccess();
                    Log.i(TAG, String.valueOf(successful));
                    if(successful) {
                        ObjectResponse objectResponse = (ObjectResponse) response;
                        SerializableLinkedList<HashMap> gpsobjectList = (SerializableLinkedList<HashMap>) objectResponse.getObject("gps_list");

                        ObjectMapper o = new ObjectMapper();
                        o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        ListIterator<HashMap> iterator = gpsobjectList.listIterator();

                        LinkedList<GpsObject> gpsObjectsfinal = new LinkedList<>();

                        while(iterator.hasNext()){
                            String json = o.writeValueAsString(iterator.next());
                            Log.d("TAREK", json);
                            SerializableLocation gps = o.readValue(json.getBytes(), SerializableLocation.class);
                            GpsObject obj = new GpsObject(new Date(gps.getTimestamp()), gps.toGeoPoint());
                            gpsObjectsfinal.add(obj);
                        }

                        setMyGroupMemberLocation(gpsObjectsfinal);

                        //setMyGroupMemberLocation(/*(LinkedList<GpsObject>)*/ linkedList);
                        /*
                        SerializableLinkedList<HashMap> serializableMembers =
                                (SerializableLinkedList<HashMap>) objectResponse.getObject("member_list");

                        ObjectMapper o = new ObjectMapper();
                        ListIterator<HashMap> iterator = serializableMembers.listIterator();

                        while(iterator.hasNext()) {
                            String json = o.writeValueAsString(iterator.next());
                            SerializableMember member = o.readValue(json.getBytes(), SerializableMember.class);

                         */


                        Log.i(TAG, "Aktualisierung war erfolgreich");
                    } else {
                        Log.e(TAG, "failed Broadcast Receiver");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, intentFilter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    /*
    private GoIntentService myServiceBinder;
    public ServiceConnection myConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder binder) {
            myServiceBinder = ((GoIntentService.MyBinder) binder).getService();
            Log.d("ServiceConnection","connected");
            //showServiceData();
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d("ServiceConnection","disconnected");
            myServiceBinder = null;
        }
    };

    public Handler myHandler = new Handler() {
        public void handleMessage(Message message) {
            Bundle data = message.getData();
        }
    };

    public void doBindService() {
        Intent intent = new Intent(this.getContext(), GoIntentService.class);
        // Create a new Messenger for the communication back
        // From the Service to the Activity
        Messenger messenger = new Messenger(myHandler);
        intent.putExtra("MESSENGER", messenger);

        getActivity().bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStart() {
        Log.d("activity", "onResume");
        if (myServiceBinder == null) {
            doBindService();
        }
        super.onStart();

    }

    @Override
    public void onStop() {
        //FIXME put back

        Log.d("activity", "onPause");
        if (myServiceBinder != null) {
            getActivity().unbindService(myConnection);
            myServiceBinder = null;
        }
        super.onStop();
    }
    */

    // ---------- GO service -----------




}