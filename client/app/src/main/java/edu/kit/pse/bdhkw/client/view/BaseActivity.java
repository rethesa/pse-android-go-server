package edu.kit.pse.bdhkw.client.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.osmdroid.util.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.communication.DeleteGroupRequest;
import edu.kit.pse.bdhkw.client.communication.DeleteUserRequest;
import edu.kit.pse.bdhkw.client.communication.LeaveGroupRequest;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.Response;
import edu.kit.pse.bdhkw.client.communication.SerializableLinkedList;
import edu.kit.pse.bdhkw.client.communication.SerializableMember;
import edu.kit.pse.bdhkw.client.communication.SerializableString;
import edu.kit.pse.bdhkw.client.communication.UpdateRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GpsObject;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupMemberClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleAppointment;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.REQUEST_TAG;
import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.RESPONSE_TAG;

public class BaseActivity extends AppCompatActivity {

    private final static String navigation = "Group navigation";
    private final static String TAG = BaseActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private List<String> groupNameList;
    private ArrayAdapter<String> mAdapter;
    private ActionBar actionBar;
    //private BaseActivity activity = this;
    private BroadcastReceiver broadcastReceiver;
    private BroadcastReceiver broadcastReceiverDeleteMe;
    private BroadcastReceiver broadcastReceiverLeaveGroup;
    private BroadcastReceiver broadcastReceiverDeleteGroup;
    private int toDeleteGroup = 0;

    private GroupClient group;
    private String groupname;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }

    /**
     * Used for setting the Title in the Toolbar. Sets the title to the newtitle.
     *
     * @param newtitle
     */
    public void setTitle(CharSequence newtitle) {
        mTitle = newtitle;
        actionBar.setTitle(mTitle);
    }

    //for activating drawer toggle/layout options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        /*
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
         */
    }

    // activity only?
    //for better syncing, menu becomes fluent
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    //for better syncing, menu becomes fluent
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

// --Commented out by Inspection START (11.03.17, 16:45):
//    /**
//     * Getter for group name, returns current group name.
//     *
//     * @return String groupname
//     */
//    public String getGroupname(){
//        return this.groupname;
//    }
// --Commented out by Inspection STOP (11.03.17, 16:45)


    /**
     * onCreateDrawer is needed for creating and draws the navigation drawer in the activity and his fragments.
     */
    protected void onCreateDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        } else {
            Log.e(TAG, "actionBar not found");
        }

        // Set the adapter for the list view
        addDrawerItem();
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setOnItemLongClickListener(new DrawerItemLongClickListener());
        // new GroupMapFragmentGo.DrawerItemClickListener()

        mTitle = mDrawerTitle = this.getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setDrawer();
    }

    private void setDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            //Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                actionBar.setTitle(mDrawerTitle);
                //groupname = mDrawerLayout.toString();
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                mAdapter.notifyDataSetChanged();
                setDrawer();
            }

            //Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                actionBar.setTitle(navigation);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void addDrawerItem() {
        //get groups where user is member or admin
        GroupService groupService = new GroupService(this);

        groupNameList = groupService.readAllGroupNames();
        groupNameList.add(0, getString(R.string.welcome) + " " + getUsername());
        groupNameList.add(groupNameList.size(), getString(R.string.addgroup));

        //setting adapter

        mAdapter = new ArrayAdapter<>(this, edu.kit.pse.bdhkw.R.layout.list_item, groupNameList);
        //mAdapter = new MemberAdapter(bla);
        mDrawerList.setAdapter(mAdapter);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            if(position != 0 && position != groupNameList.size() - 1){
                groupname = groupNameList.get(position);
            }
        }
    }

    private class DrawerItemLongClickListener implements ListView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            final String deviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            if(position == 0) {
                final Dialog appDialog = new Dialog(getThisActivity());
                appDialog.setTitle("leave Dialog");
                appDialog.setContentView(R.layout.leave_app_dialog);
                appDialog.show();

                Button yes = (Button) appDialog.findViewById(R.id.yes_app_button);
                Button no = (Button) appDialog.findViewById(R.id.no_app_button);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
                        deleteUserRequest.setSenderDeviceId(deviceID);

                        Intent intent = new Intent(getThisActivity(), NetworkIntentService.class);
                        intent.putExtra(REQUEST_TAG, deleteUserRequest);
                        startService(intent);

                        appDialog.cancel();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appDialog.cancel();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appDialog.cancel();
                    }
                });
            } else if(position > 0 && position < (groupNameList.size() -1)) {
                group = new GroupService(getBaseContext()).readOneGroupRow(groupNameList.get(position));
                if(group.getMemberType(getThisActivity(), getUserId())) {
                    final Dialog dialog = new Dialog(getThisActivity());
                    dialog.setTitle(getString(R.string.choose_option) + " " + groupNameList.get(position));
                    dialog.setContentView(R.layout.group_dialogue);
                    dialog.show();

                    Button changeGroupname = (Button) dialog.findViewById(R.id.change_groupname_button);
                    Button deleteGroup = (Button) dialog.findViewById(R.id.leave_group_button);

                    changeGroupname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            savePreferences(position);
                            Intent intent = new Intent(getThisActivity(), GroupnameActivity.class);
                            intent.putExtra("GroupID", groupNameList.get(position));
                            getThisActivity().startActivity(intent);
                        }
                    });

                    deleteGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_SHORT).show();
                            final Dialog deleteDialog = new Dialog(getThisActivity());
                            deleteDialog.setTitle("delete Dialog");
                            deleteDialog.setContentView(R.layout.delete_group_dialogue);
                            deleteDialog.show();

                            Button yes = (Button) deleteDialog.findViewById(R.id.yes_delete_button);
                            Button no = (Button) deleteDialog.findViewById(R.id.no_delete_button);

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    toDeleteGroup = position;
                                    //LeaveGroupRequest leaveGrouprequest = new LeaveGroupRequest(deviceID);
                                    //leaveGrouprequest.setSenderDeviceId(deviceID);
                                    //leaveGrouprequest.setTargetGroupName(groupNameList.get(position));
                                    DeleteGroupRequest deleteGroupRequest = new DeleteGroupRequest(deviceID);
                                    deleteGroupRequest.setTargetGroupName(groupNameList.get(position));

                                    Intent intent = new Intent(getApplicationContext(), NetworkIntentService.class);
                                    intent.putExtra(REQUEST_TAG, deleteGroupRequest);
                                    startService(intent);
                                    deleteDialog.cancel();
                                }
                            });

                            no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteDialog.cancel();
                                }
                            });
                            dialog.cancel();
                        }
                    });
                } else {
                    final Dialog leaveDialog = new Dialog(getThisActivity());
                    leaveDialog.setTitle("leave Dialog");
                    leaveDialog.setContentView(R.layout.leave_group_dialogue);
                    leaveDialog.show();

                    Button yes = (Button) leaveDialog.findViewById(R.id.yes_leave_button);
                    Button no = (Button) leaveDialog.findViewById(R.id.no_leave_button);

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            group.leaveGroup(activity, //TODO UserDecoratorClient);
                            toDeleteGroup = position;

                            LeaveGroupRequest leaveGroupRequest = new LeaveGroupRequest();
                            leaveGroupRequest.setSenderDeviceId(deviceID);
                            leaveGroupRequest.setTargetGroupName(groupNameList.get(position));

                            Intent intent = new Intent(getApplicationContext(), NetworkIntentService.class);
                            intent.putExtra(REQUEST_TAG, leaveGroupRequest);
                            startService(intent);
                            leaveDialog.cancel();
                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            leaveDialog.cancel();
                        }
                    });
                }
            }
            return true;
        }
    }

    private Activity getThisActivity(){
        return this;
    }

    // Swaps activities in the main content view
    private void selectItem(final int position) {

        // Highlight the selected item, update the title, and close the drawer
        // + 1 wegen dem "mein profil"
        mDrawerList.setItemChecked(position, true);
        setTitle(groupNameList.get(position));
        mDrawerLayout.closeDrawer(mDrawerList);

        if (position == 0) {
            Intent intent = new Intent(this, UsernameActivity.class);
            intent.putExtra("OpenFirstTime", "false");
            //startActivity(intent);
            startActivityForResult(intent, 123);
        } else if (position < (groupNameList.size() - 1)) {
            savePreferences(position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.group_container, new GroupMapNotGoFragment())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            groupname = groupNameList.get(position);
            GroupService groupService = new GroupService(this);
            group = groupService.readOneGroupRow(groupname);
            group.getGroupUpdate(getThisActivity());
        } else if (position == (groupNameList.size() - 1)) {
            // Send request to server to update group data

            Intent intent = new Intent(this, GroupnameActivity.class);
            intent.putExtra("GroupID", "false");
            startActivity(intent);
        }

    }

    private void savePreferences(int position) {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(getString(R.string.groupname), groupNameList.get(position));
        editor.commit();
    }

    private String getUsername() {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        return prefs.getString(getString(R.string.sharedUserName), "[ERROR]:unknown");
    }

    private int getUserId() {
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        int defaultUserId = -1;
        return preferences.getInt(getString(R.string.sharedUserId), defaultUserId);
    }

    @Override
    public void onStart() {
        super.onStart();

        // -------- receiver ---------
        doUpdateWork();

        deleteMe();

        doWorkLeaveGroup();

        doWorkDeleteGroup();
    }

    private void doUpdateWork(){
        IntentFilter intentFilter = new IntentFilter(NetworkIntentService.BROADCAST_RESULT + "_" + UpdateRequest.class.getSimpleName());
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Response response = intent.getParcelableExtra(RESPONSE_TAG);
                try {
                    boolean successful = response.getSuccess();
                    Log.i(TAG, "UpdateRequest: " + String.valueOf(successful));
                    if(successful) {
                        ObjectResponse objectResponse = (ObjectResponse) response;

                        SerializableString name = (SerializableString) objectResponse.getObject("group_name");
                        String groupName = name.value; //change group name is not possible

                        SimpleAppointment appointment = (SimpleAppointment) objectResponse.getObject("appointment");
                        long date = appointment.getDate();
                        Date d = new Date(date);
                        String stringDate = d.getDay() + "." + d.getMonth() + "." + d.getYear();
                        String stringTime = d.getHours() + ":" + d.getMinutes();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        String string  = dateFormat.format(d);

                        Log.d(TAG, string);
                        //Log.d(TAG, stringTime);
                        Log.d(TAG, "--------------------------");
                        Log.d(TAG, d.toString());

                        GpsObject location = appointment.getDestination();

                        GroupService groupService = new GroupService(getApplicationContext());
                        GroupClient groupClient = groupService.readOneGroupRow(groupName);
                        UserService userService = new UserService(getApplicationContext());
                        SerializableLinkedList<HashMap> serializableMembers =
                                (SerializableLinkedList<HashMap>) objectResponse.getObject("member_list");

                        List<Integer> idGroupMemberList = userService.readAllGroupMemberIds(groupName);
                        ObjectMapper o = new ObjectMapper();
                        //SerializableMember member: serializableMembers.iterator()
                        for (HashMap serializableMember : serializableMembers) {
                            String json = o.writeValueAsString(serializableMember);
                            SerializableMember member = o.readValue(json.getBytes(), SerializableMember.class);
                            //for() {
                            int count = 0;
                            for (int j = 0; j < idGroupMemberList.size(); j++) {
                                if (member.getId() == idGroupMemberList.get(j)) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                if (member.isAdmin()) {
                                    GroupAdminClient groupAdminClient = new GroupAdminClient(member.getName(), member.getId());
                                    userService.insertUserData(groupName, groupAdminClient);
                                } else {
                                    GroupMemberClient groupMemberClient = new GroupMemberClient(member.getName(), member.getId());
                                    userService.insertUserData(groupName, groupMemberClient);
                                }
                            } else {
                                if (member.isAdmin()) {
                                    GroupAdminClient groupAdminClient = new GroupAdminClient(member.getName(), member.getId());
                                    userService.updateUserName(groupAdminClient);
                                } else {
                                    GroupMemberClient groupMemberClient = new GroupMemberClient(member.getName(), member.getId());
                                    userService.updateUserName(groupMemberClient);
                                }
                            }
                        }

                        groupClient.getAppointment().setAppointmentDate(stringDate, stringTime);
                        groupClient.getAppointment().setAppointmentDestination(appointment.getName(), new GeoPoint(location.getLatitude(), location.getLongitude()));
                        groupService.updateGroupData(groupClient.getGroupName(), groupClient);

                        Toast.makeText(context, "update war erfolgreich", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Update war erfolgreich");

                        //nochmal createView aufrufen
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.group_container, new GroupMapNotGoFragment())
                                .addToBackStack(null)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();
                        //update den navigationdrawer

                        //onStop();
                    } else {
                        Toast.makeText(context, "Update war nicht erfolgreich.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    private void deleteMe() {
        IntentFilter intentFilterDeleteMe = new IntentFilter(NetworkIntentService.BROADCAST_RESULT + "_" + DeleteUserRequest.class.getSimpleName());
        broadcastReceiverDeleteMe = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Response response = intent.getParcelableExtra(RESPONSE_TAG);
                try {
                    boolean successful = response.getSuccess();
                    Log.i(TAG, String.valueOf(successful));
                    if (successful) {
                        Toast.makeText(context, "Account löschen erfolgt. Diese kann einen Moment dauern.", Toast.LENGTH_SHORT).show();

                        GroupService groupService = new GroupService(getApplicationContext());
                        groupService.deleteAllGroups();

                        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
                        prefs.edit().clear().commit();
                        Log.i(TAG, "Account gelöscht");

                        System.exit(0);

                    } else {
                        Toast.makeText(context, "Account löschen war nicht erfolgreich.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverDeleteMe, intentFilterDeleteMe);
    }

    private void doWorkLeaveGroup(){
        IntentFilter intentFilterLeaveGroup = new IntentFilter(NetworkIntentService.BROADCAST_RESULT + "_" + LeaveGroupRequest.class.getSimpleName());
        broadcastReceiverLeaveGroup = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Response response = intent.getParcelableExtra(RESPONSE_TAG);
                try {
                    boolean successful = response.getSuccess();
                    Log.i(TAG, String.valueOf(successful));
                    if (successful) {
                        Toast.makeText(context, "Gruppe verlassen erfolgt. Diese kann einen Moment dauern.", Toast.LENGTH_SHORT).show();

                        GroupService groupService = new GroupService(getApplicationContext());
                        groupService.deleteOneGroupRow(groupNameList.get(toDeleteGroup));

                        if(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit().putString(getString(R.string.groupname), "").commit()){
                            Log.d(TAG, "OHHHHH sHITTTTT");
                        }
                                //getString(getString(R.string.groupname), "").equals("")))
                        //setContentView();

                        //onCreateDrawer();
                        //View view1 = findViewById(R.id.drawer_layout);
                        //view1.invalidate();
                        //setDrawer();
                        //close it
                        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                        //mDrawerLayout.closeDrawers();
                        //mDrawerList.deferNotifyDataSetChanged();
                        //mAdapter.notifyDataSetChanged();
                        //Fragment frag = getSupportFragmentManager().findFragmentById(R.id.drawer_layout);
                        //frag.onDetach();
                        //frag.onAttach(getApplicationContext());

                        //TODO neu laden der view sonst gibts exceptions da auf eine gruppe zugegriffen wird die nicht existiert
                        //System.exit(0);

                    } else {
                        Toast.makeText(context, "Gruppe löschen war nicht erfolgreich.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverLeaveGroup, intentFilterLeaveGroup);
    }

    private void doWorkDeleteGroup(){
        IntentFilter intentFilterDeleteGroup = new IntentFilter(NetworkIntentService.BROADCAST_RESULT + "_" + DeleteGroupRequest.class.getSimpleName());
        broadcastReceiverDeleteGroup = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Response response = intent.getParcelableExtra(RESPONSE_TAG);
                try {
                    boolean successful = response.getSuccess();
                    Log.i(TAG, String.valueOf(successful));
                    if (successful) {
                        Toast.makeText(context, "Löschen der Gruppe erfolgt. Diese kann einen Moment dauern.", Toast.LENGTH_SHORT).show();

                        GroupService groupServic = new GroupService(getApplicationContext());
                        groupServic.deleteOneGroupRow(groupNameList.get(toDeleteGroup));

                        mAdapter.notifyDataSetChanged();

                        if(getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit().putString(getString(R.string.groupname), "").commit()){
                            Log.d(TAG, "OHHHHH sHITTTTT");
                            group = null;
                        }
                        //getSupportFragmentManager().getFragments().clear();
                        /*
                        mDrawerLayout.closeDrawers();
                        mAdapter.notifyDataSetChanged();
                        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                        mDrawerList.deferNotifyDataSetChanged();

                        onPause();
                        onResume();
                        */

                        //View view = findViewById(R.id.drawer_layout);
                        //view.invalidate();

                        Log.i(TAG, "Gruppe gelöscht");

                    } else {
                        Toast.makeText(context, "Gruppe löschen war nicht erfolgreich.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverDeleteGroup, intentFilterDeleteGroup);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(mDrawerTitle);
    }

    @Override
    public void onStop() {
        super.onStop();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiverDeleteMe);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiverLeaveGroup);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiverDeleteGroup);

        Log.i(TAG, "onStop()");
    }

}
