package edu.kit.pse.bdhkw.client.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.osmdroid.views.MapView;

/**
 * Created by Schokomonsterchen on 13.01.2017.
 */

public class GroupMapGoFragment extends GroupMapFragment {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void createNavigationDrawer() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) this.getActivity().findViewById(edu.kit.pse.bdhkw.R.id.drawer_layout);
        mDrawerList = (ListView) this.getActivity().findViewById(edu.kit.pse.bdhkw.R.id.left_drawer);
        // Set the adapter for the list view
        super.addDrawerItem();
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new GroupMapFragment.DrawerItemClickListener());

        mTitle = mDrawerTitle = getActivity().getTitle();
        mDrawerLayout = (DrawerLayout) this.getActivity().findViewById(edu.kit.pse.bdhkw.R.id.drawer_layout);
        super.setDrawer();
    }

    @Override
    protected View defineView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(edu.kit.pse.bdhkw.R.layout.group_map_go_fragment, container, false);
    }

    @Override
    protected void go(MapView mapView) {
        GroupMapNotGoFragment groupMapNotGoFragment = new GroupMapNotGoFragment();
        groupMapNotGoFragment.setActuallView(mapView.getMapCenter(), mapView.getZoomLevel());
        getFragmentManager().beginTransaction()
                .replace(edu.kit.pse.bdhkw.R.id.group_container, groupMapNotGoFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

}