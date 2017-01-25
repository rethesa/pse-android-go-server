package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.graphics.Point;

/**
 * Created by Theresa on 13.01.2017.
 */

public class AppointmentDestination {

    private String destinationName;
    private Point destinationPosition;

    private AppointmentDestination() {
        //destinationPosition = new Pair<>(latitude,latitude);
    }

    protected void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    protected void setDestinationPosition(Point destinationPosition) {
        this.destinationPosition = destinationPosition;
    }

    protected String getDestinationName() {
        return destinationName;
    }

    protected Point getDestinationPosition() {
        return destinationPosition;
    }
}

