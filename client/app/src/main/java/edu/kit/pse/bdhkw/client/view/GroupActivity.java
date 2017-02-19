package edu.kit.pse.bdhkw.client.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import org.osmdroid.util.GeoPoint;

import java.util.Date;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.communication.JoinGroupRequest;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.Response;
import edu.kit.pse.bdhkw.client.communication.SerializableLinkedList;
import edu.kit.pse.bdhkw.client.communication.SerializableString;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupMemberClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.Link;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleAppointment;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.REQUEST_TAG;
import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.RESPONSE_TAG;

/**
 * Created by Schokomonsterchen on 12.01.2017.
 */

public class GroupActivity extends BaseActivity {

    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;
    private static final String TAG = GroupActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity_dynamisch);
        super.onCreateDrawer();
        setContentView(edu.kit.pse.bdhkw.R.layout.group_activity_dynamisch);

        //Deep linking
        Intent intent = getIntent();
        //String action = intent.getAction();
        Uri data = intent.getData();
        if(data != null && data.isHierarchical()){
            String uri = this.getIntent().getDataString();
            String[] groupAndLink  = parseMyGroupLink(uri);

            //Request
            JoinGroupRequest rq = new JoinGroupRequest();
            //rq.setTargetGroupName(groupAndLink[0]);
            String deviceId = Settings.Secure.getString(this.getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            rq.setSenderDeviceId(deviceId);
            rq.setLink(new Link("url", groupAndLink[0], groupAndLink[1]));
            Intent intent1 = new Intent(this.getApplicationContext(), NetworkIntentService.class);
            intent1.putExtra(REQUEST_TAG, rq);
            this.startService(intent1);
        }


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(edu.kit.pse.bdhkw.R.id.group_container, new GroupMapNotGoFragment()).commit();
        }
    }

    private String[] parseMyGroupLink(String link){
        //https://i43pc164.ipd.kit.edu/PSEWS1617GoGruppe3/server/GoAppServer/Dgjff/d49a0e31-a22f-4952-934b-40b2fea896a8
        String groupAndLinkString = link.replaceFirst("https://i43pc164.ipd.kit.edu/PSEWS1617GoGruppe3/server/GoAppServer/","");
        String[] groupAndLink = groupAndLinkString.split("/");

        return groupAndLink;
    }
    

    @Override
    public void onStart() {
        super.onStart();
        intentFilter = new IntentFilter(NetworkIntentService.BROADCAST_RESULT);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Response response = intent.getParcelableExtra(RESPONSE_TAG);
                try {
                    boolean successful = response.getSuccess();
                    Log.i(TAG, String.valueOf(successful));
                    if(successful) {
                        ObjectResponse objectResponse = (ObjectResponse) response;
                        SerializableString groupname = (SerializableString) objectResponse.getObject("group_name");
                        SerializableLinkedList<UserDecoratorClient> memberlist =  (SerializableLinkedList<UserDecoratorClient>) objectResponse.getObject("member_list");
                        SimpleAppointment appointment = (SimpleAppointment) objectResponse.getObject("appointment");
                        long date = appointment.getDate();
                        Date d = new Date(date);
                        String stringDate = d.getDay() + "." + d.getMonth() + "." + d.getYear();
                        String stringTime = d.getHours() + ":" + d.getMinutes();


                        GeoPoint geoPoint = new GeoPoint(appointment.getDestination().getLongitude(), appointment.getDestination().getLatitude());
                        GroupClient groupClient = new GroupClient(groupname.getValue(), stringDate, stringTime,"NotOnServer", geoPoint);

                        GroupService groupService = new GroupService(getApplicationContext());
                        groupService.insertNewGroup(groupClient);
                        UserService userService = new UserService(getApplicationContext());

                        // member list rein schreiben in die tabelle
                        for(int i = 0; i < memberlist.size(); i++){
                            if(memberlist.get(i).isAdmin()){
                                GroupAdminClient groupAdminClient = new GroupAdminClient(memberlist.get(i).getName(), memberlist.get(i).getUserID());
                                userService.insertUserData(groupname.getValue(), groupAdminClient);
                            } else {
                                GroupMemberClient groupMemberClient = new GroupMemberClient(memberlist.get(i).getName(), memberlist.get(i).getUserID());
                                userService.insertUserData(groupname.getValue(), groupMemberClient);
                            }
                        }

                        onStop();
                    } else {
                        Toast.makeText(context, "Link öffnen war nicht erfolgreich", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onStop();
        Log.i(TAG, "onStop()");
    }
}