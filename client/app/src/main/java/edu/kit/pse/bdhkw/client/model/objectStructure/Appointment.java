package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.os.Parcel;
import android.os.Parcelable;

import org.osmdroid.util.GeoPoint;

import java.util.Date;

/**
 * Created by Schokomonsterchen on 21.12.2016.
 */

/**
 * Represents a group appointment/meeting in place and time.
 */
public class Appointment implements Serializable, Parcelable {

    private Date date;
    private GpsObject destination;
    private String name;
    //private AppointmentDate appointmentDate;
    //private AppointmentDestination appointmentDestination;

    /**
     * Instantiates a new Appointment object.
     */
    protected Appointment() {
        //this.appointmentDate = new AppointmentDate();
        //this.appointmentDestination = new AppointmentDestination();
    }

    protected Appointment(Parcel in) {
        destination = in.readParcelable(GpsObject.class.getClassLoader());
        name = in.readString();
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(destination, flags);
        dest.writeString(name);
        dest.writeLong(date.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GpsObject getDestination() {
        return destination;
    }

    public void setDestination(GpsObject destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set new date and time for the next appointment.
     *
     * @param stringDate The date of the appointment as a string. Format: dd.MM.yyyy
     * @param stringTime The time of the appointment as a string. Format: HH:mm
     */
    public void setAppointmentDate(String stringDate, String stringTime) {
        //appointmentDate.setDate(stringDate);
        //appointmentDate.setTime(stringTime);
    }

    /**
     * Gets the appointment date and time to show in activity.
     *
     * @return the appointment date and time
     */
    public AppointmentDate getAppointmentDate() {
        return null;
        //return appointmentDate;
    }

    /**
     * Set a new destination for the appointment.
     * TODO dokumentiere Abweichung von Entwurf: zweiter Parameter
     *
     * @param appointmentDestination         the name of the appointment destination
     * @param appointmentDestinationPosition the GPS coordinates of the appointment destination
     */
    public void setAppointmentDestination(String appointmentDestination, GeoPoint appointmentDestinationPosition) {
        //this.appointmentDestination.setDestinationName(appointmentDestination);
        //this.appointmentDestination.setDestinationPosition(appointmentDestinationPosition);
    }

    /**
     * Get the name and the location of the appointment.
     *
     * @return name and location of the appointment
     */
    public AppointmentDestination getAppointmentDestination() {
        //return appointmentDestination;
        return null;
    }

}