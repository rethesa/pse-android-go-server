package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.graphics.Point;

/**
 * Created by Theresa on 20.12.2016.
 */

public abstract class UserDecoratorClient implements UserComponent {

    private String userName;
    private int userID;
    private GpsObject gpsObject;
    protected boolean isAdmin;

    public UserDecoratorClient(String name, int userID) {
        this.userName = name;
        this.userID = userID;
        this.isAdmin = false;
        //this.gpsObject
    }

    public String getUserName() {
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

    public boolean isAdmin(){
        return isAdmin;
    }

}
