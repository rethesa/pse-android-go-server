package edu.kit.pse.bdhkw.client.view;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.controller.objectStructure.AccountHandler;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Schokomonsterchen on 10.01.2017.
 */

public class UsernameRegistrationFragment extends Fragment implements View.OnClickListener {

    private EditText username;

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
        if(edu.kit.pse.bdhkw.R.id.next_registration_button == view.getId()) {
            if(usernameValid()) {
                registrate();
                this.getActivity().startActivity(new Intent(this.getActivity(), GroupActivity.class));
            }
        }
    }


    /**
     * save the username and change Activity
     */
    private void registrate() {
        savePreferences();

        //simple user will be
        AccountHandler accountHandler = new AccountHandler();
        accountHandler.registerUser(this.getActivity(), username.getText().toString());
    }

    private void savePreferences(){
        SharedPreferences prefs = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.username), username.getText().toString());
        editor.commit();
    }

    /**
     * check if username is valid
     * @return if username is valid
     */
    private boolean usernameValid() {
        if(username.getText().toString().equals("")) {
            //TODO: Strings auslagern
            Toast.makeText(getActivity(), "Please choose other name", Toast.LENGTH_SHORT).show();
            return false;
        }
        //TODO: entscheide was als valide gilt und prüfen
        return true;
    }
}