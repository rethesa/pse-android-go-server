package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.graphics.Point;

import org.osmdroid.util.GeoPoint;

/**
 * Created by Theresa on 20.12.2016.
 */

public class GpsObject {

    private String timestamp;
    private GeoPoint userPosition;

    public GpsObject() {
    }

    public GeoPoint getGpsObject() {
        userPosition = new GeoPoint(null);
        //TODO
        return userPosition;
    }

    /**
     * Time of the GpsObject to see how old it is.
     * @return
     */
    public String getTimestamp() {
        return timestamp;
    }

    public void showGpsObjectOnMap(Point position) {
        //TODO
    }
}
