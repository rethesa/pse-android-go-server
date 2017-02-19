package edu.kit.pse.bdhkw.client.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.Response;
import edu.kit.pse.bdhkw.client.communication.SerializableInteger;
import edu.kit.pse.bdhkw.client.communication.SerializableLinkedList;
import edu.kit.pse.bdhkw.client.communication.SerializableMember;
import edu.kit.pse.bdhkw.client.communication.SerializableString;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.objectStructure.Appointment;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupMemberClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleAppointment;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.RESPONSE_TAG;

public class BaseActivity extends AppCompatActivity {

    public final static String navigation = "Group navigation";
    private final static String TAG = BaseActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private List<String> groupNameList;
    private ArrayAdapter<String> mAdapter;
    private ActionBar actionBar;
    private int[] counter;
    private BaseActivity activity = this;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;

    private GroupClient group;
    private String groupname;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }


    protected void onCreateDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


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


        for(int i = 0; i < osArray.length; i++){
            groupNameList.add(i+1, osArray[i]);
        }
        //set the group name into the menu
        //TEST:
        //groupNameList = osArray;

        //setting adapter

        mAdapter = new ArrayAdapter<String>(this, edu.kit.pse.bdhkw.R.layout.list_item, groupNameList);
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
            if(position == 0) {
                final Dialog appDialog = new Dialog(activity);
                appDialog.setTitle("leave Dialog");
                appDialog.setContentView(R.layout.leave_app_dialog);
                appDialog.show();

                Button yes = (Button) appDialog.findViewById(R.id.yes_app_button);
                Button no = (Button) appDialog.findViewById(R.id.no_app_button);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO: auf dem server und auf dem client entfernen und aus den Preferences
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
            } else if(position > 0 && position < (Groupname.size() -2)) {
                group = new GroupService(activity.getBaseContext()).readOneGroupRow(Groupname.get(position));
                if(group.getMemberType(activity, getUserId())) {
                    final Dialog dialog = new Dialog(activity);
                    dialog.setTitle(getString(R.string.choose_option) + " " + Groupname.get(position));
                    dialog.setContentView(R.layout.group_dialogue);
                    dialog.show();

                    Button changeGroupname = (Button) dialog.findViewById(R.id.change_groupname_button);
                    Button deleteGroup = (Button) dialog.findViewById(R.id.leave_group_button);

                    changeGroupname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            savePreferences(position);
                            Intent intent = new Intent(activity, GroupnameActivity.class);
                            intent.putExtra("GroupID", Groupname.get(position));
                            activity.startActivity(intent);
                        }
                    });

                    deleteGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_SHORT).show();
                            final Dialog deleteDialog = new Dialog(activity);
                            deleteDialog.setTitle("delete Dialog");
                            deleteDialog.setContentView(R.layout.delete_group_dialogue);
                            deleteDialog.show();

                            Button yes = (Button) deleteDialog.findViewById(R.id.yes_delete_button);
                            Button no = (Button) deleteDialog.findViewById(R.id.no_delete_button);

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //TODO: delete this group
                                    deleteDialog.cancel();
                                }
                            });

                            no.setOnClickListener(new View.OnClickListener() {
        @Override
            if(position != 0 && position != groupNameList.size() - 1){
                groupname = groupNameList.get(position);
                                public void onClick(View view) {
                                    deleteDialog.cancel();
                                }
                            });
                            dialog.cancel();
                        }

                    });
                } else {
                    final Dialog leaveDialog = new Dialog(activity);
                    leaveDialog.setTitle("leave Dialog");
                    leaveDialog.setContentView(R.layout.leave_group_dialogue);
                    leaveDialog.show();

                    Button yes = (Button) leaveDialog.findViewById(R.id.yes_leave_button);
                    Button no = (Button) leaveDialog.findViewById(R.id.no_leave_button);

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            group.leaveGroup(activity, //TODO UserDecoratorClient);
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

            // Send request to server to update group data
            groupname = groupNameList.get(position);
            GroupService groupService = new GroupService(this);
            group = groupService.readOneGroupRow(groupname);
            group.getGroupUpdate(activity);



            Intent intent = new Intent(this, GroupnameActivity.class);
            intent.putExtra("GroupID", "false");
            startActivity(intent);
        }

    }

    public void setTitle(CharSequence newtitle) {
        mTitle = newtitle;
        actionBar.setTitle(mTitle);
    }

    private void savePreferences(int position) {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(getString(R.string.groupname), groupNameList.get(position));
        editor.commit();
    }

    private String getUsername() {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        return prefs.getString(getString(R.string.username), "[ERROR]:unknown");
    }

    private int getUserId() {
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        int defaultUserId = -1;
        int userId = preferences.getInt(getString(R.string.sharedUserId), defaultUserId);
        return userId;
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
                    Log.i(TAG, "UpdateRequest: " + String.valueOf(successful));
                    if(successful) {
                        ObjectResponse objectResponse = (ObjectResponse) response;

                        SerializableString name = (SerializableString) objectResponse.getObject("group_name");
                        String groupName = name.value; //change group name is not possible like this

                        SimpleAppointment appointment = (SimpleAppointment) objectResponse.getObject("appointment");
                        long date = appointment.getDate();
                        Date d = new Date(date);
                        String stringDate = d.getDay() + "." + d.getMonth() + "." + d.getYear();
                        String stringTime = d.getHours() + ":" + d.getMinutes();

                        GroupService groupService = new GroupService(getApplicationContext());
                        GroupClient groupClient = groupService.readOneGroupRow(groupName);
                        UserService userService = new UserService(getApplicationContext());
                        SerializableLinkedList<SerializableMember> serializableMembers =
                                (SerializableLinkedList<SerializableMember>) objectResponse.getObject("member_list");

                        List<Integer> idGroupMemberList = userService.readAllGroupMemberIds(groupName);

                        //SerializableMember member: serializableMembers.iterator()
                        ListIterator<SerializableMember> iterator = serializableMembers.listIterator();
                        while(iterator.hasNext()) {
                            SerializableMember member = iterator.next();
                        //for() {
                            int count = 0;
                            for (int j = 0; j < idGroupMemberList.size(); j++) {
                                if(member.getId() == idGroupMemberList.get(j) ) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                if(member.isAdmin()) {
                                    GroupAdminClient groupAdminClient = new GroupAdminClient(member.getName(), member.getId());
                                    userService.insertUserData(groupName, groupAdminClient);
                                } else {
                                    GroupMemberClient groupMemberClient = new GroupMemberClient(member.getName(), member.getId());
                                    userService.insertUserData(groupName, groupMemberClient);
                                }
                            } else {
                                if(member.isAdmin()) {
                                    GroupAdminClient groupAdminClient = new GroupAdminClient(member.getName(), member.getId());
                                    userService.updateUserName(groupAdminClient);
                                } else {
                                    GroupMemberClient groupMemberClient = new GroupMemberClient(member.getName(), member.getId());
                                    userService.updateUserName(groupMemberClient);
                                }
                            }
                        }

                        groupClient.getAppointment().setAppointmentDate(stringDate, stringTime);
                        groupService.updateGroupData(groupClient.getGroupName(), groupClient);

                        Toast.makeText(context, "update war erfolgreich", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Update war erfolgreich");

                        //nochmal createView aufrufen
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.group_container, new GroupMapNotGoFragment())
                                .addToBackStack(null)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();
                        onStop();
                    } else {
                        Toast.makeText(context, getString(R.string.registrationNotSuccessful), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
        Log.i(TAG, "onAttach()");


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    public String getGroupname() {
        return groupname;
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
        Log.i(TAG, "onStop");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private boolean admin() {
        return false;

    }

}
