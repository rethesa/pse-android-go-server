package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationOverTimeImpl;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.*;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.IOException;

import edu.kit.pse.bdhkw.client.communication.CreateGroupRequest;
import edu.kit.pse.bdhkw.client.communication.CreateLinkRequest;
import edu.kit.pse.bdhkw.client.view.MainActivity;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.*;

/**
 * Created by Theresa on 28.02.2017.
 */

@Config(manifest= Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class GroupClientTest {

    private Activity activity;

    @Mock
    private CreateLinkRequest createLinkRequestMock;

    @Rule
    //public MockWebServer mockWebServer;
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
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
    }

    @Test
    public void testCreateInviteLink() throws IOException, InterruptedException {
        GroupClient group = new GroupClient("Group", createLinkRequestMock);
        try {
            activity = Robolectric.setupActivity(MainActivity.class);
        } catch (Exception e){}
        group.createInviteLink(activity);

        verify(createLinkRequestMock).setSenderDeviceId("kdjfksjdf");


        /*MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();
        CreateLinkRequest createLinkRequest = new CreateLinkRequest();
        GroupClient groupClient = new GroupClient("Name", createLinkRequest);
        groupClient.createInviteLink(mainActivity);
        */

        /*
        new MockServerClient("localhost", 1090).verify(
            request()
                .withMethod("POST")
                .withPath("/login")
                .withBody(exact("{username: 'foo', password: 'bar'}"))
                .withCookies(
                        new Cookie("sessionId", ".*")
                ),
            VerificationTimes.exactly(1)
    );*/

    }

}