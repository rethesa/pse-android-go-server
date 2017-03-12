package edu.kit.pse.bdhkw.client.model.objectStructure;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

/**
 * Test class for GoService.
 * @author Theresa Heine
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
public class GoServiceTest {

    @Test
    public void activateGoStatus() throws Exception {
        GroupClient group = new GroupClient("Group");
        GoService goService = new GoService(group);
        goService.activateGoStatus();

        Assert.assertTrue(goService.getGoStatus());
    }

    @Test
    public void deactivateGoStatus() throws Exception {
        GroupClient group = new GroupClient("Group");
        GoService goService = new GoService(group);
        goService.deactivateGoStatus();

        Assert.assertFalse(goService.getGoStatus());
    }

    @Test
    public void getGoStatus() throws Exception {
        GroupClient group = new GroupClient("Group");
        GoService goService = new GoService(group);

        Assert.assertNotNull(goService.getGoStatus());
    }

}