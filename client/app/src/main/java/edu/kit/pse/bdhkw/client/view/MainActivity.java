package edu.kit.pse.bdhkw.client.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.controller.objectStructure.GroupHandler;
import edu.kit.pse.bdhkw.client.model.database.DBHelperGroup;
import edu.kit.pse.bdhkw.client.model.objectStructure.Appointment;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupMemberClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    protected GroupService groupService;
    protected UserService userService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.kit.pse.bdhkw.R.layout.main_activitiy);


        groupService = new GroupService(this);
        userService = new UserService(this);


       /* List<UserDecoratorClient> mitgliederListe = new LinkedList<>();
        UserDecoratorClient userDecoratorClient = new GroupMemberClient("Theresa", 1234);
        mitgliederListe.add(userDecoratorClient);
        GroupClient groupClient = new GroupClient("Gruppe1", "11.22.3333", "00:00", "mensa", mitgliederListe);

        GroupHandler groupHandler = new GroupHandler();
        groupHandler.createGroup("SuperGruppe");
*/

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