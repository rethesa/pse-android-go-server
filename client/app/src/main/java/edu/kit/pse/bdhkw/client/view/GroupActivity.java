package edu.kit.pse.bdhkw.client.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import edu.kit.pse.bdhkw.R;

/**
 * Created by Schokomonsterchen on 12.01.2017.
 */

public class GroupActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity_dynamisch);
        super.onCreateDrawer();

        setContentView(edu.kit.pse.bdhkw.R.layout.group_activity_dynamisch);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(edu.kit.pse.bdhkw.R.id.group_container, new GroupMapNotGoFragment()).commit();
        }
    }





}