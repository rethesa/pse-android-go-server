package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.graphics.Point;

/**
 * Created by Theresa on 13.01.2017.
 */

public class SimpleUser implements UserComponent {

    private String userName;
    private int userId;
    private String userDeviceId;


    public SimpleUser(String name, String deviceId) {
        this.userName = name;
        this.userDeviceId = deviceId;
        //this.userId = ...
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public int getUserID() {
        return 0;
    }

    @Override
    public String getUserDeviceId() {
        return null;
    }

    @Override
    public Point getUserPosition() {
        return null;
    }

    /**
     * Change the name of the user to a different one.
     * @param newUserName
     */
    public void changeUserName(String newUserName) {
        userName = newUserName;
        //TODO
    }
}
