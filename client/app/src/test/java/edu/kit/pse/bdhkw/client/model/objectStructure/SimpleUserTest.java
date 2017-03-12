package edu.kit.pse.bdhkw.client.model.objectStructure;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;

/**
 * Tests for Simple User.
 * @author Theresa Heine
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class SimpleUserTest {

    @Test
    public void setName() throws Exception {
        SimpleUser simpleUser = new SimpleUser();
        simpleUser.setName("SimpleUser");

        Assert.assertEquals("SimpleUser", simpleUser.getName());
    }

    @Test
    public void setId() throws Exception {
        SimpleUser simpleUser = new SimpleUser();
        simpleUser.setId(123456789);

        Assert.assertEquals(123456789, simpleUser.getUserID());
    }

    @Test
    public void setDeviceId() throws Exception {
        SimpleUser simpleUser = Mockito.spy(new SimpleUser());
        simpleUser.setDeviceId("asdf1234");

        Mockito.verify(simpleUser, times(1)).setDeviceId(anyString());
    }

    @Test
    public void getName() throws Exception {
        SimpleUser simpleUser = new SimpleUser("User", 123456789);

        Assert.assertEquals("User", simpleUser.getName());
    }

    @Test
    public void getUserID() throws Exception {
        SimpleUser simpleUser = new SimpleUser("User", 123456789);

        Assert.assertEquals(123456789, simpleUser.getUserID());
    }

}