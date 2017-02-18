package edu.kit.pse.bdhkw.client.model.objectStructure;

import org.osmdroid.util.GeoPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Schokomonsterchen on 21.12.2016.
 */

/**
 * Represents a group appointment/meeting in place and time.
 */
public class Appointment extends SimpleAppointment {

    private AppointmentDate appointmentDate = new AppointmentDate();
    private AppointmentDestination appointmentDestination = new AppointmentDestination();

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private Date date;

    public SimpleAppointment toSimpleAppointment() {
        SimpleAppointment a = new SimpleAppointment();
        date = new Date();
        a.setDestination(new GpsObject(date, appointmentDestination.getDestinationPosition()));
        a.setName(appointmentDestination.getDestinationName());
        return a;
    }
    /**
     * Instantiates a new Appointment object.
     */
    protected Appointment() {
        this.appointmentDate = new AppointmentDate();
        this.appointmentDestination = new AppointmentDestination();
    }

    public Appointment(String date, String time, String destination, GeoPoint geoPoint) {
        this.appointmentDate = new AppointmentDate();
        this.appointmentDestination = new AppointmentDestination();
        appointmentDate.setDate(date);
        appointmentDate.setTime(time);
        appointmentDestination.setDestinationName(destination);
        appointmentDestination.setDestinationPosition(geoPoint);
    }

    /**
     * Set new date and time for the next appointment.
     *
     * @param stringDate The date of the appointment as a string. Format: dd.MM.yyyy
     * @param stringTime The time of the appointment as a string. Format: HH:mm
     */
    public void setAppointmentDate(String stringDate, String stringTime) {
        appointmentDate.setDate(stringDate);
        appointmentDate.setTime(stringTime);
    }

    /**
     * Gets the appointment date and time to show in activity.
     *
     * @return the appointment date and time
     */
    public AppointmentDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Set a new destination for the appointment.
     * TODO dokumentiere Abweichung von Entwurf: zweiter Parameter
     *
     * @param appointmentDestination         the name of the appointment destination
     * @param appointmentDestinationPosition the GPS coordinates of the appointment destination
     */
    public void setAppointmentDestination(String appointmentDestination, GeoPoint appointmentDestinationPosition) {
        this.appointmentDestination.setDestinationName(appointmentDestination);
        this.appointmentDestination.setDestinationPosition(appointmentDestinationPosition);
    }

    /**
     * Get the name and the location of the appointment.
     *
     * @return name and location of the appointment
     */
    public AppointmentDestination getAppointmentDestination() {
        return appointmentDestination;
    }

}