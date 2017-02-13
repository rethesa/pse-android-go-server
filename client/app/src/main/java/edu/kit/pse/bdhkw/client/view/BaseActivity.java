package edu.kit.pse.bdhkw.client.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RemoteViews;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

public class BaseActivity extends AppCompatActivity {

    public final static String navigation = "Group navigation";
    private final static String groupNameString = "groupname";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] Groupname;
    private ArrayAdapter<String> mAdapter;
    private ActionBar actionBar;

    private GroupClient group;
    private String groupname;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        group = new GroupClient("");

    }

    public String getCurrentGroup(){
        return groupname;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }





    protected void onCreateDrawer(){

        mDrawerLayout = (DrawerLayout) findViewById(edu.kit.pse.bdhkw.R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(edu.kit.pse.bdhkw.R.id.left_drawer);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        // Set the adapter for the list view
        addDrawerItem();
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // new GroupMapFragmentGo.DrawerItemClickListener()

        mTitle = mDrawerTitle = this.getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(edu.kit.pse.bdhkw.R.id.drawer_layout);

        setDrawer();
    }

    private void setDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                edu.kit.pse.bdhkw.R.string.drawer_open, edu.kit.pse.bdhkw.R.string.drawer_close) {

            //Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                actionBar.setTitle(mDrawerTitle);
                //groupname = mDrawerLayout.toString();
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            //Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                actionBar.setTitle(navigation);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void addDrawerItem(){
        //get groups where user is member or admin
        //TEST:
        //TODO: get real group information
        String[] osArray = {getString(R.string.ownprofil), "Gruppe 1", "Gruppe 2", "Gruppe 3", "Gruppe 4", "Gruppe 5" };

        //Groupname = getGroupname()

        //set the group name into the menu
        //TEST:
        Groupname = osArray;

        //setting adapter
        mAdapter = new ArrayAdapter<String>(this, edu.kit.pse.bdhkw.R.layout.list_item, osArray);
        //mAdapter = new MemberAdapter(bla);
        mDrawerList.setAdapter(mAdapter);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            groupname = Groupname[position];
        }
    }

    // Swaps activities in the main content view
    private void selectItem(int position) {

        // Highlight the selected item, update the title, and close the drawer
        // + 1 wegen dem "mein profil"
        mDrawerList.setItemChecked(position, true);
        setTitle(Groupname[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        if(position == 0){
            Intent intent = new Intent(this, UsernameActivity.class);
            intent.putExtra("OpenFirstTime", "false");
            startActivity(intent);
        } else {
            //name der gruppe übernehmen
            Bundle bundle = new Bundle();
            bundle.putString(groupNameString, Groupname[position]);
            GroupMembersFragment groupmembers = new GroupMembersFragment();
            groupmembers.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.group_container, groupmembers)
                                    .addToBackStack(null)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .commit();

        }
        //TODO: wechsel gruppe auf der map
        //Intent intent = new Intent(this, GroupActivity.class);
        //startActivity(intent);
        /*
        getFragmentManager().beginTransaction()
                .replace(R.id.activity_base, new GroupMembersFragment())
                .addToBackStack(null)
                .commit();
        */

    }

    public void setTitle(CharSequence newtitle) {
        mTitle = newtitle;
        actionBar.setTitle(mTitle);
    }

    //for activating drawer toggle/layout options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
        /*
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
         */
    }

    // activity only?
    //for better syncing, menu becomes fluent
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    //for better syncing, menu becomes fluent
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(mDrawerTitle);

    }
}
