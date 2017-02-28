package edu.kit.pse.bdhkw.client.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.fasterxml.jackson.databind.deser.Deserializers;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import edu.kit.pse.bdhkw.BuildConfig;
import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.model.objectStructure.GpsObject;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Schokomonsterchen on 10.01.2017.
 */

public class GroupMapFragment extends Fragment implements View.OnClickListener {

    //navigation drawer


    private static final String TAG = GroupMapFragment.class.getSimpleName();
    protected MapView mapView;
    private double latitude = 0;
    private double longitude = 0;
    private int zoom = 0;
    private Context ctx = null;
    protected GroupClient group;
    private Button groupName;
    private Button groupAppointment;
    private MyLocationNewOverlay mLocationOverlay;
    private IMapController controller;


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

        controller = mapView.getController();

        if (zoom == 0) {
            controller.setZoom(15);
        } else {
            controller.setZoom(zoom);
        }

        if (latitude == 0 && longitude == 0) {
            controller.setCenter(getActuallPosition());
        } else {
            controller.setCenter(new GeoPoint(latitude, longitude));
        }

        mapView.invalidate();

        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getActivity()), this.mapView);
        this.mLocationOverlay.enableMyLocation();
        //this.mLocationOverlay.enableFollowLocation();
        this.mLocationOverlay.setDrawAccuracyEnabled(true);
        this.mLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                controller.animateTo(mLocationOverlay
                        .getMyLocation());
            }
        });

        if(defined()) {
            if (admin()) {
                view.findViewById(edu.kit.pse.bdhkw.R.id.appointment_button).setOnClickListener(this);
            }
            view.findViewById(edu.kit.pse.bdhkw.R.id.go_button).setOnClickListener(this);
            view.findViewById(edu.kit.pse.bdhkw.R.id.groupname_button).setOnClickListener(this);
        }
        startServiceRename(); //start service not possible because MapView cast to Parcelable
        setMyNextMeeting();
        // ------------------------ TEST ---------------------------
        setMyGroupMemberLocation(new LinkedList<GpsObject>());

        //LinkedList<GpsObject> test = new LinkedList<>();
        //GpsObject a = new GpsObject(new Date(134)), new GeoPoint(49.013513, 8.4022463)
        //test.add();

        return view;
    }

    /**
     * handles the clicks from the buttons
     *
     * @param view describes the view of the fragment
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(defined()) {
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

    public void setActuallView(GeoPoint geoPoint, int newZoom) {
        latitude = geoPoint.getLatitude();
        longitude = geoPoint.getLongitude();
        zoom = newZoom;
    }

    public void setMyLocation(boolean bool){
        if(bool == true){
            mapView.getOverlays().add(this.mLocationOverlay);
            mapView.invalidate();
        }
    }



    public void setMyGroupMemberLocation(LinkedList<GpsObject> locations){
        //poimaker nimmt maker entgegen
        /*
        RadiusMarkerClusterer poiMarkers = new RadiusMarkerClusterer(this.getActivity());

        //setting icons
        Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_cluster);
        Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();
        poiMarkers.setIcon(clusterIcon);


        //making markers and adding them to poimarkers
        Marker marker = new Marker(mapView);
        for(int i = 0; i < locations.size(); i++){
            marker.setPosition(new GeoPoint(locations.get(i).getLatitude(), locations.get(i).getLongitude()));
            poiMarkers.add(marker);
        }

        //adding overlay to map
        mapView.getOverlays().add(poiMarkers);
        mapView.invalidate();
        */

        //------------ TEST -----------------

        Marker start = new Marker(mapView);
        start.setPosition(new GeoPoint(49.0139, 8.4044));

        Marker a = new Marker(mapView);
        a.setPosition(new GeoPoint(49.012941, 8.404409));

        Marker b = new Marker(mapView);
        b.setPosition(new GeoPoint(49.013744, 8.404305));


        //poimaker nimmt maker entgegen
        RadiusMarkerClusterer poiMarkers = new RadiusMarkerClusterer(this.getActivity());

        //setting icons
        Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_cluster);
        Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();
        poiMarkers.setIcon(clusterIcon);

        //adding markers
        poiMarkers.add(start);
        poiMarkers.add(a);
        poiMarkers.add(b);

        //adding overlay to map
        mapView.getOverlays().add(poiMarkers);
        mapView.invalidate();

    }


    private void setMyNextMeeting(){
        //der killt manchmal
        if(defined()){
            GeoPoint geoPoint = new GeoPoint(group.getAppointment()
                    .getAppointmentDestination().getDestinationPosition()
                    .getLatitude(),
                    group.getAppointment().getAppointmentDestination()
                            .getDestinationPosition().getLongitude()
            );
            Marker meeting = new Marker(mapView);
            meeting.setPosition(geoPoint);
            mapView.getOverlays().add(meeting);
            //Log.d("BLAA", "----------------------------------------------");
        }
        //Log.d("BLAA", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

    }


    //public MyLocationNewOverlay getMyLocation(){
        //return this.mLocationOverlay;
    //}

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

    protected void go(MapView mapView) {

    }

    protected boolean goStatus() {
        if (defined()) {
            GroupService groupService = new GroupService(getActivity().getApplicationContext());
            SharedPreferences preferences = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
            String name = preferences.getString(getString(R.string.groupname), "");
            Log.i(TAG, name);
            group = groupService.readOneGroupRow(name);
            return group.getGoService().getGoStatus();
        } else {
            return false;
        }
    }

    protected void startServiceRename() {

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

    private void defineGroup(View view) {
        groupName = (Button) view.findViewById(R.id.groupname_button);
        groupAppointment = (Button) view.findViewById(R.id.appointment_button);
        String name = this.getActivity()
                .getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE)
                .getString(getString(R.string.groupname), "");
        if(!defined()) {
            groupName.setText("");
            groupAppointment.setText("");
//            setActuallView(getActuallPosition(), 15);
        } else {
            GroupService groupService = new GroupService(getActivity().getApplicationContext());
            group = groupService.readOneGroupRow(name);
            groupName.setText(group.getGroupName());
            groupAppointment.setText(group.getAppointment().getAppointmentDestination().getDestinationName());
//            setActuallView(group.getAppointment().getAppointmentDestination().getDestinationPosition(), 15);
        }
    }

    private int getUserId() {
        SharedPreferences preferences = this.getActivity().
                getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        int defaultUserId = -1;
        int userId = preferences.getInt(getString(R.string.sharedUserId), defaultUserId);
        return userId;
    }

    private boolean defined() {
        if(!(this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).
                getString(getString(R.string.groupname), "").equals(""))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean admin() {
        return group.getMemberType(this.getActivity(), getUserId());
    }

}