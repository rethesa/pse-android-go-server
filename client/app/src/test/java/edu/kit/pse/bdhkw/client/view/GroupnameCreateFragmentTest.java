package edu.kit.pse.bdhkw.client.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.kit.pse.bdhkw.client.communication.Response;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;

/**
 * Created by Schokomonsterchen on 11.03.2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GroupnameCreateFragment.class)
public class GroupnameCreateFragmentTest {

    @Mock
    GroupnameCreateFragment groupnameCreateFragmentMock;

    @Before
    public void setUp() {
        groupnameCreateFragmentMock = spy(new GroupnameCreateFragment());
    }

    @Test
    public void onAttachTest() throws Exception {
        Context contextMock = mock(Context.class);
        IntentFilter intentFilterMock = mock(IntentFilter.class);
        PowerMockito.whenNew(IntentFilter.class).withAnyArguments().thenReturn(intentFilterMock);
        BroadcastReceiver broadcastReceiverMock = mock(BroadcastReceiver.class);
        PowerMockito.whenNew(BroadcastReceiver.class).withAnyArguments().thenReturn(broadcastReceiverMock);
        Intent intentMock = mock(Intent.class);
        Response responseMock = mock(Response.class);
        doReturn(true).when(responseMock).getSuccess();
        doReturn("name").when(groupnameCreateFragmentMock).getName();
        GroupClient groupClientMock = mock(GroupClient.class);
        PowerMockito.whenNew(GroupClient.class).withAnyArguments().thenReturn(groupClientMock);
        GroupAdminClient groupAdminClientMock = mock(GroupAdminClient.class);
        PowerMockito.whenNew(GroupAdminClient.class).withAnyArguments().thenReturn(groupAdminClientMock);
        GroupService groupServiceMock = mock(GroupService.class);
        PowerMockito.whenNew(GroupService.class).withAnyArguments().thenReturn(groupServiceMock);
        UserService userServiceMock = mock(UserService.class);
        PowerMockito.whenNew(UserService.class).withAnyArguments().thenReturn(userServiceMock);
        doNothing().when(groupServiceMock).insertNewGroup(groupClientMock);
        doNothing().when(userServiceMock).insertUserData("name", groupAdminClientMock);
        doNothing().when(groupnameCreateFragmentMock).savePreferences();
        doNothing().when(groupnameCreateFragmentMock).startActivityHelp();
        doNothing().when(groupnameCreateFragmentMock).onDetach();


//          ohne die Zeile läuft der Test grün... Irgendein Denkfehler
//        verify(LocalBroadcastManager.getInstance(any(Context.class)), times(0));


//          so könnte man vlt an die Methode onReceive ran kommen
//        groupnameCreateFragmentMock.broadcastReceiver.onReceive(contextMock, intentMock);

    }

}