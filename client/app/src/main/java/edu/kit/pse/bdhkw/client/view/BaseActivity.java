package edu.kit.pse.bdhkw.client.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

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
    private int[] counter;
    private BaseActivity activity = this;

    private GroupClient group;
    private String groupname;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        group = new GroupClient("");

    }

    public String getCurrentGroup() {
        return groupname;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }


    protected void onCreateDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        // Set the adapter for the list view
        addDrawerItem();
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setOnItemLongClickListener(new DrawerItemLongClickListener());
        // new GroupMapFragmentGo.DrawerItemClickListener()

        mTitle = mDrawerTitle = this.getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setDrawer();
    }

    private void setDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

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

    private void addDrawerItem() {
        //get groups where user is member or admin
        //TEST:
        //TODO: get real group information
        String[] osArray = {getString(R.string.welcome) + getUsername(), "Gruppe 1", "Gruppe 2", "Gruppe 3", "Gruppe 4", "Gruppe 5", getString(R.string.addMember)};

        //Groupname = getGroupname()

        //set the group name into the menu
        //TEST:
        Groupname = osArray;

        //setting adapter
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, osArray);
        //mAdapter = new MemberAdapter(bla);
        mDrawerList.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Base Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            groupname = Groupname[position];
        }
    }

    private class DrawerItemLongClickListener implements ListView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if(position > 0 && position < (Groupname.length -1)) {
                savePreferences(position);
                Intent intent = new Intent(activity, GroupnameActivity.class);
                intent.putExtra("GroupID", Groupname[position]);
                activity.startActivity(intent);
            }
            return true;
        }
    }

    // Swaps activities in the main content view
    private void selectItem(final int position) {

        // Highlight the selected item, update the title, and close the drawer
        // + 1 wegen dem "mein profil"
        mDrawerList.setItemChecked(position, true);
        setTitle(Groupname[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        if (position == 0) {
            Intent intent = new Intent(this, UsernameActivity.class);
            intent.putExtra("OpenFirstTime", "false");
            startActivity(intent);
        } else if (position < (Groupname.length - 1)) {
            savePreferences(position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.group_container, new GroupMapNotGoFragment())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (position == (Groupname.length - 1)) {
            Intent intent = new Intent(this, GroupnameActivity.class);
            intent.putExtra("GroupID", "false");
            startActivity(intent);
        }

    }

    public void setTitle(CharSequence newtitle) {
        mTitle = newtitle;
        actionBar.setTitle(mTitle);
    }

    private void savePreferences(int position) {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.groupname), Groupname[position]);
        editor.commit();
    }

    private String getUsername() {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        return prefs.getString(getString(R.string.username), "[ERROR]:unknown");
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
