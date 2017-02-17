package edu.kit.pse.bdhkw.client.view;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.objectStructure.AccountHandler;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

import static android.content.Context.MODE_PRIVATE;
import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.RESPONSE_TAG;

/**
 * Created by Schokomonsterchen on 10.01.2017.
 */

public class UsernameRegistrationFragment extends Fragment implements View.OnClickListener {

    private EditText username;
    private AccountHandler accountHandler;
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;
    private static final String TAG = UsernameRegistrationFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(edu.kit.pse.bdhkw.R.layout.username_registration_fragment, container, false);
        username = (EditText) view.findViewById(edu.kit.pse.bdhkw.R.id.input_edittext);
        view.findViewById(edu.kit.pse.bdhkw.R.id.next_registration_button).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (edu.kit.pse.bdhkw.R.id.next_registration_button == view.getId()) {
            if (isUsernameValid()) {
                accountHandler = new AccountHandler();
                accountHandler.registerUser(this.getActivity());
            }
        }
    }


    /*private void savePreferences(String value) {
        SharedPreferences prefs = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.username), value);
        editor.commit();
    }*/

    private void saveSharedPreferences(String nameValue, int idValue) {
        //TODO noch testen ob das klappt
        SharedPreferences preferences = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.sharedUserName), nameValue);
        editor.putInt(getString(R.string.sharedUserId), idValue);
    }


    /**
     * check if username is valid
     *
     * @return if username is valid
     */
    private boolean isUsernameValid() {
        //TODO: entscheide was als valide giltund prüfen
        //name nicht leer etc. etc.
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        intentFilter = new IntentFilter(NetworkIntentService.BROADCAST_RESULT);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ObjectResponse objResp = intent.getParcelableExtra(RESPONSE_TAG);
                try {
                    boolean successful = objResp.getSuccess();
                    Log.i(TAG, String.valueOf(successful));
                    if(successful) {
                        String userName = username.getText().toString();
                        int userId = (int) objResp.getObject("user_id");

                        SimpleUser simpleUser = new SimpleUser(userName, userId);
                        saveSharedPreferences(simpleUser.getName(), simpleUser.getUserID());
                        Toast.makeText(context, getString(R.string.registrationSuccessful), Toast.LENGTH_SHORT).show();
                        getActivity().startActivity(new Intent(getActivity(), GroupActivity.class));
                    } else {
                        Toast.makeText(context, getString(R.string.registrationNotSuccessful), Toast.LENGTH_SHORT).show();
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