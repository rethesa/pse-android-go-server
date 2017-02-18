package edu.kit.pse.bdhkw.client.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.controller.objectStructure.GroupHandler;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Schokomonsterchen on 13.01.2017.
 */

public class GroupnameCreateFragment extends Fragment implements View.OnClickListener {

    private EditText groupname;
    private String name;

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
                createGroup();
            }
        }
    }


    /**
     * save the username and change Activity
     */
    private void createGroup() {
        savePreferences();
        GroupHandler groupHandler = new GroupHandler();
        //groupHandler.createGroup(getActivity(), finalGroupname);
        //TODO: create group
        this.getActivity().startActivity(new Intent(this.getActivity(), GroupActivity.class));
    }


    /**
     * check if username is valid
     * @return if username is valid
     */
    private boolean groupnameValid() {
        name = groupname.getText().toString();
        if(!name.matches("[a-zA-Z0-9äöüÄÖÜ ]")) {
            Toast.makeText(getActivity(), getString(R.string.signs), Toast.LENGTH_SHORT).show();
            return false;
        } else if(name.toString().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.no_name), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            name = name.replaceAll("\\s\\s+"," ");
        }
        return true;
    }

    private void savePreferences(){
        SharedPreferences prefs = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.groupname), name);
        editor.commit();
    }

}