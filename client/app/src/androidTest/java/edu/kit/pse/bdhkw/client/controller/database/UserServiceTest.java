package edu.kit.pse.bdhkw.client.controller.database;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupMemberClient;

import static org.hamcrest.CoreMatchers.is;

/**
 * Tests for insert, read, delete and update of the group table in database.
 * @author Theresa Heine
 */
@RunWith(AndroidJUnit4.class)
public class UserServiceTest {

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void tearDown() {
        userService.deleteAllUserAndGroups();
    }

    @Test
    public void testInsertUserData() {
        GroupMemberClient user1 = new GroupMemberClient("User1", 1111);
        userService.insertUserData("Gruppe1", user1);
        List<String> groupMembers  = userService.readAllGroupMembers("Gruppe1");

        Assert.assertEquals(user1.getName(), groupMembers.get(0));
    }

    @Test
    public void testReadAllGroupMembers() {
        GroupMemberClient user2 = new GroupMemberClient("User2", 2222);
        GroupMemberClient user3 = new GroupMemberClient("User3", 3333);
        userService.insertUserData("Gruppe2", user2);
        userService.insertUserData("Gruppe2", user3);
        List<String> allGroupMembers = userService.readAllGroupMembers("Gruppe2");

        Assert.assertThat(allGroupMembers.size(), is(2));
        Assert.assertTrue(allGroupMembers.get(0).equalsIgnoreCase(user2.getName()) || allGroupMembers.get(0).equals(user3.getName()));

    }

    @Test
    public void testReadAllGroupMemberIds() {
        GroupMemberClient user4 = new GroupMemberClient("User4", 4444);
        GroupMemberClient user5 = new GroupMemberClient("User5", 5555);
        userService.insertUserData("Gruppe3", user4);
        userService.insertUserData("Gruppe3", user5);
        List<Integer> allGroupMemberIds = userService.readAllGroupMemberIds("Gruppe3");

        Assert.assertThat(allGroupMemberIds.size(), is(2));
        Assert.assertTrue(allGroupMemberIds.get(0) == user4.getUserID() || allGroupMemberIds.get(0) == user5.getUserID());
    }

    @Test
    public void testReadAdminOrMemberStatus() {
        GroupMemberClient user6 = new GroupMemberClient("User6", 6666);
        GroupAdminClient user7 = new GroupAdminClient("User7", 7777);
        userService.insertUserData("Gruppe4", user6);
        userService.insertUserData("Gruppe4", user7);
        int memberIsAdmin = userService.readAdminOrMemberStatus("Gruppe4", user6.getUserID());
        int adminIsAdmin = userService.readAdminOrMemberStatus("Gruppe4", user7.getUserID());

        Assert.assertTrue(0 == memberIsAdmin);
        Assert.assertTrue(1 == adminIsAdmin);
    }

    @Test
    public void testDeleteAllUserAndGroups() {

    }

    @Test
    public void testDeleteUserFromGroup() {

    }

    @Test
    public void testDeleteAllGroupMembers() {

    }

    @Test
    public void testUpadteGroupNameInAlloc() {

    }

    @Test
    public void testUpdateUserName() {

    }

    @Test
    public void testUpdateGroupMemberToAdmin() {

    }
}
