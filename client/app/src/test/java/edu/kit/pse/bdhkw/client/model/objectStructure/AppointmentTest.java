package edu.kit.pse.bdhkw.client.model.objectStructure;

import junit.framework.Assert;

import org.junit.Test;
import org.osmdroid.util.GeoPoint;

import static org.junit.Assert.*;

/**
 * Test class for appointment.
 * @author Theresa Heine
 * @version 1.0
 */
public class AppointmentTest {


    @Test
    public void testToSimpleAppointment() {
        //TODO
    }

    @Test
    public void testMakeDate() {
        //TODO
    }

    @Test
    public void testSetAppointmentDate() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate("24.12.2017", "20:00");

        Assert.assertTrue(appointment.getAppointmentDate().getDate().equals("24.12.2017") &&
        appointment.getAppointmentDate().getTime().equals("20:00"));
    }

    @Test
    public void testGetAppointmentDate() {
        GeoPoint geoPoint = new GeoPoint(8.1234, 50.1234);
        Appointment appointment = new Appointment("28.02.1997", "16:54", "Karlsruhe", geoPoint);

        Assert.assertEquals("28.02.1997", appointment.getAppointmentDate().getDate());
        Assert.assertEquals("16:54", appointment.getAppointmentDate().getTime());
    }

    @Test
    public void testSetAppointmentDestination() {
        Appointment appointment = new Appointment();
        GeoPoint geoPoint = new GeoPoint(1.2222, 40.456);
        appointment.setAppointmentDestination("Zielort", geoPoint);
        double delta = 0.0001;

        Assert.assertEquals("Zielort", appointment.getAppointmentDestination().getDestinationName());
        Assert.assertEquals(1.2222, appointment.getAppointmentDestination().getDestinationPosition().getLatitude(), delta);
        Assert.assertEquals(40.456,appointment.getAppointmentDestination().getDestinationPosition().getLongitude(), delta);
    }

    @Test
    public void testGetAppointmentDestination() {
        GeoPoint geoPoint = new GeoPoint(8.1234, 50.1234);
        Appointment appointment = new Appointment("28.02.1997", "16:54", "Karlsruhe", geoPoint);

        Assert.assertEquals("Karlsruhe", appointment.getAppointmentDestination().getDestinationName());
        Assert.assertEquals(geoPoint.getLatitude(), appointment.getAppointmentDestination().getDestinationPosition().getLatitude());
        Assert.assertEquals(geoPoint.getLongitude(), appointment.getAppointmentDestination().getDestinationPosition().getLongitude());
    }
}