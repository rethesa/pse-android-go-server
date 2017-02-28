package edu.kit.pse.bdhkw.client.model.objectStructure;

import junit.framework.Assert;

import org.junit.Test;
import org.osmdroid.util.GeoPoint;

/**
 * Test class for appointment date.
 * @author Theresa Heine
 * @version 1.0
 */
public class AppointmentDateTest {

    @Test
    public void testSetDate() {
        Appointment appointment = new Appointment();
        appointment.getAppointmentDate().setDate("24.12.2017");

        Assert.assertTrue(appointment.getAppointmentDate().getDate().equals("24.12.2017"));
    }

    @Test
    public void testSetEmptyDate() {
        Appointment appointment = new Appointment();
        appointment.getAppointmentDate().setDate("");

        Assert.assertTrue(!appointment.getAppointmentDate().getDate().equals(""));
        Assert.assertTrue(appointment.getAppointmentDate().getDate().equals("01.01.2000"));
    }

    @Test
    public void testSetTime() {
        Appointment appointment = new Appointment();
        appointment.getAppointmentDate().setTime("14:54");

        Assert.assertTrue(appointment.getAppointmentDate().getTime().equals("14:54"));
    }

    @Test
    public void testSetEmptyDateTime(){
        Appointment appointment = new Appointment();
        appointment.getAppointmentDate().setTime("");

        Assert.assertTrue(!appointment.getAppointmentDate().getTime().equals(""));
        Assert.assertTrue(appointment.getAppointmentDate().getTime().equals("00:00"));
    }

    @Test
    public void testGetDate() {
        GeoPoint geoPoint = new GeoPoint(1.456, 50.343);
        Appointment appointment = new Appointment("16.03.2008", "17:53", "Mensa am Adenauerring", geoPoint);

        Assert.assertTrue(appointment.getAppointmentDate().getDate().equals("16.03.2008"));
    }

    @Test
    public void testGetTime() {
        GeoPoint geoPoint = new GeoPoint(1.456, 50.343);
        Appointment appointment = new Appointment("16.03.2008", "17:53", "Mensa am Adenauerring", geoPoint);

        Assert.assertTrue(appointment.getAppointmentDate().getTime().equals("17:53"));
    }
}