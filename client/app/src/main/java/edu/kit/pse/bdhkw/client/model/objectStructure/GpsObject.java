package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.graphics.Point;

/**
 * Created by Theresa on 20.12.2016.
 */

public class GpsObject {

    private String timestamp;
    private Point userPosition;

    public GpsObject() {
    }

    public Point getGpsObject() {
        userPosition = new Point();
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
