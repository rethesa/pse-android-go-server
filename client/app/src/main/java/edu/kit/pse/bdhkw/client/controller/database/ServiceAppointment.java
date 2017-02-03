package edu.kit.pse.bdhkw.client.controller.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import edu.kit.pse.bdhkw.client.model.database.DBHelperAppointment;
import edu.kit.pse.bdhkw.client.model.objectStructure.Appointment;

/**
 * Created by Theresa on 11.01.2017.
 * Appointment erstellen
 * Appointment löschen
 * Appointment ändern
 * Informationen über Appointent bekommen: return Appointment
 */

public class ServiceAppointment {

    private final DBHelperAppointment dbHelperAppointment;
    private SQLiteDatabase db;

    public ServiceAppointment(Context context) {
        dbHelperAppointment = new DBHelperAppointment(context.getApplicationContext());
    }

    /**
     * Insert the very first appointment for the group.
     * @param groupID of the group the appointment is for
     * @param appointment of the group (date, time, destination)
     * @return
     */
    public boolean insertAppointment(int groupID, Appointment appointment) {
        //TODO
        db = dbHelperAppointment.getWritableDatabase();
        try {

            return false;
        } finally {
            db.close();
        }
    }

    /**
     * Get information about the appointment through the corresponding group
     * @param groupID of the group to get information about the appointment
     * @return appointment object
     */
    public Appointment readAppointmentData(int groupID) {
        //TODO
        return null;
    }

    /**
     * Appointment will be deleted if group will be deleted.
     * @param groupID delete the appointment of this group
     * @return true if the deletion was successful
     */
    public boolean deleteAppointmentData(int groupID) { //EIGENTLICH REICHT HIER PROTECTED
        //TODO
        return false;
    }

    /**
     * Every group hast just one Appointment, so if there is a new Appointment it will override the
     * old one. So update is the most important method of appointment.
     * @param groupID to actualise the corresponding appointment
     * @return true if update was successful
     */
    public boolean updateData(int groupID, Appointment appointment) {
        //TODO
        return false;
    }

    public void deleteAllAppointments() {
    }
}
