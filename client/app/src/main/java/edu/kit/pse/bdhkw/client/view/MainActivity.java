package edu.kit.pse.bdhkw.client.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.osmdroid.util.GeoPoint;

import java.util.LinkedList;
import java.util.List;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.controller.objectStructure.AccountHandler;
import edu.kit.pse.bdhkw.client.model.database.FeedReaderContract;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupMemberClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final String defaultregistered = "a";

    protected GroupService groupService;
    protected UserService userService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.kit.pse.bdhkw.R.layout.main_activitiy);

        //MEINE TESTAUFRUFE STARTEN HIER

        GroupService groupService = new GroupService(this);
        //UserService userService = new UserService(this);

        //userService.deleteAllUserAndGroups();

        /*List<UserDecoratorClient> list = new LinkedList<>();
        list = null;
        GeoPoint geoPoint = new GeoPoint(50.11, 20.44);
        GroupClient groupClient = new GroupClient("Blödsinngruppe", "14.02.2017", "14:00", "Mensa", geoPoint, list);
        AccountHandler accountHandler = new AccountHandler();
        accountHandler.registerUser(this, "Theresa");*/


        //groupClient.createInviteLink(this);
        //groupClient.getGroupUpdate(this);
        //groupClient.changeGroupName(this, "Scheiße");


        //groupService.deleteAllGroups();
        //groupService.insertNewGroup(groupClient);

        //UserDecoratorClient user = new GroupAdminClient("Theresa", 1111);
        //userService.insertUserData(groupClient.getGroupName(), user);
        //boolean bool = groupClient.getMemberType(this, user.getUserID());

        //String deviceId = Settings.Secure.getString(this.getApplicationContext().getContentResolver(),
        //Settings.Secure.ANDROID_ID);

        //Log.i("read is working", deviceId);


        //GroupClient returnGroup = groupService.readOneGroupRow(groupClient.getGroupName());
        //Log.i("read is working", returnGroup.getGroupName() + returnGroup.getAppointment().getAppointmentDate().getDate());

        //Log.i("read is working", String.valueOf(bool));  */


        //MEINE TESTAUFRUFE ENDEN HIER

        //TODO join group?! beim beitreten der gruppe

        if(!loadPreference().equals("")) {
            startActivity(new Intent(this, GroupActivity.class));
        } else {
            //prefs.edit().putBoolean("registered", true);
            Intent intent = new Intent(this, UsernameActivity.class);
            intent.putExtra("OpenFirstTime", "true");
            startActivity(new Intent(intent));
        }
    }


    private String loadPreference(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        return prefs.getString(getString(R.string.username), "");
    }

    /*
    private boolean isRegistered(SharedPreferences prefs) {
        return prefs.getBoolean("registered", false);
    }
    */

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