package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.graphics.Point;

import com.fasterxml.jackson.annotation.JsonTypeName;


/**
 * Created by Theresa on 13.01.2017.
 */

@JsonTypeName("SimpleUser_class")
public class SimpleUser implements UserComponent, Serializable {
    private String name;
    private int id;

    public SimpleUser() {
        // Intentionally left blank
    }
    public SimpleUser(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getUserID() {
        return id;
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
        name = newUserName;
    }
}
