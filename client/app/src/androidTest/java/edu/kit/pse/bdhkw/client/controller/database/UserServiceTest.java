package edu.kit.pse.bdhkw.client.controller.database;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupMemberClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

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

        Assert.assertEquals("User1", groupMembers.get(0));
        Assert.assertTrue(groupMembers.size() == 1);
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

        //TODO test for -1
    }

    @Test
    public void testDeleteAllUserAndGroups() {
        userService.deleteAllUserAndGroups();
        //TODO dont know how to test this, because I nerver get all entries of the user table
    }

    @Test
    public void testDeleteUserFromGroup() {
        GroupAdminClient user8 = new GroupAdminClient("User8", 8888);
        GroupMemberClient user9 = new GroupMemberClient("User9", 9999);
        userService.insertUserData("Gruppe5", user8);
        userService.insertUserData("Gruppe5", user9);
        userService.deleteUserFromGroup("Gruppe5", user8);
        List<String> groupMembers = userService.readAllGroupMembers("Gruppe5");

        Assert.assertThat(groupMembers.size(), is(1));
    }

    @Test
    public void testDeleteAllGroupMembers() {
        GroupAdminClient user10 = new GroupAdminClient("User10", 101010);
        GroupMemberClient user11 = new GroupMemberClient("User11", 111111);
        userService.insertUserData("Gruppe6", user10);
        userService.insertUserData("Gruppe6", user11);
        userService.deleteAllGroupMembers("Gruppe6");
        List<String> groupMembers = userService.readAllGroupMembers("Gruppe6");

        Assert.assertThat(groupMembers.size(), is(0));
    }

    @Test
    public void testUpadteGroupNameInAlloc() {
        GroupMemberClient user12 = new GroupMemberClient("User12", 121212);
        GroupAdminClient user13 = new GroupAdminClient("User13", 131313);
        userService.insertUserData("Gruppe7", user12);
        userService.insertUserData("Gruppe7", user13);
        userService.updateGroupNameInAlloc("Gruppe7", "Gruppe777");
        List<String> groupMembers = userService.readAllGroupMembers("Gruppe777");

        Assert.assertThat(groupMembers.size(), is(2));
        //Should throw exception
        //List<String> groupMembersOfNotExistingGroup = userService.readAllGroupMembers("Gruppe7");
    }

    @Test
    public void testUpdateUserName() {
        SimpleUser simpleUser = new SimpleUser("Simple User", 141414);
        GroupMemberClient user14 = new GroupMemberClient(simpleUser.getName(), simpleUser.getUserID());
        userService.insertUserData("Gruppe8", user14);
        userService.insertUserData("Gruppe9", user14);
        simpleUser.setName("User14");
        GroupMemberClient newUser14 = new GroupMemberClient(simpleUser.getName(), simpleUser.getUserID());
        userService.updateUserName(newUser14);
        List<String> memberListGruppe8 = userService.readAllGroupMembers("Gruppe8");
        List<String> memberListGruppe9 = userService.readAllGroupMembers("Gruppe9");

        Assert.assertTrue(memberListGruppe8.get(0).equals(newUser14.getName()));
        Assert.assertTrue(memberListGruppe9.get(0).equals(newUser14.getName()));
    }

    @Test
    public void testUpdateGroupMemberToAdmin() {
        GroupMemberClient user15 = new GroupMemberClient("User15", 151515);
        userService.insertUserData("Gruppe10", user15);
        userService.updateGroupMemberToAdmin("Gruppe10", user15);
        int isAdmin = userService.readAdminOrMemberStatus("Gruppe10", user15.getUserID());

        Assert.assertTrue(isAdmin == 1);
    }
}
