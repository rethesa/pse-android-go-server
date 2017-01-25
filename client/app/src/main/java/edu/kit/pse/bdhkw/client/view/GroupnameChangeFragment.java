package edu.kit.pse.bdhkw.client.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Schokomonsterchen on 13.01.2017.
 */

public class GroupnameChangeFragment extends Fragment implements View.OnClickListener {

    private TextView oldGroupname;
    private EditText groupname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(edu.kit.pse.bdhkw.R.layout.groupname_change_fragment, container, false);
        oldGroupname = (TextView) view.findViewById(edu.kit.pse.bdhkw.R.id.old_groupname_textview);
        groupname = (EditText) view.findViewById(edu.kit.pse.bdhkw.R.id.input_edit_text);
        view.findViewById(edu.kit.pse.bdhkw.R.id.next_change_groupname_button).setOnClickListener(this);

        oldGroupname.setText(getUsername());
        return view;
    }

    @Override
    public void onClick(View view) {
        if(edu.kit.pse.bdhkw.R.id.next_change_groupname_button == view.getId()) {
            if(isGroupnameValid()) {
                changeGroupname();
            }
        }
    }


    /**
     * save the username and change Activity
     */
    private void changeGroupname() {
        String finalGroupname = groupname.getText().toString();
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
    private boolean isGroupnameValid() {
        //TODO: entscheide was als valide gilt und prüfen
        return true;
    }
}