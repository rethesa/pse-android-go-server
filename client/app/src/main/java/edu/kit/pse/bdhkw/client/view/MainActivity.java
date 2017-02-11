package edu.kit.pse.bdhkw.client.view;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.controller.objectStructure.GroupHandler;
import edu.kit.pse.bdhkw.client.model.database.DBHelperGroup;
import edu.kit.pse.bdhkw.client.model.database.FeedReaderContract;
import edu.kit.pse.bdhkw.client.model.objectStructure.Appointment;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupMemberClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    protected GroupService groupService;
    protected UserService userService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.kit.pse.bdhkw.R.layout.main_activitiy);

        //DATENBANK TESTZEUGS; DASS GEHÖRT HIER ÜBERHAUPT NICHT REIN

        /*groupService = new GroupService(this);
        userService = new UserService(this);

        List<UserDecoratorClient> listGroup1 = new LinkedList<>();
        List<UserDecoratorClient> listGroup2 = new LinkedList<>();

        SimpleUser user = new SimpleUser("Theresa",  1111);
        UserDecoratorClient user1 = new GroupMemberClient(user.getUserName(), user.getUserID());
        UserDecoratorClient user2 = new GroupMemberClient("Victoria", 2222);
        UserDecoratorClient user3 = new GroupMemberClient("Tarek", 3333);
        UserDecoratorClient user4 = new GroupMemberClient("Dennis", 4444);

        listGroup1.add(user1);
        listGroup1.add(user2);
        listGroup1.add(user3);

        listGroup2.add(user1);
        listGroup2.add(user4);

        GroupClient group1 = new GroupClient("Gruppe1", "11.10.2017", "00:00", "mensa", listGroup1);
        group1.getAppointment().getAppointmentDestination().setDestinationPosition(49.013941, 8.404409);

        GroupClient group2 = new GroupClient("Gruppe2", "14.03.95", "16:00", "Brauerstraße 19" ,listGroup2);
        group2.getAppointment().getAppointmentDestination().setDestinationPosition(50.11, 20.44);

        groupService.deleteAllGroups();

        groupService.insertNewGroup(group1);
        groupService.insertNewGroup(group2);

        Cursor cursor = groupService.readOneGroupRow(group1.getGroupName());

        String name = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryGroup.COL_GROUP_NAME));
        int goStatus= cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntryGroup.COL_GO_STATUS));
        String appDest = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_DEST));

        Log.i("read is working", name + " " + goStatus + " " + appDest);

        userService.deleteAllUserAndGroups();

        userService.insertUserData(group1.getGroupName(), user1);
        userService.updateGroupMemberToAdmin(group1.getGroupName(), user1);
        userService.insertUserData(group1.getGroupName(), user2);
        userService.insertUserData(group1.getGroupName(), user3);

        userService.insertUserData(group2.getGroupName(), user1);
        userService.insertUserData(group2.getGroupName(), user4);
        userService.updateGroupMemberToAdmin(group2.getGroupName(), user4);

        String oldName = group1.getGroupName();
        group1.changeGroupName("SuperCooleGruppe");
        userService.updateGroupNameInAlloc(oldName, group1.getGroupName());

        int result = userService.readAdminOrMemberStatus(group1.getGroupName(), user1.getUserID());
        Log.i("read is working", result  + "");

        List<String> list = userService.readAllGroupMembers(group1.getGroupName());
        Log.i("read is working", list.get(0) + list.get(1) + list.get(2));
        */

        //DATENBANK TESTZEUGS ENDET HIER

        if(isUnregistered()) {
            Intent intent = new Intent(this, UsernameActivity.class);
            intent.putExtra("OpenFirstTime", "true");
            startActivity(new Intent(intent));
        } else if(!isUnregistered()) {
            startActivity(new Intent(this, GroupActivity.class));
        }

    }

    private boolean isUnregistered() {
        //TODO: openFirstTime muss irgendwo anders gespeichert sein und geholt werden
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

}