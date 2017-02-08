package edu.kit.pse.bdhkw.client.controller;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;

/**
 * Created by Theresa on 08.02.2017.
 */

public class UserIntentService extends IntentService{

    private static final String LOG_TAG = UserIntentService.class.getSimpleName();

    public UserIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
