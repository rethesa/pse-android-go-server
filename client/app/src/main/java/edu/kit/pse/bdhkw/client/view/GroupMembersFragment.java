package edu.kit.pse.bdhkw.client.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.Response;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.controller.objectStructure.GroupHandler;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.Link;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.RESPONSE_TAG;

/**
 * Created by Schokomonsterchen on 12.01.2017.
 */

public class GroupMembersFragment extends Fragment implements View.OnClickListener {

    private final static String groupNameString = "groupname";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;
    private ShareActionProvider mShareActionProvider;

    private GroupClient groupClient;
    private GroupAdminClient groupAdminClient;
    private GroupService groupService;
    private UserService userService;

    private static final String TAG = GroupMembersFragment.class.getSimpleName();

    private String[] data = {"tarek" , "theresa", "victoria", "matthias", "dennis" , "bla" , "fisch", "alex", "mähhh", "bähhh", "hola", "    DFadf dnöfn "};


    private String groupname;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button gn = (Button) getView().findViewById(edu.kit.pse.bdhkw.R.id.groupname_button);
        gn.setText(groupname);
    }

    public void setbutton(){
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            groupname = bundle.getString(groupNameString, "Mustergruppe.");
        }
        //Toast.makeText(this.getActivity(), groupname, Toast.LENGTH_SHORT).show();

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

        groupname = ((BaseActivity) getActivity()).getGroupname();

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
            groupService = new GroupService(getActivity());
            //groupClient = groupService.readOneGroupRow("kdjfskljdflskdjfkl");
            groupClient = groupService.readOneGroupRow(((BaseActivity) getActivity()).getGroupname());
            groupClient.createInviteLink(getActivity());
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        getActivity().getMenuInflater().inflate(R.menu.main, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) shareItem.getActionProvider();

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plan");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "corresponding link to share");

        mShareActionProvider.setShareIntent(shareIntent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        intentFilter = new IntentFilter(NetworkIntentService.BROADCAST_RESULT);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Response response = intent.getParcelableExtra(RESPONSE_TAG);
                try {
                    boolean successful = response.getSuccess();
                    Log.i(TAG, "success: " + String.valueOf(successful));
                    if(successful) {
                        ObjectResponse objectResponse = (ObjectResponse) response;
                        Link link = (Link) objectResponse.getObject("link");
                        Log.i(TAG, link.toString());
                        Toast.makeText(context, "share link with....", Toast.LENGTH_SHORT).show();
                        // Share link with...
                        /**
                         * TODO funktioniert noch nicht
                         * Intent myIntent = new Intent(Intent.ACTION_SEND);
                         myIntent.setType("text/plain");
                         String shareBody= "Send this invite link.";
                         String shareSub = "http://halloichbineinlink.com";
                         myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                         myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                         startActivity(Intent.createChooser(myIntent, "Share this link using"));
                         */
                    } else {
                        Toast.makeText(context, getString(R.string.sendLinkWasNotSuccessful), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
