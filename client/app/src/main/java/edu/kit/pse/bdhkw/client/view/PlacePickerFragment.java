package edu.kit.pse.bdhkw.client.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.kit.pse.bdhkw.R;

import static android.content.Context.MODE_PRIVATE;


public class PlacePickerFragment extends Fragment {

    private MapView map;
    private MyLocationNewOverlay mLocationOverlay;
    private IMapController mapController;
    private ItemizedOverlayWithFocus<OverlayItem> mOverlay;

    private String search;
    private Context ctx;
    private List<Address> addressList = new LinkedList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = defineView(inflater, container);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);
        ctx = this.getActivity().getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        //Context ctx = getApplicationContext();  // ??
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

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

    private View defineView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(edu.kit.pse.bdhkw.R.layout.fragment_place_picker, container, false);
    }

    private void onFind(View view){
        EditText editText = (EditText) getView().findViewById(R.id.search);
        String edittext = editText.getText().toString();

        //String message = editText.getText().toString();
        if(!edittext.equals(search)){
            map.getOverlays().remove(this.mOverlay);
            map.invalidate();
        }

        search = edittext;

        GeocoderNominatim geoc = new GeocoderNominatim("agentname");
        try {
            List<Address> results = geoc.getFromLocationName(search, 10);
            if(results == null || results.isEmpty()){
                Toast.makeText(ctx, "Destination not found.", Toast.LENGTH_SHORT).show();
            } else {
                map.invalidate();
                dowork(results, search);

            }

        } catch (IOException e){
            Toast.makeText(ctx, "Geocoding error.", Toast.LENGTH_SHORT).show();
        }

    }

    private void dowork(List<Address> results, final String name){
        ArrayList<OverlayItem> items = new ArrayList<>();
        addressList = results;
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

        this.mOverlay = new ItemizedOverlayWithFocus<>(getActivity(), items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_LONG).show();
                        return false; // We 'handled' this event.
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        onItemLongPressHelper(index, name);
                        return true;
                    }
                });



        this.map.getOverlays().add(this.mOverlay);
        this.map.invalidate();

    }


    private void onItemLongPressHelper(int index, String searchTitle){
        //TODO ich hoffe das passt so. Bitte überprüfen
        //String groupName = "TODO";
        Address address = addressList.get(index);
        double latitude = address.getLatitude();
        double longitude = address.getLongitude();

        //String addressName = searchTitle;

        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.selectedPlace), searchTitle);
        editor.putLong(getString(R.string.selectedLongitude), Double.doubleToRawLongBits(longitude)); // weil man keinen double abspeichern kann
        editor.putLong(getString(R.string.selectedLatitude), Double.doubleToRawLongBits(latitude)); // weil man keinen double abspeichern kann
        editor.commit();

        getFragmentManager().popBackStackImmediate();
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
