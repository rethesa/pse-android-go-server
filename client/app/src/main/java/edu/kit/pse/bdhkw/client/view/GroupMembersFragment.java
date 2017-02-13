package edu.kit.pse.bdhkw.client.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import edu.kit.pse.bdhkw.R;

/**
 * Created by Schokomonsterchen on 12.01.2017.
 */

public class GroupMembersFragment extends Fragment implements View.OnClickListener {

    private final static String groupNameString = "groupname";


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private String groupname;

    private String[] data = {"tarek" , "theresa", "victoria", "matthias", "dennis" , "bla" , "fisch", "alex", "mähhh", "bähhh", "hola", "    DFadf dnöfn "};



    public void setbutton(){
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            groupname = bundle.getString(groupNameString, "bla");
        }

        Toast.makeText(this.getActivity(), groupname, Toast.LENGTH_SHORT).show();

        Button button = (Button)getActivity().findViewById(R.id.groupname_button);
        button.setText(groupname);

        /*
        RemoteViews remoteViews;
        if(admin()) {
            remoteViews= new RemoteViews(getActivity().getPackageName(), R.layout.groupmembers_fragment_admin);
        } else {
            remoteViews= new RemoteViews(getActivity().getPackageName(), R.layout.groupmembers_fragment_admin);
        }

        remoteViews.setTextViewText(R.id.groupname_button, groupname);
        */

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if(admin()) {
            view = inflater.inflate(edu.kit.pse.bdhkw.R.layout.groupmembers_fragment_admin, container, false);
        } else {
            view = inflater.inflate(edu.kit.pse.bdhkw.R.layout.groupmembers_fragment, container, false);
        }


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        //performance boost
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MemberAdapter(data);
        mRecyclerView.setAdapter(mAdapter);

        view.findViewById(edu.kit.pse.bdhkw.R.id.groupname_button).setOnClickListener(this);
        if(admin()) {
            view.findViewById(edu.kit.pse.bdhkw.R.id.appointment_button).setOnClickListener(this);
            view.findViewById(edu.kit.pse.bdhkw.R.id.add_member_button).setOnClickListener(this);
        }

        setbutton();

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (edu.kit.pse.bdhkw.R.id.groupname_button == id) {
            Fragment groupMapFragment;
            if (go()) {
                groupMapFragment = new GroupMapGoFragment();
            } else {
                groupMapFragment = new GroupMapNotGoFragment();
            }    getFragmentManager().beginTransaction()
                    .replace(edu.kit.pse.bdhkw.R.id.groupmembers_fragment, groupMapFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (edu.kit.pse.bdhkw.R.id.appointment_button == id) {
            getFragmentManager().beginTransaction()
                    .replace(edu.kit.pse.bdhkw.R.id.groupmembers_fragment, new GroupAppointmentFragment())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (edu.kit.pse.bdhkw.R.id.add_member_button == id) {

        }

    }

    private boolean admin() {
        //TODO: überprüfen, ob dieser Client admin ist
        return true;
    }

    private boolean go() {
        //TODO: überprüfen, ob go gedrückt ist
        return true;
    }

}
