package edu.kit.pse.bdhkw.client.model.objectStructure;

/**
 * This class represents the date and time of a group appointment.
 * @autor Theresa Heine
 * @version 1.0
 */
public class AppointmentDate {

    private String date;
    private String time;

    /**
     * Instantiates a new AppointmentDate object.
     */
    protected AppointmentDate() {
        /*
        this.date = "01.01.2000";
        this.time = "00:00";
         */
        this.date = "";
        this.time = "";
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
        if (stringTime != "") {
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
