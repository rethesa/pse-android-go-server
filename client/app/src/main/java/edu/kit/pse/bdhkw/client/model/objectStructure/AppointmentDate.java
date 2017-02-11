package edu.kit.pse.bdhkw.client.model.objectStructure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Theresa on 20.12.2016.
 */

/**
 * This class represents the date and time of a group appointment.
 */
public class AppointmentDate {

    private final SimpleDateFormat dateParser;
    private final SimpleDateFormat timeParser;
    private Date date;
    private Date time;

    /**
     * Instantiates a new AppointmentDate object.
     */
    protected AppointmentDate() {
        this.date = null;
        this.time = null;
        timeParser = new SimpleDateFormat("HH:mm");
        dateParser = new SimpleDateFormat("dd.MM.yyyy");
    }

    /**
     * Set the date of the appointment of the group

     *
     * @param stringDate date in dd.MM.yyyy
     */
    protected void setDate(String stringDate) {
        try {
            this.date = dateParser.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the time of the appointment for the group.
     *
     * @param stringTime time in HH:mm
     */
    protected void setTime(String stringTime) {
        try {
            this.time = timeParser.parse(stringTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the date of the appointment.
     *
     * @return date of the appointment
     */
    public Date getDate() {
        return date;
    }

    /**
     * Get the time of the appointment.
     *
     * @return time of the appointment
     */
    public Date getTime() {
        return time;
    }


}
