package edu.kit.pse.bdhkw.client.view;

import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.LinkedList;
import java.util.List;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

public class BaseActivity extends AppCompatActivity {

    public final static String navigation = "Group navigation";
    private final static String groupNameString = "groupname";

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private List<String> Groupname;
    private ArrayAdapter<String> mAdapter;
    private ActionBar actionBar;
    private int[] counter;
    private BaseActivity activity = this;

    private GroupClient group;
    private String groupname;


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

    public String getGroupname(){
        return this.groupname;
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

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(mDrawerTitle);

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
        GroupService groupService = new GroupService(this);
        Groupname = groupService.readAllGroupNames();
        Groupname.add(0, getString(R.string.welcome) + " " + getUsername());
        Groupname.add(Groupname.size(), getString(R.string.addgroup));

        //setting adapter
        mAdapter = new ArrayAdapter<String>(this, edu.kit.pse.bdhkw.R.layout.list_item, Groupname);
        //mAdapter = new MemberAdapter(bla);
        mDrawerList.setAdapter(mAdapter);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            if(position != 0 && position != Groupname.size() - 1){
                groupname = Groupname.get(position);
            }
        }
    }

    private class DrawerItemLongClickListener implements ListView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            if(position == 0) {
                final Dialog appDialog = new Dialog(activity);
                appDialog.setTitle("leave Dialog");
                appDialog.setContentView(R.layout.leave_app_dialog);
                appDialog.show();

                Button yes = (Button) appDialog.findViewById(R.id.yes_app_button);
                Button no = (Button) appDialog.findViewById(R.id.no_app_button);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO: auf dem server und auf dem client entfernen und aus den Preferences
                        appDialog.cancel();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appDialog.cancel();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        appDialog.cancel();
                    }
                });
            } else if(position > 0 && position < (Groupname.size() -2)) {
                group = new GroupService(activity.getBaseContext()).readOneGroupRow(Groupname.get(position));
                if(group.getMemberType(activity, getUserId())) {
                    final Dialog dialog = new Dialog(activity);
                    dialog.setTitle(getString(R.string.choose_option) + " " + Groupname.get(position));
                    dialog.setContentView(R.layout.group_dialogue);
                    dialog.show();

                    Button changeGroupname = (Button) dialog.findViewById(R.id.change_groupname_button);
                    Button deleteGroup = (Button) dialog.findViewById(R.id.leave_group_button);

                    changeGroupname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            savePreferences(position);
                            Intent intent = new Intent(activity, GroupnameActivity.class);
                            intent.putExtra("GroupID", Groupname.get(position));
                            activity.startActivity(intent);
                        }
                    });

                    deleteGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_SHORT).show();
                            final Dialog deleteDialog = new Dialog(activity);
                            deleteDialog.setTitle("delete Dialog");
                            deleteDialog.setContentView(R.layout.delete_group_dialogue);
                            deleteDialog.show();

                            Button yes = (Button) deleteDialog.findViewById(R.id.yes_delete_button);
                            Button no = (Button) deleteDialog.findViewById(R.id.no_delete_button);

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //TODO: delete this group
                                    deleteDialog.cancel();
                                }
                            });

                            no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    deleteDialog.cancel();
                                }
                            });
                            dialog.cancel();
                        }

                    });
                } else {
                    final Dialog leaveDialog = new Dialog(activity);
                    leaveDialog.setTitle("leave Dialog");
                    leaveDialog.setContentView(R.layout.leave_group_dialogue);
                    leaveDialog.show();

                    Button yes = (Button) leaveDialog.findViewById(R.id.yes_leave_button);
                    Button no = (Button) leaveDialog.findViewById(R.id.no_leave_button);

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            group.leaveGroup(activity, //TODO UserDecoratorClient);
                            leaveDialog.cancel();
                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            leaveDialog.cancel();
                        }
                    });
                }
            }
            return true;
        }
    }

    // Swaps activities in the main content view
    private void selectItem(final int position) {

        // Highlight the selected item, update the title, and close the drawer
        // + 1 wegen dem "mein profil"
        mDrawerList.setItemChecked(position, true);
        setTitle(Groupname.get(position));
        mDrawerLayout.closeDrawer(mDrawerList);
        if (position == 0) {
            Intent intent = new Intent(this, UsernameActivity.class);
            intent.putExtra("OpenFirstTime", "false");
            //startActivity(intent);
            startActivityForResult(intent, 123);
        } else if (position < (Groupname.size() - 1)) {
            savePreferences(position);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.group_container, new GroupMapNotGoFragment())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (position == (Groupname.size() - 1)) {
            Intent intent = new Intent(this, GroupnameActivity.class);
            intent.putExtra("GroupID", "false");
            startActivity(intent);
        }

    }

    private void savePreferences(int position) {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.groupname), Groupname.get(position));
        editor.commit();
    }

    private String getUsername() {
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        return prefs.getString(getString(R.string.sharedUserName), "[ERROR]:unknown");
    }

    private int getUserId() {
        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        int defaultUserId = -1;
        int userId = preferences.getInt(getString(R.string.sharedUserId), defaultUserId);
        return userId;
    }

}
