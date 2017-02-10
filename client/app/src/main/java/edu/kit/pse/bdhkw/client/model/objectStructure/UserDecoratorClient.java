package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.graphics.Point;

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

    public String getName() {
        return userName;
    }

    @Override
    public int getUserID() {
        return userID;
    }

    @Override
    public String getUserDeviceId() {
        return null;
    }

    @Override
    public Point getUserPosition() {
       return null;
    }

    public boolean getView() {
        return false;
    }

    public GpsObject getGpsObject() {
        return gpsObject;
    }

}
