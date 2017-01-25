package edu.kit.pse.bdhkw.client.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import edu.kit.pse.bdhkw.R;


/**
 * Created by Schokomonsterchen on 13.01.2017.
 */

public class UsernameChangeFragment extends Fragment implements View.OnClickListener {

    private TextView oldUsername;
    private EditText username;

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
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.username_container, new UsernameRegistrationFragment())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            view = inflater.inflate(R.layout.username_registration_fragment, container, false);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        if(R.id.next_change_username_button == view.getId()) {
            if(isUsernameValid()) {
                changeUsername();
            }
        }
    }


    /**
     * save the username and change Activity
     */
    private void changeUsername() {
        String finalUsername = username.getText().toString();
        //TODO: save username
        this.getActivity().startActivity(new Intent(this.getActivity(), GroupActivity.class));
    }

    private String getUsername() {
        //TODO username laden
        return "Mäh";
    }


    /**
     * check if username is valid
     * @return if username is valid
     */
    private boolean isUsernameValid() {
        //TODO: entscheide was als valide gilt und prüfen
        return true;
    }
}