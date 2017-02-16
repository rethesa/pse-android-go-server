package edu.kit.pse.bdhkw.client.view;


import android.app.Activity;
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

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.objectStructure.AccountHandler;

import static android.content.Context.MODE_PRIVATE;
import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.RESPONSE_TAG;

/**
 * Created by Schokomonsterchen on 10.01.2017.
 */

public class UsernameRegistrationFragment extends Fragment implements View.OnClickListener {

    private EditText username;
    private IntentFilter intentFilter;
    BroadcastReceiver broadcastReceiver;

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
                registrate();
            }
        }
    }


    /**
     * save the username and change Activity
     */
    private void registrate() {
        String finalUsername = username.getText().toString();

        savePreferences(finalUsername);

        //simple user will be
        AccountHandler ah = new AccountHandler();
        ah.registerUser(this.getActivity(), finalUsername);
    }

    private void savePreferences(String value) {
        SharedPreferences prefs = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.username), value);
        editor.commit();
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
                boolean suc = objResp.getSuccess();
                Log.i(BroadcastReceiver.class.getSimpleName(), String.valueOf(suc));
                getActivity().startActivity(new Intent(getActivity(), GroupActivity.class));
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