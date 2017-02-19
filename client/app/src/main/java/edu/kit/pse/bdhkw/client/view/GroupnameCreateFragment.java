package edu.kit.pse.bdhkw.client.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.Response;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;

import edu.kit.pse.bdhkw.client.controller.objectStructure.GroupHandler;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

import static android.content.Context.MODE_PRIVATE;
import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.RESPONSE_TAG;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Schokomonsterchen on 13.01.2017.
 */

public class GroupnameCreateFragment extends Fragment implements View.OnClickListener {

    private EditText groupName;

    private GroupHandler groupHandler;
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;

    private GroupClient groupClient;
    private GroupAdminClient groupAdminClient;
    private GroupService groupService;
    private UserService userService;
    private Activity activity = this.getActivity();

    private static final String TAG = UsernameRegistrationFragment.class.getSimpleName();

    private String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(this.getActivity().getIntent().getStringExtra("GroupID").equals("false")) {
            view = inflater.inflate(edu.kit.pse.bdhkw.R.layout.groupname_create_fragment, container, false);
            groupName = (EditText) view.findViewById(edu.kit.pse.bdhkw.R.id.input_edittext);
            view.findViewById(edu.kit.pse.bdhkw.R.id.next_group_button).setOnClickListener(this);
        } else {
            getFragmentManager().beginTransaction()
                    .replace(edu.kit.pse.bdhkw.R.id.groupname_container, new GroupnameChangeFragment())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            view = inflater.inflate(edu.kit.pse.bdhkw.R.layout.groupname_change_fragment, container, false);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        if(edu.kit.pse.bdhkw.R.id.next_group_button == view.getId()) {
            if(groupnameValid()) {
                GroupHandler groupHandler = new GroupHandler();
                groupHandler.createGroup(getActivity(), name);
            }
        }
    }



    /**
     * check if username is valid
     * @return if username is valid
     */
    private boolean groupnameValid() {
        name = groupName.getText().toString();
        if(name.length() > 20) {
            Toast.makeText(getActivity(), getString(R.string.to_long), Toast.LENGTH_SHORT).show();
            return false;
        } else if(name.equals("")) {
            Toast.makeText(getActivity(), getString(R.string.no_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if(!name.matches("(([a-zA-Z_0-9])+(ä|ö|ü|Ä|Ö|Ü| |ß)*)+")) {
            Toast.makeText(getActivity(), getString(R.string.signs), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            name = name.replaceAll("\\s\\s+"," ");
        }
        return true;
    }

    private String readSharedPreferencesGetUserName() {
        SharedPreferences preferences =this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String userName = preferences.getString(getString(R.string.sharedUserName), "");
        return userName;
    }

    private int readSharedPreferencesGetUserId() {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        int defaultUserId = -1;
        int userId = preferences.getInt(getString(R.string.sharedUserId), defaultUserId);
        return userId;
    }

    private void savePreferences(){
        SharedPreferences prefs = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.groupname), name);
        editor.commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        intentFilter = new IntentFilter(NetworkIntentService.BROADCAST_RESULT);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Response response = intent.getParcelableExtra(RESPONSE_TAG);
                try {
                    boolean successful = response.getSuccess();
                    Log.i(TAG, "success: " + String.valueOf(successful));
                    if(successful) {
                        String groupName = GroupnameCreateFragment.this.name;
                        groupClient = new GroupClient(groupName);
                        // The user who creates the group becomes admin
                        Log.i(TAG, "name: " + readSharedPreferencesGetUserName() + "id: " + readSharedPreferencesGetUserId());
/*                        SharedPreferences preferences = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        String userName = preferences.getString(getString(R.string.username), "");
                        int userID = preferences.getInt(getString(R.string.sharedUserId), 0);
                        groupAdminClient = new GroupAdminClient(userName, userID);
*/                        groupAdminClient = new GroupAdminClient(readSharedPreferencesGetUserName(), readSharedPreferencesGetUserId());
                        // Save group and admin on db
                        groupService = new GroupService(getActivity().getApplicationContext());
                        userService = new UserService(getActivity().getApplicationContext());
                        groupService.insertNewGroup(groupClient); //TODO überprüfen ob null werte für appointment funktionieren
                        userService.insertUserData(groupName, groupAdminClient);
                        savePreferences();

                        Toast.makeText(context, getString(R.string.createGroupSuccessful), Toast.LENGTH_SHORT).show();
                        getActivity().startActivity(new Intent(getActivity(), GroupActivity.class)); //TODO in diejenige Group weiterleiten
                        onDetach();
                    } else {
                        Toast.makeText(context, getString(R.string.createGroupNotSuccessful), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

}