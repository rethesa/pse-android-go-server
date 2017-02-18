package edu.kit.pse.bdhkw.client.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import edu.kit.pse.bdhkw.R;

import static android.content.Context.MODE_PRIVATE;

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

        oldGroupname.setText(this.getActivity().getIntent().getStringExtra("GroupID"));
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
        savePreferences();
        //TODO: save groupname & lege neue Gruppe an
        //String finalGroupname = groupname.getText().toString();
        this.getActivity().startActivity(new Intent(this.getActivity(), GroupActivity.class));
    }



    /**
     * check if username is valid
     * @return if username is valid
     */
    private boolean isGroupnameValid() {
        if(groupname.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private void savePreferences(){
        SharedPreferences prefs = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.groupname), groupname.getText().toString());
        editor.commit();
    }


}