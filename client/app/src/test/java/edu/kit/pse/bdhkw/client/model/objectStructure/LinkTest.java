package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.os.Parcel;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test cases for Link which will be sent to someone to join a group in goapp.
 * @author Theresa Heine
 * @version 1.0
 */
public class LinkTest {


    @Test
    public void testToString() throws Exception {
        Link link = new Link("url", "GroupName", "secret");

        Assert.assertEquals("url/GroupName/secret", link.toString());
    }

    @Test
    public void describeContents() throws Exception {
        Link link = new Link();

        Assert.assertEquals(0, link.describeContents());
    }

    @Test
    public void writeToParcel() throws Exception {
        Link link = new Link("url", "GroupName", "secret");
        Parcel parcleMock = mock(Parcel.class);
        link.writeToParcel(parcleMock, anyInt());

        verify(parcleMock, times(3)).writeString(anyString());
    }

    @Test
    public void setUrl() throws Exception {
        Link link = new Link("url", "GroupName", "secret");
        link.setUrl("http");

        Assert.assertEquals("http", link.getUrl());
    }

    @Test
    public void getUrl() throws Exception {
        Link link = new Link("url", "GroupName", "secret");

        Assert.assertEquals("url", link.getUrl());
    }

    @Test
    public void setGroupName() throws Exception {
        Link link = new Link("url", "GroupName", "secret");
        link.setGroupName("NewGroupName");

        Assert.assertEquals("NewGroupName", link.getGroupName());
    }

    @Test
    public void getGroupName() throws Exception {
        Link link = new Link("url", "GroupName", "secret");

        Assert.assertEquals("GroupName", link.getGroupName());
    }

    @Test
    public void setSecret() throws Exception {
        Link link = new Link("url", "GroupName", "secret");
        link.setSecret("newSecret");

        Assert.assertEquals("newSecret", link.getSecret());
    }

    @Test
    public void getSecret() throws Exception {
        Link link = new Link("url", "GroupName", "secret");

        Assert.assertEquals("secret", link.getSecret());
    }

}