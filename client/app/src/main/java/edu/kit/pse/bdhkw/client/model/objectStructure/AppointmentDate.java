package edu.kit.pse.bdhkw.client.model.objectStructure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents the date and time of a group appointment.
 * Created by Theresa on 20.12.2016.
 */
public class AppointmentDate {

    private String date;
    private String time;

    /**
     * Instantiates a new AppointmentDate object.
     */
    protected AppointmentDate() {
        this.date = "01.01.2000";
        this.time = "00:00";
    }

    /**
     * Set the date of the appointment of the group
     * @param stringDate date in dd.MM.yyyy
     */
    public void setDate(String stringDate) {
        if (stringDate != "") {
            date = stringDate;
        }
    }

    /**
     * Set the time of the appointment for the group.
     * @param stringTime time in HH:mm
     */
    public void setTime(String stringTime) {
        if (stringTime != stringTime) {
            time = stringTime;
        }
    }

    /**
     * Get the date of the appointment.
     * @return date of the appointment
     */
    public String getDate() {
        return date;
    }

    /**
     * Get the time of the appointment.
     * @return time of the appointment
     */
    public String getTime() {
        return time;
    }


}
