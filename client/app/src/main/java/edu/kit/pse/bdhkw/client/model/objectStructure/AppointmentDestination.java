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

