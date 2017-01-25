package edu.kit.pse.bdhkw.client.model;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

/**
 * Created by Tarek on 13.01.17.
 */

public class GoIntentService extends IntentService {
    private GroupClient group;

    public GoIntentService(GroupClient group) {
        super(GoIntentService.class.getSimpleName());
        this.group = group;
    }

    public void activate(Context context) {
        // send messages to the server in periodic intervals
        Intent intent = new Intent(context, GoIntentService.class);
        context.startService(intent);

        // TODO:
    }

    public void deactivate() {
        // Stop polling
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
