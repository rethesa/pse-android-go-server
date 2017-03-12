package edu.kit.pse.bdhkw.client.model.objectStructure;

import org.osmdroid.util.GeoPoint;

/**
 * Created by Theresa on 20.12.2016.
 */

/**
 * The class represents the users location at a specific time.
 */
public class SerializableLocation {

    private double longitude;
    private double latitude;
    private long timestamp;

    /**
     * Instantiates a new GpsObject.
     *
     * @param timestamp    time when the location information was taken
     * @param userPosition position of the user
     */
    public SerializableLocation(long timestamp, GeoPoint userPosition) {
        this.timestamp = timestamp;
        this.longitude = userPosition.getLongitude();
        this.latitude = userPosition.getLatitude();
    }

    public SerializableLocation() {
        // default constructor
    }

    /**
     * Get the position of the user.
     *
     * @return the GPS location of the user
     */
    public GeoPoint toGeoPoint() {
        return new GeoPoint(latitude, longitude);
    }

    /**
     * Get the timestamp of the location.
     *
     * @return the timestamp of the GPS location
     */
    public long getTimestamp() {
        return timestamp;
    }

    /** Getters/Setters for ObjectMapper */
    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

/**    public void showGpsObjectOnMap(GeoPoint position) {
 // I dont't think this should be here. The GpsObject would have to know the map/activity.
 // Instead the map should pull the location and display it there.
 //TODO Abweichung vom Entwurf dokumentieren.
 }*/
}
