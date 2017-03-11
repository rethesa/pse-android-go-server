package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.os.Parcel;
import android.os.Parcelable;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.osmdroid.util.GeoPoint;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Test cases for SimpleAppointment.
 * @author Theresa Heine
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class SimpleAppointmentTest {

    @Test
    public void setName() throws Exception {
        SimpleAppointment simpleAppointment = new SimpleAppointment();
        simpleAppointment.setName("Schloss Karlsruhe");

        Assert.assertEquals("Schloss Karlsruhe", simpleAppointment.getName());
    }

    @Test
    public void setDestination() throws Exception {
        SimpleAppointment simpleAppointment = new SimpleAppointment();
        GeoPoint geoPoint = new GeoPoint(49.012941, 8.404409);
        GpsObject gpsObject = new GpsObject(null, geoPoint);
        simpleAppointment.setDestination(gpsObject);

        Assert.assertEquals(49.012941, simpleAppointment.getDestination().getLatitude());
        Assert.assertEquals(8.404409, simpleAppointment.getDestination().getLongitude());
    }

    @Test
    public void setDate() throws Exception {
        SimpleAppointment simpleAppointment = new SimpleAppointment();
        simpleAppointment.setDate(1220227200);

        Assert.assertEquals(1220227200, simpleAppointment.getDate());
    }

    @Test
    public void getDate() throws Exception {
        SimpleAppointment simpleAppointment = new SimpleAppointment();

        Assert.assertEquals(0, simpleAppointment.getDate());
    }

    @Test
    public void getName() throws Exception {
        Parcel parcelMock = mock(Parcel.class);
        SimpleAppointment simpleAppointment = new SimpleAppointment(parcelMock);
        simpleAppointment.setName("Schloss Karlsruhe");

        Assert.assertEquals("Schloss Karlsruhe", simpleAppointment.getName());
        verify(parcelMock, times(1)).readString();
        verify(parcelMock, times(1)).readParcelable(any(ClassLoader.class));
        verify(parcelMock, times(1)).readLong();
    }

    @Test
    public void getDestination() throws Exception {
        SimpleAppointment simpleAppointment = new SimpleAppointment();
        simpleAppointment.setName("Schloss Karlsruhe");

        Assert.assertEquals("Schloss Karlsruhe", simpleAppointment.getName());
    }

    @Test
    public void describeContents() throws Exception {
        SimpleAppointment simpleAppointment = new SimpleAppointment();

        Assert.assertEquals(0, simpleAppointment.describeContents());
    }

    @Test
    public void writeToParcel() throws Exception {
        SimpleAppointment simpleAppointment = new SimpleAppointment();
        Parcel parcelMock = mock(Parcel.class);
        simpleAppointment.writeToParcel(parcelMock, 0);

        verify(parcelMock, times(1)).writeString(anyString());
        verify(parcelMock, times(1)).writeParcelable(any(Parcelable.class), anyInt());
        verify(parcelMock, times(1)).writeLong(anyLong());
    }

}