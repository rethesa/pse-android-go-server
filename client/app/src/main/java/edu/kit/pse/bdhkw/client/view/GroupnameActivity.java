package edu.kit.pse.bdhkw.client.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Schokomonsterchen on 13.01.2017.
 */

public class GroupnameActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.kit.pse.bdhkw.R.layout.groupname_activity_dynamisch);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(edu.kit.pse.bdhkw.R.id.groupname_container, new GroupnameCreateFragment()).commit();
        }
    }

}