package edu.kit.pse.bdhkw.client.model.objectStructure;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.osmdroid.util.GeoPoint;

/**
 * Tests for AppointmentDestination.
 * @author Theresa Heine
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class AppointmentDestinationTest {

    @Test
    public void testGetDestinationName() {
        AppointmentDestination appointmentDestination = new AppointmentDestination();
        appointmentDestination.setDestinationName("Schloss Karlsruhe");

        Assert.assertEquals("Schloss Karlsruhe", appointmentDestination.getDestinationName());
    }

    @Test
    public void testSetDestinationName() {
        AppointmentDestination appointmentDestination = new AppointmentDestination();
        appointmentDestination.setDestinationName("Mensa am Adenauerring");

        Assert.assertEquals("Mensa am Adenauerring", appointmentDestination.getDestinationName());
    }

    @Test
    public void testSetEmptyDestinationName() {
        AppointmentDestination appointmentDestination = new AppointmentDestination();
        appointmentDestination.setDestinationName("");

        Assert.assertTrue(appointmentDestination.getDestinationName().equals(""));
    }

    @Test
    public void testGetDestinationPosition() {
        AppointmentDestination appointmentDestination = new AppointmentDestination();
        GeoPoint geoPoint = new GeoPoint(49.012941, 8.404409);
        appointmentDestination.setDestinationPosition(geoPoint);
        Double delta = 0.00001; // Maximum difference between values

        Assert.assertEquals(appointmentDestination.getDestinationPosition().getLatitude(), 49.012941, delta);
        Assert.assertEquals(appointmentDestination.getDestinationPosition().getLongitude(), 8.404409, delta);
    }

    @Test
    public void testSetDestinationPosition() {
        AppointmentDestination appointmentDestination = new AppointmentDestination();
        GeoPoint geoPoint = new GeoPoint(1.999, 34.66);
        appointmentDestination.setDestinationPosition(geoPoint);
        double delta = 0.0001; //Maximum difference between values

        Assert.assertEquals(1.999,appointmentDestination.getDestinationPosition().getLatitude(), delta);
        Assert.assertEquals(34.66, appointmentDestination.getDestinationPosition().getLongitude(), delta);
    }

    @Test
    public void testSetEmptyDestinationPosition() {
        AppointmentDestination appointmentDestination = new AppointmentDestination();
        appointmentDestination.setDestinationPosition(null);

        Assert.assertNotNull(appointmentDestination.getDestinationPosition());
    }
}