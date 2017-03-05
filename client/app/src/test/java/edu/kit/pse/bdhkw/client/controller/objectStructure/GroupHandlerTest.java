package edu.kit.pse.bdhkw.client.controller.objectStructure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.StringJoiner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.kit.pse.bdhkw.client.communication.CreateGroupRequest;
import edu.kit.pse.bdhkw.client.communication.DeleteGroupRequest;
import edu.kit.pse.bdhkw.client.communication.DeleteUserRequest;
import edu.kit.pse.bdhkw.client.communication.JoinGroupRequest;
import edu.kit.pse.bdhkw.client.communication.Request;
import edu.kit.pse.bdhkw.client.model.objectStructure.Link;
import edu.kit.pse.bdhkw.client.view.MainActivity;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Test class for creating, joining and deleting a group.
 * @author Theresa Heine
 * @verion 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GroupHandler.class)
public class GroupHandlerTest {

    @Mock
    private MainActivity mainActivityMock;

    @Before
    public void setUp() throws Exception {
        mainActivityMock = mock(MainActivity.class);
    }

    @Test
    public void createGroup() throws Exception {
        GroupHandler groupHandlerSpy = Mockito.spy(new GroupHandler());
        CreateGroupRequest createGroupRequestMock = mock(CreateGroupRequest.class);
        PowerMockito.whenNew(CreateGroupRequest.class).withAnyArguments().thenReturn(createGroupRequestMock);
        Intent intentMock = mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                withArguments(any(Context.class),any(Class.class)).thenReturn(intentMock);
        doReturn(intentMock).when(intentMock).putExtra(anyString(),any(Request.class));
        doReturn("asdf1234").when(groupHandlerSpy).getDeviceId(any(Activity.class));
        String groupName = "Group";
        groupHandlerSpy.createGroup(mainActivityMock, groupName);

        verify(createGroupRequestMock).setSenderDeviceId("asdf1234");
        verify(createGroupRequestMock).setNewGroupName("Group");
        verify(groupHandlerSpy,atLeast(1)).getDeviceId(any(Activity.class));
    }

    @Test
    public void joinGroup() throws Exception {
        GroupHandler groupHandlerSpy = Mockito.spy(new GroupHandler());
        JoinGroupRequest joinGroupRequestMock = mock(JoinGroupRequest.class);
        PowerMockito.whenNew(JoinGroupRequest.class).withAnyArguments().thenReturn(joinGroupRequestMock);
        Intent intentMock = mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                withArguments(any(Context.class),any(Class.class)).thenReturn(intentMock);
        doReturn(intentMock).when(intentMock).putExtra(anyString(),any(Request.class));
        doReturn("asdf1234").when(groupHandlerSpy).getDeviceId(any(Activity.class));
        String groupName = "Groupname";
        String secret = "asdfghjkl";
        String[] groupAndLink = {groupName, secret};
        Link linkMock = mock(Link.class);
        PowerMockito.whenNew(Link.class).withParameterTypes(String.class, String.class, String.class).
                withArguments("url", groupName, secret).thenReturn(linkMock);
        groupHandlerSpy.joinGroup(mainActivityMock, groupAndLink);

        verify(joinGroupRequestMock).setSenderDeviceId("asdf1234");
        verify(joinGroupRequestMock).setLink(linkMock);
        verify(groupHandlerSpy,atLeast(1)).getDeviceId(any(Activity.class));
    }

    @Test
    public void deleteGroup() throws Exception {
        GroupHandler groupHandlerSpy = Mockito.spy(new GroupHandler());
        DeleteGroupRequest deleteGroupRequestMock = mock(DeleteGroupRequest.class);
        PowerMockito.whenNew(DeleteGroupRequest.class).withAnyArguments().thenReturn(deleteGroupRequestMock);
        Intent intentMock = mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                withArguments(any(Context.class),any(Class.class)).thenReturn(intentMock);
        doReturn(intentMock).when(intentMock).putExtra(anyString(),any(Request.class));
        doReturn("asdf1234").when(groupHandlerSpy).getDeviceId(any(Activity.class));
        String groupName = "Groupname";
        groupHandlerSpy.deleteGroup(mainActivityMock, groupName);

        verify(deleteGroupRequestMock).setSenderDeviceId("asdf1234");
        verify(deleteGroupRequestMock).setTargetGroupName("Groupname");
        verify(groupHandlerSpy,atLeast(1)).getDeviceId(any(Activity.class));
    }

}