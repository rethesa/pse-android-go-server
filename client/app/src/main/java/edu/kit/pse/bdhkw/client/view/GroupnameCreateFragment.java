package edu.kit.pse.bdhkw.client.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import edu.kit.pse.bdhkw.client.controller.objectStructure.GroupHandler;

/**
 * Created by Schokomonsterchen on 13.01.2017.
 */

public class GroupnameCreateFragment extends Fragment implements View.OnClickListener {

    private EditText groupname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(this.getActivity().getIntent().getStringExtra("GroupID").equals("false")) {
            view = inflater.inflate(edu.kit.pse.bdhkw.R.layout.groupname_create_fragment, container, false);
            groupname = (EditText) view.findViewById(edu.kit.pse.bdhkw.R.id.input_edittext);
            view.findViewById(edu.kit.pse.bdhkw.R.id.next_group_button).setOnClickListener(this);
        } else {
            getFragmentManager().beginTransaction()
                    .replace(edu.kit.pse.bdhkw.R.id.groupname_container, new UsernameRegistrationFragment())
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
            if(isGroupnameValid()) {
                createGroup();
            }
        }
    }


    /**
     * save the username and change Activity
     */
    private void createGroup() {
        String finalGroupname = groupname.getText().toString();
        //TODO: save groupname

        GroupHandler groupHandler = new GroupHandler();
        //groupHandler.createGroup(getActivity(), finalGroupname);

        //TODO: delete group
        this.getActivity().startActivity(new Intent(this.getActivity(), GroupActivity.class));
    }


    /**
     * check if username is valid
     * @return if username is valid
     */
    private boolean isGroupnameValid() {
        //TODO: entscheide was als valide giltund prüfen
        //TODO: muss an server geschickt werden
        return true;
    }
}