package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.test.ActivityInstrumentationTestCase2;

import org.apache.tools.ant.Main;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationOverTimeImpl;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.Robolectric;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.kit.pse.bdhkw.client.communication.CreateLinkRequest;
import edu.kit.pse.bdhkw.client.communication.Request;
import edu.kit.pse.bdhkw.client.view.MainActivity;



/**
 * Created by Theresa on 28.02.2017.
 */

//@Config(manifest= Config.NONE)
//@RunWith(RobolectricTestRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest(GroupClient.class)
public class GroupClientTest {

    @Mock
    private MainActivity mainActivityMock;

    public GroupClientTest() {

    }

    @Before
    public void setUp() throws Exception {
        mainActivityMock = Mockito.mock(MainActivity.class);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateInviteLink() throws Exception {
            GroupClient groupSpy = Mockito.spy(new GroupClient("Group"));
            //mock createLinkRequest
            CreateLinkRequest createLinkRequestMock = mock(CreateLinkRequest.class);
            PowerMockito.whenNew(CreateLinkRequest.class).withAnyArguments().thenReturn(createLinkRequestMock); //mock Constructor of Request
            //doCallRealMethod().when(createLinkRequestMock).setSenderDeviceId(anyString()); //use real method of Request

            //mock Intent
            Intent intent = mock(Intent.class);
            doReturn(intent).when(intent).putExtra(anyString(),any(Request.class));
            PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                    withArguments(any(Context.class),any(Class.class)).thenReturn(intent);

            //mock device Id that value is asdf1234
            Mockito.doReturn("asdf1234").when(groupSpy).readDeviceId(any(Activity.class));
            groupSpy.createInviteLink(mainActivityMock);

            Mockito.verify(createLinkRequestMock).setSenderDeviceId("asdf1234");
            verify(createLinkRequestMock).setTargetGroupName("Group");
            verify(groupSpy,atLeast(1)).readDeviceId(any(Activity.class));
    }

}