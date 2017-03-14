package edu.kit.pse.bdhkw.client.model.objectStructure;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.asm.tree.JumpInsnNode;
import org.osmdroid.util.GeoPoint;

/**
 * Test class for appointment date.
 * @author Theresa Heine
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class AppointmentDateTest {

    @Test
    public void testSetDate() {
        AppointmentDate appointmentDate = new AppointmentDate();
        appointmentDate.setDate("24.12.2017");

        Assert.assertTrue(appointmentDate.getDate().equals("24.12.2017"));
    }

    @Test
    public void testSetEmptyDate() {
        AppointmentDate appointmentDate = new AppointmentDate();
        appointmentDate.setDate("");

        Assert.assertTrue(appointmentDate.getDate().equals(""));
    }

    @Test
    public void testSetTime() {
        AppointmentDate appointmentDate = new AppointmentDate();
        appointmentDate.setTime("14:54");

        Assert.assertTrue(appointmentDate.getTime().equals("14:54"));
    }

    @Test
    public void testSetEmptyDateTime(){
        AppointmentDate appointmentDate = new AppointmentDate();
        appointmentDate.setTime("");

        Assert.assertTrue(appointmentDate.getTime().equals(""));
    }

    @Test
    public void testGetDate() {
        AppointmentDate appointmentDate = new AppointmentDate();
        appointmentDate.setDate("16.03.2008");

        Assert.assertTrue(appointmentDate.getDate().equals("16.03.2008"));
    }

    @Test
    public void testGetTime() {
        AppointmentDate appointmentDate = new AppointmentDate();
        appointmentDate.setTime("17:53");

        Assert.assertTrue(appointmentDate.getTime().equals("17:53"));
    }
}