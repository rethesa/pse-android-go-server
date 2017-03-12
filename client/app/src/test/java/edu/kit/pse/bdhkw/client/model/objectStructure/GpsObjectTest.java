package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.os.Parcel;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.osmdroid.util.GeoPoint;
import org.powermock.api.mockito.internal.invocation.MockitoMethodInvocationControl;
import org.powermock.api.mockito.internal.verification.VerifyNoMoreInteractions;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;

/**
 * Test mehtods for GPS Object.
 * @author Theresa Heine
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GpsObject.class)
public class GpsObjectTest {

    @Test
    public void toGeoPoint() throws Exception {
        GeoPoint userPosition = new GeoPoint(49.012941, 8.404409);
        GpsObject gpsObjectSpy = Mockito.spy(new GpsObject(new Date(), userPosition));
        double delta = 0.0001;
        GeoPoint geoPoint = gpsObjectSpy.toGeoPoint();

        Assert.assertEquals(userPosition.getLatitude(), geoPoint.getLatitude(), delta);
        Assert.assertEquals(userPosition.getLongitude(), geoPoint.getLongitude(), delta);
    }

    @Test
    public void getTimestamp() throws Exception {
        GeoPoint userPosition = new GeoPoint(49.012941, 8.404409);
        Date timestamp = new Date(1220227200);
        GpsObject gpsObjectSpy = Mockito.spy(new GpsObject(timestamp, userPosition));

        Assert.assertEquals(timestamp, gpsObjectSpy.getTimestamp());
    }

    @Test
    public void getLongitude() throws Exception {
        GeoPoint userPosition = new GeoPoint(49.012941, 8.404409);
        GpsObject gpsObjectSpy = Mockito.spy(new GpsObject(new Date(), userPosition));

        Assert.assertEquals(userPosition.getLongitude(), gpsObjectSpy.getLongitude());
    }

    @Test
    public void getLatitude() throws Exception {
        GeoPoint userPosition = new GeoPoint(49.012941, 8.404409);
        GpsObject gpsObjectSpy = Mockito.spy(new GpsObject(new Date(), userPosition));

        Assert.assertEquals(userPosition.getLatitude(), gpsObjectSpy.getLatitude());
    }

    @Test
    public void setLongitude() throws Exception {
        GeoPoint userPosition = new GeoPoint(0.0, 0.0);
        GpsObject gpsObjectSpy = Mockito.spy(new GpsObject(new Date(), userPosition));
        gpsObjectSpy.setLongitude(8.404409);

        Assert.assertEquals(8.404409, gpsObjectSpy.getLongitude());
    }

    @Test
    public void setLatitude() throws Exception {
        GeoPoint userPosition = new GeoPoint(0.0, 0.0);
        GpsObject gpsObjectSpy = Mockito.spy(new GpsObject(new Date(), userPosition));
        gpsObjectSpy.setLatitude(49.012941);

        Assert.assertEquals(49.012941, gpsObjectSpy.getLatitude());
    }

    @Test
    public void describeContents() throws Exception {
        GpsObject gpsObjectSpy = Mockito.spy(new GpsObject(new Date(), new GeoPoint(0.0, 0.0)));

        Assert.assertEquals(0, gpsObjectSpy.describeContents());
    }

    @Test
    public void writeToParcel() throws Exception {
        GpsObject gpsObjectSpy = Mockito.spy(new GpsObject(new Date(), new GeoPoint(0.0, 0.0)));
        Parcel parcelMock = mock(Parcel.class);
        gpsObjectSpy.writeToParcel(parcelMock, 0);
        
        verify(parcelMock, times(2)).writeDouble(anyDouble());
        verify(parcelMock, times(1)).writeLong(anyInt());
    }

}