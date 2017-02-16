package edu.kit.pse.bdhkw.client.controller.database;

import android.app.Activity;
import android.content.Context;
import android.test.mock.MockContext;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.osmdroid.util.GeoPoint;

import java.util.LinkedList;
import java.util.List;

import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;
import edu.kit.pse.bdhkw.client.view.MainActivity;

import static org.junit.Assert.*;

/**
 * Created by Theresa on 15.02.2017.
 */
public class GroupServiceTest {

    @Mock
    private GroupService groupServiceMock;


    private GroupClient group1;
    private GroupClient group2;

    @Before
    public void setUp() {
        groupServiceMock = Mockito.mock(GroupService.class);
        List<UserDecoratorClient> list = new LinkedList<>();
        list = null;

        GeoPoint gp1 = new GeoPoint(34.66, 34.99);
        group1 = new GroupClient("Gruppe1", "14.03.2017", "14:45", "Mensa am Adenauering", gp1, list);
        GeoPoint gp2 = new GeoPoint(40.55, 10.77);
        group2 = new GroupClient("Gruppe2", "15.02.1995", "02:05", "Uni", gp2, list);

        groupServiceMock.insertNewGroup(group1);
        groupServiceMock.insertNewGroup(group2);
    }

    @Test
    public void readOneGroupRowTest() {
        GroupClient returnGroup = groupServiceMock.readOneGroupRow(group1.getGroupName());
        assertEquals(group1.getGroupName(), returnGroup.getGroupName());
    }






    @After
    public void cleanUp() {
        groupServiceMock.deleteAllGroups();
    }

}