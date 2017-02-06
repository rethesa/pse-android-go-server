package edu.kit.pse.bdhkw.common.model;

import org.osmdroid.util.GeoPoint;

/**
 * Created by Theresa on 13.01.2017.
 */

public class AppointmentDestination {

    private String destinationName;
    private GeoPoint destinationPosition;

    private AppointmentDestination() {
        //destinationPosition = new Pair<>(latitude,latitude);
    }

    protected String getDestinationName() {
        return destinationName;
    }

    protected void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    protected GeoPoint getDestinationPosition() {
        return destinationPosition;
    }

    protected void setDestinationPosition(GeoPoint destinationPosition) {
        this.destinationPosition = destinationPosition;
    }
}

