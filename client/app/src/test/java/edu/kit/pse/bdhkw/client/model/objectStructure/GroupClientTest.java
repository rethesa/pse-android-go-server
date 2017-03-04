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
    private CreateLinkRequest createLinkRequestMock;
    private MainActivity mainActivityMock;

    public GroupClientTest() {

    }

    //@Rule
    //public MockWebServer mockWebServer;
    //public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        mainActivityMock = Mockito.mock(MainActivity.class);

    }

    @After
    public void tearDown() throws Exception {

    }


  /*  @Test
    public void testDeviceId() {
        //mainActivity = Robolectric.setupActivity(MainActivity.class);
        //Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        Bundle savedInstanceState = new Bundle();
        Activity activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .restoreInstanceState(savedInstanceState)
                .get();

        String id = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //RobolectricTestRunner.injectEnvironment();
    }*/

    @Test
    public void testCreateInviteLink() throws Exception {
            final GroupClient group = new GroupClient("Group", createLinkRequestMock);
            final GroupClient groupSpy = Mockito.spy(group);

            Assert.assertNotNull(mainActivityMock);
            Intent intent = mock(Intent.class);
            doReturn(intent).when(intent).putExtra(anyString(),any(Request.class));
            PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).withArguments(any(Context.class),any(Class.class)).thenReturn(intent);
            //mainActivityRobo = Robolectric.setupActivity(MainActivity.class);

            //PowerMockito.mockStatic(Settings.class);
            //BDDMockito.given(Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
               //     Settings.Secure.ANDROID_ID)).willReturn("asdf1234")
            Mockito.doReturn("asdf1234").when(groupSpy).readDeviceId(any(Activity.class));
            //doReturn("asdf1234").when(groupSpy.readDeviceId(mainActivityMock));

            groupSpy.createInviteLink(mainActivityMock);

            Mockito.verify(createLinkRequestMock).setSenderDeviceId("asdf1234");
            verify(groupSpy,atLeast(1)).readDeviceId(any(Activity.class));


            /*MockWebServer mockWebServer = new MockWebServer();
            mockWebServer.start();
            CreateLinkRequest createLinkRequest = new CreateLinkRequest();
            groupClient.createInviteLink(mainActivity);
            */

            /*
                    .withPath("/login")
                    .withBody(exact("{username: 'foo', password: 'bar'}"))
            new MockServerClient("localhost", 1090).verify(
                request()
                    .withMethod("POST")
                    .withCookies(
                            new Cookie("sessionId", ".*")
                    ),
                VerificationTimes.exactly(1)
        );*/

    }

}