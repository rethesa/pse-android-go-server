package edu.kit.pse.bdhkw.client.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.kit.pse.bdhkw.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Schokomonsterchen on 13.01.2017.
 */

public class UsernameChangeFragment extends Fragment implements View.OnClickListener {

    private TextView oldUsername;
    private EditText username;
    private String name;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (this.getActivity().getIntent().getStringExtra("OpenFirstTime").equals("false")) {
            view = inflater.inflate(R.layout.username_change_fragment, container, false);
            oldUsername = (TextView) view.findViewById(R.id.old_username_textview);
            username = (EditText) view.findViewById(R.id.input_edit_text);
            view.findViewById(R.id.next_change_username_button).setOnClickListener(this);
            oldUsername.setText(getUsername());
        } else if (this.getActivity().getIntent().getStringExtra("OpenFirstTime").equals("true")) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.username_container, new UsernameRegistrationFragment())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            view = inflater.inflate(R.layout.username_registration_fragment, container, false);
        } else {
            return null;
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        if(R.id.next_change_username_button == view.getId()) {
            if(usernameValid()) {
                savePreferences();
                this.getActivity().startActivity(new Intent(this.getActivity(), GroupActivity.class));
                onStop();
            }
        }
    }


    private void savePreferences(){
        SharedPreferences prefs = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.username), name);
        editor.commit();
    }

    private String getUsername() {
        SharedPreferences prefs = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        return prefs.getString(getString(R.string.sharedUserName), "[ERROR]:unknown");
    }

    /**
     * check if username is valid
     * @return if username is valid
     */
    private boolean usernameValid() {
        name = username.getText().toString();
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

    //TODO: delete my account - > account handler
}