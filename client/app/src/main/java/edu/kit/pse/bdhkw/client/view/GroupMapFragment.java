package edu.kit.pse.bdhkw.client.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import edu.kit.pse.bdhkw.BuildConfig;
import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Schokomonsterchen on 10.01.2017.
 */

public class GroupMapFragment extends Fragment implements View.OnClickListener {

    //navigation drawer


    private MapView mapView;
    private double latitude = 0;
    private double longitude = 0;
    private int zoom = 0;
    private Context ctx = null;
    private GroupClient group;
    private Button groupName;
    private Button groupAppointment;
    //getActivity().getApplicationContext();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = defineView(inflater, container);

        if (container != null) {
            container.removeAllViews();
        }

        defineGroup(view);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);

        //important! set your user agent to prevent getting banned from the osm servers
        ctx = this.getActivity().getApplicationContext();  // ??
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));


        mapView = (MapView) view.findViewById(edu.kit.pse.bdhkw.R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        mapView.setMultiTouchControls(true);
        //mapView.setBuiltInZoomControls(true);
        mapView.setClickable(true);

        IMapController controller = mapView.getController();

        if (zoom == 0) {
            controller.setZoom(15);
        } else {
            controller.setZoom(zoom);
        }

        if (latitude == 0 && longitude == 0) {
            controller.setCenter(getActuallPosition());
        } else {
            //GeoPoint bla = new GeoPoint(latitude, longitude)
            controller.setCenter(new GeoPoint(latitude, longitude));
        }

        mapView.invalidate();

        view.findViewById(edu.kit.pse.bdhkw.R.id.groupname_button).setOnClickListener(this);
        if (defined() && admin()) {
            view.findViewById(edu.kit.pse.bdhkw.R.id.appointment_button).setOnClickListener(this);
        } else {
            view.findViewById(edu.kit.pse.bdhkw.R.id.go_button).setOnClickListener(this);
        }

        // navigation drawer


        return view;
    }

    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
    }




    protected View defineView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(edu.kit.pse.bdhkw.R.layout.group_map_not_go_fragment, container, false);
    }


    /**
     * identify the actuall GeoPoint
     *
     * @return actuall GeoPoint of the client
     */
    private GeoPoint getActuallPosition() {
        //TODO: vom GPS den Standpunkt ermitteln
        return new GeoPoint(49.013941, 8.404409);
    }

    /**
     * handles the clicks from the buttons
     *
     * @param view describes the view of the fragment
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(!this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).
                getString(getString(R.string.groupname), "").equals("")) {
            if (edu.kit.pse.bdhkw.R.id.groupname_button == id) {
                getFragmentManager().beginTransaction()
                        .replace(edu.kit.pse.bdhkw.R.id.group_container, new GroupMembersFragment())
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else if (edu.kit.pse.bdhkw.R.id.appointment_button == id) {
                getFragmentManager().beginTransaction()
                        .replace(edu.kit.pse.bdhkw.R.id.group_container, new GroupAppointmentFragment())
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else if (edu.kit.pse.bdhkw.R.id.go_button == id) {
                go(mapView);
            }
        }
    }

    protected void go(MapView mapView) {
        //TODO:
    }

    private void defineGroup(View view) {
        groupName = (Button) view.findViewById(R.id.groupname_button);
        groupAppointment = (Button) view.findViewById(R.id.appointment_button);
        String name = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).
                getString(getString(R.string.groupname), "");
        if(!defined()) {
            groupName.setText("");
            groupAppointment.setText("");
            setActuallView(getActuallPosition(), 15);
        } else {
            GroupService groupService = new GroupService(getActivity().getApplicationContext());
            group = groupService.readOneGroupRow(name);
            groupName.setText(group.getGroupName());
            groupAppointment.setText(group.getAppointment().getAppointmentDestination().getDestinationName());
            setActuallView(group.getAppointment().getAppointmentDestination().getDestinationPosition(), 15);
        }
    }

    public void setActuallView(GeoPoint geoPoint, int newZoom) {
        latitude = geoPoint.getLatitude();
        longitude = geoPoint.getLongitude();
        zoom = newZoom;
    }

    private int getUserId() {
        SharedPreferences preferences = this.getActivity().
                getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        int defaultUserId = -1;
        int userId = preferences.getInt(getString(R.string.sharedUserId), defaultUserId);
        return userId;
    }

    protected boolean goStatus() {
        return group.getGoService().getGoStatus();
    }

    private boolean defined() {
        return !this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).
                getString(getString(R.string.groupname), "").equals("");
    }

    private boolean admin() {
        return group.getMemberType(this.getActivity(), getUserId());
    }


}