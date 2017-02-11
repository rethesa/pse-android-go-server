package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.graphics.Point;

import com.fasterxml.jackson.annotation.JsonTypeName;


/**
 * Created by Theresa on 13.01.2017.
 */

@JsonTypeName("SimpleUser_class")
public class SimpleUser implements UserComponent, Serializable {
    private String userName;
    private int userId;

    public SimpleUser() {
        // Intentionally left blank
    }
    public SimpleUser(String name, int id) {
        this.userName = name;
        this.userId = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

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

}
