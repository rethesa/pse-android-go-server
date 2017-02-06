package edu.kit.pse.bdhkw.client.model.objectStructure;

import org.osmdroid.util.GeoPoint;

/**
 * Created by Schokomonsterchen on 21.12.2016.
 */

public class Appointment {

    private AppointmentDate appointmentDate;
    private AppointmentDestination appointmentDestination;

    public Appointment() {
        this.appointmentDate = new AppointmentDate();
        this.appointmentDestination = new AppointmentDestination();
    }

    /**
     * Set new date and time for the next appointment.
     *
     * @param stringDate
     * @param stringTime
     */
    public void setAppointmentDate(String stringDate, String stringTime) {
        appointmentDate.setDate(stringDate);
        appointmentDate.setTime(stringTime);
    }

    /**
     * To show in activity.
     *
     * @return
     */
    public AppointmentDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Set a new destination for the appointment.
     * TODO dokumentiere Abweichung von Entwurf: zweiter Parameter
     * @param appointmentDestination
     */
    public void setAppointmentDestination(String appointmentDestination, GeoPoint appointmentDestinationPosition) {
        this.appointmentDestination.setDestinationName(appointmentDestination);
        this.appointmentDestination.setDestinationPosition(appointmentDestinationPosition);
    }

    /**
     * Get the name and the position of the appointment.
     *
     * @return name and position.
     */
    public AppointmentDestination getAppointmentDestination() {
        return appointmentDestination;
    }

}