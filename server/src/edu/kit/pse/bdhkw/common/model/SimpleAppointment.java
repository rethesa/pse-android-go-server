package edu.kit.pse.bdhkw.common.model;

/**
 * Created by Tarek on 10.02.17.
 * IS REPLACED BY APPOINTMENT ON SERVER !!
 */

public class SimpleAppointment implements Serializable {
    private String name;
    private GpsObject destination;
    private long date;

    public SimpleAppointment() {
        // For objectmapper
    }

    public void setName(String name) {
        this.name = name;
    }
    public  void setDestination(GpsObject destination) {
        this.destination = destination;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public long getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public GpsObject getDestination() {
        return destination;
    }
}
