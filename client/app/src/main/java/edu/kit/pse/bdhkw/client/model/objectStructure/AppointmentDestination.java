package edu.kit.pse.bdhkw.client.model.objectStructure;

import org.osmdroid.util.GeoPoint;

/**
 * This class represents the location of a group appointment.
 * Created by Theresa on 13.01.2017.
 */
public class AppointmentDestination {

    private String destinationName;
    private GeoPoint destinationPosition;

    /**
     * Instantiates a new AppointmentDestination object.
     * This default appointment will be set when a group will be created.
     */
    protected AppointmentDestination() {
        this.destinationName = "Schloss Karlsruhe";
        double latitude = 49.012941;
        double longitude = 8.404409;
        this.destinationPosition = new GeoPoint(latitude, longitude);
    }

    /**
     * Get the name of the appointments location.
     * @return the name of the destination
     */
    public String getDestinationName() {
        return destinationName;
    }

    /**
     * Set the name of the appointments location.
     * @param destinationName the name of the destination
     */
    protected void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    /**
     * Get the location/position of the appointment.
     * @return the GPS coordinates for the appointment
     */
    public GeoPoint getDestinationPosition() {
        return destinationPosition;
    }

    /**
     * Set the location/position of the appointment.
     * @param geoPoint latitude and longitude of the gps coordinates
     */
    public void setDestinationPosition(GeoPoint geoPoint) {
        destinationPosition = geoPoint;
    }



}

