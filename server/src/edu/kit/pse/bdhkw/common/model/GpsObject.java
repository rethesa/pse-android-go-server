package edu.kit.pse.bdhkw.common.model;

import org.osmdroid.util.GeoPoint;

import java.util.Date;

/**
 * Created by Theresa on 20.12.2016.
 */

public class GpsObject {

    private Date timestamp;
    private GeoPoint userPosition;

    public GpsObject() {
    }

    public GeoPoint getGpsObject() {
        userPosition = new GeoPoint(50d, 50d);
        //TODO
        return userPosition;
    }

    /**
     * Time of the GpsObject to see how old it is.
     * @return
     */
    public Date getTimestamp() {
        return timestamp;
    }

    public void showGpsObjectOnMap(GeoPoint position) {
        //TODO
    }
}
