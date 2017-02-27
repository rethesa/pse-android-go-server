package edu.kit.pse.bdhkw.client.controller.objectStructure;

import android.support.test.runner.AndroidJUnit4;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import io.appflate.restmock.RESTMockServerStarter;
import io.appflate.restmock.android.AndroidAssetsFileParser;
import io.appflate.restmock.android.AndroidLogger;
import okhttp3.OkHttpClient;

import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Tests to ensure that the server requests haven been sent.
 * @author Theresa Heine
 */
@RunWith(AndroidJUnit4.class)
public class AccountHandlerTest {

    @Before
    public void setUp() throws Exception {
        RESTMockServerStarter.startSync(new AndroidAssetsFileParser(getContext()), new AndroidLogger());

    }

    /*new MockServerClient("localhost", 1090).verify(
            request()
    .withMethod("POST")
    .withPath("/login")
    .withBody(exact("{username: 'foo', password: 'bar'}"))
            .withCookies(
            new Cookie("sessionId", ".*")
    ),
            VerificationTimes.exactly(1)
            );*/

    @After
    public void tearDown() throws Exception {

    }

}