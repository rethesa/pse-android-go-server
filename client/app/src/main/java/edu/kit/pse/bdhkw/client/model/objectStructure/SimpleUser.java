package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.graphics.Point;
import android.provider.Settings;

import static java.security.AccessController.getContext;


/**
 * Created by Theresa on 13.01.2017.
 */

public class SimpleUser implements UserComponent {

    private String userName;
    private int userId;

    public SimpleUser(String name, int userId) {
        this.userName = name;
        this.userId = userId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public int getUserID() {
        return userId;
    }

    @Override
    public String getUserDeviceId() {
        //BEKOMMT MAN NUR IN ACTIVITY UND FRAGMENT UND KANN SOWIESO NIE AUFGERUFEN WERDEN
        return null;
    }

    @Override
    public Point getUserPosition() {
        //BEKOMMT MAN NUR IN ACTIVITY UND FRAGMENT
       return null;
    }

    /**
     * Change the name of the user to a different one.
     * @param newUserName
     */
    public void changeUserName(String newUserName) {
        //TODO check if username is valid   ODER PASSIERT DAS NUR AUF DEM SERVER ODER AN ANDERER STELLE
        userName = newUserName;
    }













}
