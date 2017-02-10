package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.graphics.Point;

import org.osmdroid.util.GeoPoint;

/**
 * Created by Theresa on 13.01.2017.
 */

public class AppointmentDestination {

    private String destinationName;
    private GeoPoint destinationPosition;
    protected AppointmentDestination() {
        //destinationPosition = new Pair<>(latitude,latitude);
    }

    protected void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public void setDestinationPosition(double latitude, double longitude) {
        destinationPosition = new GeoPoint(latitude, longitude);
    }

    public String getDestinationName() {
        return destinationName;
    }

    public GeoPoint getDestinationPosition() {
        return destinationPosition;
    }
}

