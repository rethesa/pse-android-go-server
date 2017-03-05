package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import org.osmdroid.util.GeoPoint;

import java.util.Date;

/**
 * The class represents the users location at a specific time.
 * @author Theresa Heine
 * @version 1.0
 */
@JsonTypeName("GpsObject_class")
public class GpsObject implements Parcelable, Serializable {

    private Date timestamp;
    private double longitude;
    private double latitude;

    /**
     * Instantiates a new GpsObject.
     * @param timestamp    time when the location information was taken
     * @param userPosition position of the user
     */
    public GpsObject(Date timestamp, GeoPoint userPosition) {
        this.timestamp = timestamp;
        this.longitude = userPosition.getLongitude();
        this.latitude = userPosition.getLatitude();
    }

    public GpsObject() {
        // default constructor
    }

    protected GpsObject(Parcel in) {
        longitude = in.readDouble();
        latitude = in.readDouble();
        timestamp = new Date(in.readLong());
    }

    /**
     * Creator of a GPS Object.
     */
    public static final Creator<GpsObject> CREATOR = new Creator<GpsObject>() {
        @Override
        public GpsObject createFromParcel(Parcel in) {
            return new GpsObject(in);
        }

        @Override
        public GpsObject[] newArray(int size) {
            return new GpsObject[size];
        }
    };

    /**
     * Get the position of the user.
     * @return the GPS location of the user
     */
    public GeoPoint toGeoPoint() {
        return new GeoPoint(latitude, longitude);
    }

    /**
     * Get the timestamp of the location.
     * @return the timestamp of the GPS location
     */
    public Date getTimestamp() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
        parcel.writeLong(timestamp.getTime());
    }

}
