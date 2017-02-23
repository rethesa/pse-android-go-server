package edu.kit.pse.bdhkw.client.controller.database;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import javax.sql.DataSource;

import edu.kit.pse.bdhkw.client.model.database.DBHelperGroup;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for insert, read, delete and update of the group table in database.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class GroupServiceTest {

   private GroupService groupService;

    @Before
    public void setUp() {
        groupService = new GroupService(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testDeleteAllGroups() {
        groupService.deleteAllGroups();
        List<String> allGroupNames = groupService.readAllGroupNames();

        assertThat(allGroupNames.size(), is(0));
    }




}