package edu.kit.pse.bdhkw.client.model.objectStructure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;


/**
 * Created by Theresa on 13.01.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("SimpleUser_class")
public class SimpleUser implements UserComponent, Serializable {

    private String name;
    private int id;
    private String deviceId;

    public SimpleUser() {
        // Intentionally left blank
    }
    public SimpleUser(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void setName(String name) {
        //preferences anpassen
        this.name = name;
    }
    public void setId(int id) {
        //zu preferences hinzufügen
        this.id = id;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getUserID() {
        return id;
    }

}
