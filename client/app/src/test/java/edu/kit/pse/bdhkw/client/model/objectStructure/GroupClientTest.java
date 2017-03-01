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
import org.robolectric.Robolectric;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.kit.pse.bdhkw.client.communication.CreateLinkRequest;
import edu.kit.pse.bdhkw.client.view.MainActivity;



/**
 * Created by Theresa on 28.02.2017.
 */

//@Config(manifest= Config.NONE)
//@RunWith(RobolectricTestRunner.class)
@RunWith(MockitoJUnitRunner.class)
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(Settings.class)
public class GroupClientTest extends ActivityInstrumentationTestCase2<MainActivity> {

    @Mock
    private CreateLinkRequest createLinkRequestMock;
    private MainActivity mainActivityMock;

    public GroupClientTest() {
        super(MainActivity.class);
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
    public void testCreateInviteLink() throws IOException, InterruptedException {
        final GroupClient group = new GroupClient("Group", createLinkRequestMock);
        final GroupClient groupSpy = Mockito.spy(group);

        Assert.assertNotNull(mainActivityMock);
        //mainActivityRobo = Robolectric.setupActivity(MainActivity.class);

        //PowerMockito.mockStatic(Settings.class);
        //BDDMockito.given(Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
           //     Settings.Secure.ANDROID_ID)).willReturn("asdf1234");

        Mockito.when(groupSpy.readDeviceId(mainActivityMock)).thenReturn("asdf1234");
        //doReturn("asdf1234").when(groupSpy.readDeviceId(mainActivityMock));

        group.createInviteLink(mainActivityMock);

        Mockito.verify(createLinkRequestMock).setSenderDeviceId("asdf1234");


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