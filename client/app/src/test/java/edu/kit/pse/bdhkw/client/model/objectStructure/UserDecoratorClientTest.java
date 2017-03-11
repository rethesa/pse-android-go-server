package edu.kit.pse.bdhkw.client.model.objectStructure;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Test class for abstract UserDecoratorClient.
 * @author Theresa Heine
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class UserDecoratorClientTest {

    @Test
    public void getName() throws Exception {
        UserDecoratorClient user = new GroupAdminClient("Admin", 1111);

        Assert.assertEquals("Admin", user.getName());
    }

    @Test
    public void getUserID() throws Exception {
        UserDecoratorClient user = new GroupAdminClient("Admin", 1111);

        Assert.assertEquals(1111, user.getUserID());
    }

    @Test
    public void getView() throws Exception {
        UserDecoratorClient admin = new GroupAdminClient("Admin", 1111);
        UserDecoratorClient member = new GroupMemberClient("Member", 2222);

        Assert.assertEquals(true, admin.getView());
        Assert.assertEquals(false, member.getView());
    }

    @Test
    public void isAdmin() throws Exception {
        UserDecoratorClient admin = new GroupAdminClient("Admin", 1111);
        UserDecoratorClient member = new GroupMemberClient("Member", 2222);

        Assert.assertEquals(true, admin.isAdmin());
        Assert.assertEquals(false, member.isAdmin());
    }

}