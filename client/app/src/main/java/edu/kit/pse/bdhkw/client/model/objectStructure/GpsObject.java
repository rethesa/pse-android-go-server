package edu.kit.pse.bdhkw.client.model.objectStructure;

import org.osmdroid.util.GeoPoint;

import java.util.Date;

/**
 * Created by Theresa on 20.12.2016.
 */

/**
 * The class represents the users location at a specific time.
 */
public class GpsObject {

    private Date timestamp;
    private GeoPoint userPosition;

    /**
     * Instantiates a new GpsObject.
     *
     * @param timestamp    time when the location information was taken
     * @param userPosition position of the user
     */
    public GpsObject(Date timestamp, GeoPoint userPosition) {
        this.timestamp = timestamp;
        this.userPosition = userPosition;
    }

    /**
     * Get the position of the user.
     *
     * @return the GPS location of the user
     */
    public GeoPoint getGpsObject() {
        return userPosition;
    }

    /**
     * Get the timestamp of the location.
     *
     * @return the timestamp of the GPS location
     */
    public Date getTimestamp() {
        return timestamp;
    }

/**    public void showGpsObjectOnMap(GeoPoint position) {
 // I dont't think this should be here. The GpsObject would have to know the map/activity.
 // Instead the map should pull the location and display it there.
 //TODO Abweichung vom Entwurf dokumentieren.
 }*/
}
