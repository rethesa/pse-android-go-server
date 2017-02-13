package edu.kit.pse.bdhkw.client.view;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.BuildConfig;
import org.osmdroid.bonuspack.location.GeocoderNominatim;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.kit.pse.bdhkw.R;


public class PlacePickerFragment extends Fragment {

    MapView map;
    MyLocationNewOverlay mLocationOverlay;
    CompassOverlay mCompassOverlay;
    private IMapController mapController;
    ItemizedOverlayWithFocus<OverlayItem> mOverlay;

    ScaleBarOverlay mScaleBarOverlay;
    String search;
    Context ctx;
    String edittext;
    Boolean go;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = defineView(inflater, container);
       // View view = inflater.inflate(R.layout.fragment_place_picker, null);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);
        ctx = this.getActivity().getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        //Context ctx = getApplicationContext();  // ??
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setContentView(R.layout.fragment_place_picker);

        map = (MapView) view.findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.setClickable(true);

        //controller
        mapController = map.getController();
        mapController.setZoom(11);

        GeoPoint startPoint = new GeoPoint(49.0157643,8.2698451); // karlsruhe
        mapController.setCenter(startPoint);

        map.invalidate();

        //my location
        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getActivity()), this.map);
        this.mLocationOverlay.enableMyLocation();
        this.mLocationOverlay.enableFollowLocation();
        this.mLocationOverlay.setDrawAccuracyEnabled(true);
        this.mLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                mapController.animateTo(mLocationOverlay
                        .getMyLocation());
            }
        });
        map.getOverlays().add(this.mLocationOverlay);
        map.invalidate();

        //compass
        this.mCompassOverlay = new CompassOverlay(getActivity(), new InternalCompassOrientationProvider(getActivity()), map);
        this.mCompassOverlay.enableCompass();
        this.map.getOverlays().add(this.mCompassOverlay);

        Button button = (Button) view.findViewById(R.id.find);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onFind(v);
            }
        });

        return view;

    }

    protected View defineView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(edu.kit.pse.bdhkw.R.layout.fragment_place_picker, container, false);
    }

    private void onFind(View view){
        //Intent intent = new Intent(this, MainActivity.class);

        EditText editText = (EditText) getView().findViewById(R.id.search);
        edittext = editText.getText().toString();

        //String message = editText.getText().toString();
        if(!edittext.equals(search)){
            map.getOverlays().remove(this.mOverlay);
            map.invalidate();
        }

        search = edittext;

        GeocoderNominatim geoc = new GeocoderNominatim("agentname");
        //bla.getFromLocationName()
        try {
            List<Address> results = geoc.getFromLocationName(search, 10);
            if(results.size() == 0){
                Toast.makeText(ctx, "Destination not found.", Toast.LENGTH_SHORT).show();
            } else {
                map.invalidate();
                dowork(results, search);

            }

        } catch (IOException e){
            Toast.makeText(ctx, "Geocoding error.", Toast.LENGTH_SHORT).show();
        }

    }

    public void setGo(boolean go){
        this.go = go;
    }

    private void dowork(final List<Address> results, String name){

        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        for(int i = 0; i < results.size(); i++){
            Address address = results.get(i);
            String streetname = " " + address.getThoroughfare();
            if (address.getThoroughfare() == null){
                streetname = "";
            }
            String city = " " + address.getLocality();
            if(address.getLocality() == null){
                city = "";
            }
            String country = " " + address.getCountryName();
            if(address.getCountryName() == null){
                country = "";
            }

            String title = "Ergebnisse für: '" + name + "'" + streetname + city + country;
            items.add(new OverlayItem(title, "", new GeoPoint(address.getLatitude(), address.getLongitude())));
        }
        mapController.setCenter(items.get(0).getPoint());
        mapController.setZoom(14);

        this.mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getActivity(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_LONG).show();
                        return true; // We 'handled' this event.
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        Toast.makeText(
                                getActivity(),
                                "Place '" + item.getTitle() + "' (index=" + index
                                        + ") got long pressed", Toast.LENGTH_LONG).show();

                        onItemLongPressHelper(results.get(index));
                        return false;
                    }
                });



        this.map.getOverlays().add(this.mOverlay);
        this.map.invalidate();

    }

    private void onItemLongPressHelper(Address address){
        //TODO: geopoint weiter geben an gruppen objekt
        //TODO: zurück in die map gelangen; go, not go?


        Fragment groupMapFragment;
        if (this.go) {
            groupMapFragment = new GroupMapGoFragment();
        } else {
            groupMapFragment = new GroupMapNotGoFragment();
        }

        getFragmentManager().beginTransaction()
                .replace(edu.kit.pse.bdhkw.R.id.group_container, groupMapFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }


    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(getActivity(), PreferenceManager.getDefaultSharedPreferences(getActivity()));

    }
}
