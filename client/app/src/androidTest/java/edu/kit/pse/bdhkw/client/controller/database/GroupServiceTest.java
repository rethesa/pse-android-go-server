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

import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests for insert, read, delete and update of the group table in database.
 * @author Theresa Heine
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
public class GroupServiceTest {

    private GroupService groupService;

    @Before
    public void setUp() {
        groupService = new GroupService(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void tearDown() {
        groupService.deleteAllGroups();
    }

    @Test
    public void testInsertNewGroup() {
        GroupClient group1 = new GroupClient("Gruppe1");
        groupService.deleteAllGroups();
        groupService.insertNewGroup(group1);
        List<String> allGroupNames = groupService.readAllGroupNames();

        assertThat(allGroupNames.size(), is(1));
        Assert.assertEquals(group1.getGroupName(), allGroupNames.get(0));
    }

    @Test
    public void testReadOneGroupRow() {
        GroupClient group2 = new GroupClient("Gruppe2");
        groupService.insertNewGroup(group2);
        GroupClient returnGroup2 = groupService.readOneGroupRow(group2.getGroupName());
        double delta = 0.00001; //allowed maximum difference between expected and actual latitude and longitude

        Assert.assertEquals(group2.getGroupName(), returnGroup2.getGroupName());
        Assert.assertEquals(group2.getGoService().getGoStatus(), returnGroup2.getGoService().getGoStatus());
        Assert.assertEquals(group2.getAppointment().getAppointmentDate().getDate(), returnGroup2.getAppointment()
                .getAppointmentDate().getDate());
        Assert.assertEquals(group2.getAppointment().getAppointmentDate().getTime(), returnGroup2.getAppointment()
                .getAppointmentDate().getTime());
        Assert.assertEquals(group2.getAppointment().getAppointmentDestination().getDestinationName(), returnGroup2
                .getAppointment().getAppointmentDestination().getDestinationName());
        Assert.assertEquals(group2.getAppointment().getAppointmentDestination().getDestinationPosition().getLatitude(),
                returnGroup2.getAppointment().getAppointmentDestination().getDestinationPosition().getLatitude(), delta);
        Assert.assertEquals(group2.getAppointment().getAppointmentDestination().getDestinationPosition().getLongitude(),
                returnGroup2.getAppointment().getAppointmentDestination().getDestinationPosition().getLongitude(), delta);

        //TODO test for goStatus = true
    }

    @Test
    public void testReadAllGroupNames() {
        groupService.deleteAllGroups();
        GroupClient group3 = new GroupClient("Gruppe3");
        GroupClient group4 = new GroupClient("Gruppe4");
        GroupClient group5 = new GroupClient("Gruppe5");
        groupService.insertNewGroup(group3);
        groupService.insertNewGroup(group4);
        groupService.insertNewGroup(group5);
        List<String> allGroupNames = groupService.readAllGroupNames();

        assertThat(allGroupNames.size(), is(3));
        Assert.assertTrue(allGroupNames.get(0).equals(group3.getGroupName()) || allGroupNames.get(0)
                .equals(group4.getGroupName()) || allGroupNames.get(0).equals(group5.getGroupName()));
    }

    @Test
    public void testDeleteAllGroups() {
        groupService.deleteAllGroups();
        List<String> allGroupNames = groupService.readAllGroupNames();

        assertThat(allGroupNames.size(), is(0));
    }

    @Test
    public void testDeleteOneGroupRow() {
        GroupClient group6 = new GroupClient("Gruppe6");
        groupService.insertNewGroup(group6);
        int before = groupService.readAllGroupNames().size();
        groupService.deleteOneGroupRow(group6.getGroupName());
        int after = groupService.readAllGroupNames().size();

        Assert.assertEquals(before-1, after);
    }

    @Test
    public void testUpdateGroupData() {
        GroupClient group7 = new GroupClient("Gruppe7");
        groupService.insertNewGroup(group7);
        GroupClient returnGroup7 = groupService.readOneGroupRow(group7.getGroupName());
        returnGroup7.getAppointment().getAppointmentDate().setDate("28.02.1990");
        returnGroup7.getAppointment().getAppointmentDate().setTime("14:35");
        groupService.updateGroupData(group7.getGroupName(), group7);
        GroupClient updateReturnGroup7 = groupService.readOneGroupRow(group7.getGroupName());

        Assert.assertEquals(group7.getAppointment().getAppointmentDate().getDate(), updateReturnGroup7
                .getAppointment().getAppointmentDate().getDate());
        Assert.assertEquals(group7.getAppointment().getAppointmentDate().getTime(), updateReturnGroup7
                .getAppointment().getAppointmentDate().getTime());
    }

}