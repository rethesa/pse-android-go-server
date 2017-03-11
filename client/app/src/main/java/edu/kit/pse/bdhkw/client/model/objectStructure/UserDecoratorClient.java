package edu.kit.pse.bdhkw.client.model.objectStructure;

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

    public String getName() {
        return userName;
    }

    @Override
    public int getUserID() {
        return userID;
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
