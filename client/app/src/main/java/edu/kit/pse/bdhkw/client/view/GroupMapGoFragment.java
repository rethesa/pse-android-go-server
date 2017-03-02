package edu.kit.pse.bdhkw.client.view;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import edu.kit.pse.bdhkw.client.communication.BroadcastGpsRequest;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.Response;
import edu.kit.pse.bdhkw.client.communication.SerializableLinkedList;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.model.GoIntentService;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.RESPONSE_TAG;

/**
 * Created by Schokomonsterchen on 13.01.2017.
 */

public class GroupMapGoFragment extends GroupMapFragment {

    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;
    private static final String TAG = GroupMapGoFragment.class.getSimpleName();
    private final boolean imGo = true;

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
        group.deactivateGoService(this.getActivity());
        //stoppt den go intent service
        //getActivity().stopService(getIntent());
        //wechselt fragment
        GroupMapNotGoFragment groupMapNotGoFragment = new GroupMapNotGoFragment();
        groupMapNotGoFragment.setActuallView(((GeoPoint) mapView.getMapCenter()), mapView.getZoomLevel());
        getFragmentManager().beginTransaction()
                .replace(edu.kit.pse.bdhkw.R.id.group_container, groupMapNotGoFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }

    @Override
    protected void startService() {
        Log.e(TAG, "started service");
        setMyLocation(imGo);
        Intent intent = new Intent(this.getActivity(), GoIntentService.class);
        intent.putExtra("key", group.getGroupName());
        String deviceID = Settings.Secure.getString(this.getActivity().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        intent.putExtra("ID", deviceID);
        this.getActivity().startService(intent);
        Log.e("fuck", "this shit");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        intentFilter = new IntentFilter(NetworkIntentService.BROADCAST_RESULT+ "_" + BroadcastGpsRequest.class.getSimpleName());
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Response response = intent.getParcelableExtra(RESPONSE_TAG);
                try {
                    boolean successful = response.getSuccess();
                    Log.i(TAG, String.valueOf(successful));
                    if(successful) {
                        ObjectResponse objectResponse = (ObjectResponse) response;
                        SerializableLinkedList linkedList = (SerializableLinkedList) objectResponse.getObject("gps_data");
                        setMyGroupMemberLocation(linkedList);
                        Log.i(TAG, "Aktualisierung war erfolgreich");
                    } else {
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