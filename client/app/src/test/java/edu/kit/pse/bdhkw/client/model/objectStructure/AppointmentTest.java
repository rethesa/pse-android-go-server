package edu.kit.pse.bdhkw.client.model.objectStructure;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for appointment.
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

        /*Assert.assertTrue(appointment.getAppointmentDate().getDate().equals("24.12.2017") &&
        appointment.getAppointmentDate().getTime().equals("20:00"));*/
        //Assert.assertTrue(appointment.getAppointmentDate().getTime().equals("20:00"));
    }

    @Test
    public void testGetAppointmentDate() {

    }

    @Test
    public void testSetAppointmentDestination() {

    }

    @Test
    public void testGetAppointmentDestination() {

    }
}