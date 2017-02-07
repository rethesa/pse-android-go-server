package edu.kit.pse.bdhkw.client.model.objectStructure;

import org.osmdroid.util.GeoPoint;

/**
 * Created by Theresa on 13.01.2017.
 */

/**
 * This class represents the location of a group appointment.
 */
public class AppointmentDestination {

    private String destinationName;
    private GeoPoint destinationPosition;

    /**
     * Instantiates a new AppointmentDestination object.
     */
    protected AppointmentDestination() {
        this.destinationName = null;
        this.destinationPosition = null;
    }

    /**
     * Get the name of the appointments location.
     *
     * @return the name of the destination
     */
    protected String getDestinationName() {
        return destinationName;
    }

    /**
     * Set the name of the appointments location.
     *
     * @param destinationName the name of the destination
     */
    protected void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    /**
     * Get the location/position of the appointment.
     *
     * @return the GPS coordinates for the appointment
     */
    protected GeoPoint getDestinationPosition() {
        return destinationPosition;
    }

    /**
     * Set the location/position of the appointment.
     *
     * @param destinationPosition the GPS coordinates for the appointment
     */
    protected void setDestinationPosition(GeoPoint destinationPosition) {
        this.destinationPosition = destinationPosition;
    }
}

