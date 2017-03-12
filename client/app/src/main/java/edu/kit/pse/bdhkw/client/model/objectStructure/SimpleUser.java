package edu.kit.pse.bdhkw.client.model.objectStructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;


/**
 * User of the app is a simple user as long as he isn't member of any group.
 * @author Theresa Heine
 * @version 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("SimpleUser_class")
public class SimpleUser implements UserComponent, Serializable {

    private String name;
    private int id;
    private String deviceId;

    /**
     * Empty constructor.
     */
    public SimpleUser() {
        // Intentionally left blank
    }

    /**
     * Constructor of the simple user with name and unique id.
     * @param name of the user
     * @param id of the user
     */
    public SimpleUser(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Change the name of the simple user after registration.
     * @param newName of the user
     */
    public void setName(String newName) {
        if(name != "") {
            this.name = newName;
        }
    }

    /**
     * Set unique id of the user.
     * @param id of the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the device id of the user.
     * @param deviceId of user
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Get name of the user.
     * @return name
     */
    public String getName() {
        return name;
    }

    @Override
    public int getUserID() {
        return id;
    }

}
