package edu.kit.pse.bdhkw.client.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.kit.pse.bdhkw.R;


public class ButtonFragment extends Fragment {

    public void setText(String newgroupname){
        Button groupname = (Button) getActivity().findViewById(R.id.groupname_button);
        groupname.setText(newgroupname);
    }

}