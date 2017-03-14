package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.util.Log;

import org.osmdroid.util.GeoPoint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Represents a group appointment/meeting in place and time.
 * @author Theresa Heine
 * @version 1.0
 */
public class Appointment extends SimpleAppointment {

    private AppointmentDate appointmentDate;
    private AppointmentDestination appointmentDestination;

    /**
     * Cast an appointment to an simple appointment for communication with the server.
     * @return simple appointment
     */
    public SimpleAppointment toSimpleAppointment() {
        SimpleAppointment simpleAppointment = new SimpleAppointment();
        Date date = new Date();
        simpleAppointment.setDestination(new GpsObject(date, this.appointmentDestination.getDestinationPosition()));
        simpleAppointment.setName(this.appointmentDestination.getDestinationName());
        long milliseconds = date.getTime();
        simpleAppointment.setDate(milliseconds);
        return simpleAppointment;
    }

    /**
     * Make date.
     * @return date
     */
    private Date makeDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(this.appointmentDate.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ERROR", "parsing appointment date didnt work");
        }
        return date;
    }
    /**
     * Instantiates a new Appointment object.
     */
    protected Appointment() {
        this.appointmentDate = new AppointmentDate();
        this.appointmentDestination = new AppointmentDestination();
    }

    /**
     * Constructor for a given appointment.
     * @param date of the appointment
     * @param time of the appointment
     * @param destination of the appointment
     * @param geoPoint of the appointment
     */
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
     * @param stringDate The date of the appointment as a string. Format: dd.MM.yyyy
     * @param stringTime The time of the appointment as a string. Format: HH:mm
     */
    public void setAppointmentDate(String stringDate, String stringTime) {
        if (stringDate != "" && stringTime != "") {
            appointmentDate.setDate(stringDate);
            appointmentDate.setTime(stringTime);
        }
    }

    /**
     * Gets the appointment date and time to show in activity.
     * @return the appointment date and time
     */
    public AppointmentDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Set a new destination for the appointment.
     * @param appointmentDestination         the name of the appointment destination
     * @param appointmentDestinationPosition the GPS coordinates of the appointment destination
     */
    public void setAppointmentDestination(String appointmentDestination, GeoPoint appointmentDestinationPosition) {
        if (appointmentDestination != "" && appointmentDestination != null) {
            this.appointmentDestination.setDestinationName(appointmentDestination);
            this.appointmentDestination.setDestinationPosition(appointmentDestinationPosition);
        }
    }

    /**
     * Get the name and the location of the appointment.
     * @return name and location of the appointment
     */
    public AppointmentDestination getAppointmentDestination() {
        return appointmentDestination;
    }

}