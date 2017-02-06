package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.graphics.Point;

import edu.kit.pse.bdhkw.common.model.GpsObject;
import edu.kit.pse.bdhkw.common.model.UserComponent;

/**
 * Created by Theresa on 20.12.2016.
 */

public abstract class UserDecoratorClient implements UserComponent {

    private String userName;
    private int userID;
    private GpsObject gpsObject;

    public UserDecoratorClient(String name) {
        this.userName = name;
        //this.userID =
        //this.gpsObject
    }

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public int getID() {
        return userID;
    }

    @Override
    public String getDeviceId() {
        return null;
    }

    public boolean getView() {
        return false;
    }

    @Override
    public GpsObject getGpsObject() {
        return gpsObject;
    }

}
