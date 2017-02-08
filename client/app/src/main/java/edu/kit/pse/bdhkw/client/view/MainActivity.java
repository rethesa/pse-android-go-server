package edu.kit.pse.bdhkw.client.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import edu.kit.pse.bdhkw.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final String defaultregistered = "a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.kit.pse.bdhkw.R.layout.main_activitiy);

        //SharedPreferences prefs = this.getSharedPreferences("edu.kit.pse.bdhkq.client", Context.MODE_PRIVATE);
        //String isregistered = "edu.kit.pse.bdhkq.client.registered_file_key";

        //String defaultregistered = defaultregistered;
        //if(prefs.edit().putBoolean("registered" ,true).commit()){
            // write successfull
        //}

        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //SharedPreferences prefs = getApplicationContext().getSharedPreferences(
         //       getString(R.string.preference_file_key), Context.MODE_PRIVATE);


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