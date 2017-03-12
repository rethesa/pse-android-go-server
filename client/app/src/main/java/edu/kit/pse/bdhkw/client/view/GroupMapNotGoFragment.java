package edu.kit.pse.bdhkw.client.view;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * Created by Schokomonsterchen on 15.01.2017.
 */

public class GroupMapNotGoFragment extends GroupMapFragment {
    @Override
    protected View defineView(LayoutInflater inflater, ViewGroup container) {
        View view;

        if (goStatus()) {
            getFragmentManager().beginTransaction()
                    .replace(edu.kit.pse.bdhkw.R.id.group_container, new GroupMapGoFragment())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            view = inflater.inflate(edu.kit.pse.bdhkw.R.layout.group_map_go_fragment, container, false);
        } else {
            view = inflater.inflate(edu.kit.pse.bdhkw.R.layout.group_map_not_go_fragment, container, false);
            /*
            if(getIntent() != null){
                getActivity().stopService(getIntent());
            }*/
        }
        return view;
    }

    @Override
    protected void go(MapView mapView) {

        getGroup().activateGoService(this.getActivity());
        //starte den go intent service


        GroupMapGoFragment groupMapGoFragment = new GroupMapGoFragment();
        groupMapGoFragment.setActuallView(((GeoPoint) mapView.getMapCenter()), mapView.getZoomLevel());
        getFragmentManager().beginTransaction()
                .replace(edu.kit.pse.bdhkw.R.id.group_container, groupMapGoFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

}
