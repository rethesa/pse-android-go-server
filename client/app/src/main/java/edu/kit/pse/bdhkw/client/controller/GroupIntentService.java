package edu.kit.pse.bdhkw.client.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import edu.kit.pse.bdhkw.client.controller.database.GroupService;

/**
 * Created by Theresa on 08.02.2017.
 */

public class GroupIntentService extends IntentService {

    private static final String LOG_TAG = GroupIntentService.class.getSimpleName();
    private GroupService groupService;

    public static void start(Context context) {
        Log.i(LOG_TAG, "Starting on thread #" + Thread.currentThread().getId());
        Intent intent = new Intent(context, GroupIntentService.class);
        context.startService(intent);
    }

    public GroupIntentService() {
        super(GroupIntentService.class.getSimpleName());
    }


    @Override
    public void onCreate() {
        super.onCreate();
        groupService = new GroupService(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
