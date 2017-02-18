package edu.kit.pse.bdhkw.client.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.LinkedList;

import edu.kit.pse.bdhkw.client.model.objectStructure.GpsObject;

/**
 * Created by Schokomonsterchen on 13.01.2017.
 */

public class GroupMapGoFragment extends GroupMapFragment {

    private String groupname;
    //private MapView map;
    //private MyLocationNewOverlay mLocationOverlay;
    private final boolean imGo = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        groupname = ((BaseActivity) getActivity()).getGroupname();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button gn = (Button) getView().findViewById(edu.kit.pse.bdhkw.R.id.groupname_button);
        gn.setText(groupname);

        setMyLocation(imGo);
        //TODO: liste ist nicht leer, bitt eine holen
        setMyGroupMemberLocation(new LinkedList<GpsObject>());
    }

    @Override
    protected View defineView(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(edu.kit.pse.bdhkw.R.layout.group_map_go_fragment, container, false);
    }

    @Override
    protected void go(MapView mapView) {
        GroupMapNotGoFragment groupMapNotGoFragment = new GroupMapNotGoFragment();
        groupMapNotGoFragment.setActuallView(((GeoPoint) mapView.getMapCenter()), mapView.getZoomLevel());
        getFragmentManager().beginTransaction()
                .replace(edu.kit.pse.bdhkw.R.id.group_container, groupMapNotGoFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

}