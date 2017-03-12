package edu.kit.pse.bdhkw.client.controller.objectStructure;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.provider.Settings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.*;

import edu.kit.pse.bdhkw.client.communication.DeleteUserRequest;
import edu.kit.pse.bdhkw.client.communication.RegistrationRequest;
import edu.kit.pse.bdhkw.client.communication.Request;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.view.MainActivity;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Test classes for registration and delte account.
 * @author Theresa Heine
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(AccountHandler.class)
public class AccountHandlerTest {

    @Mock
    private MainActivity mainActivityMock;

    @Before
    public void setUp() throws Exception {
        mainActivityMock = mock(MainActivity.class);
    }

    @Test
    public void testRegisterUser() throws Exception {
        AccountHandler accountHandlerSpy = Mockito.spy(new AccountHandler());
        RegistrationRequest registrationRequestMock = mock(RegistrationRequest.class);
        PowerMockito.whenNew(RegistrationRequest.class).withAnyArguments().thenReturn(registrationRequestMock);
        Intent intentMock = mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                withArguments(any(Context.class),any(Class.class)).thenReturn(intentMock);
        doReturn(intentMock).when(intentMock).putExtra(anyString(),any(Request.class));
        doReturn("asdf1234").when(accountHandlerSpy).getDeviceId(any(Activity.class));
        accountHandlerSpy.registerUser(mainActivityMock, "UserName");

        verify(registrationRequestMock).setSenderDeviceId("asdf1234");
        verify(registrationRequestMock).setUserName("UserName");
        verify(accountHandlerSpy,atLeast(1)).getDeviceId(any(Activity.class));
    }

    @Test
    public void testDeleteUserAccount() throws Exception {
        AccountHandler accountHandlerSpy = Mockito.spy(new AccountHandler());
        DeleteUserRequest deleteUserRequestMock = mock(DeleteUserRequest.class);
        PowerMockito.whenNew(DeleteUserRequest.class).withAnyArguments().thenReturn(deleteUserRequestMock);
        Intent intentMock = mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                withArguments(any(Context.class),any(Class.class)).thenReturn(intentMock);
        doReturn(intentMock).when(intentMock).putExtra(anyString(),any(Request.class));
        doReturn("asdf1234").when(accountHandlerSpy).getDeviceId(any(Activity.class));
        accountHandlerSpy.deleteUserAccount(mainActivityMock);

        verify(deleteUserRequestMock).setSenderDeviceId("asdf1234");
        verify(accountHandlerSpy,atLeast(1)).getDeviceId(any(Activity.class));
    }

    @Test (expected = NullPointerException.class)
    public void getDeviceId() {
        AccountHandler accountHandlerSpy = new AccountHandler();
        accountHandlerSpy.getDeviceId(mainActivityMock);
    }

}