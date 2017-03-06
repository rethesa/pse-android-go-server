package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import org.osmdroid.util.GeoPoint;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.LinkedList;
import java.util.List;

import edu.kit.pse.bdhkw.client.communication.CreateLinkRequest;
import edu.kit.pse.bdhkw.client.communication.KickMemberRequest;
import edu.kit.pse.bdhkw.client.communication.LeaveGroupRequest;
import edu.kit.pse.bdhkw.client.communication.MakeAdminRequest;
import edu.kit.pse.bdhkw.client.communication.RenameGroupRequest;
import edu.kit.pse.bdhkw.client.communication.Request;
import edu.kit.pse.bdhkw.client.communication.UpdateRequest;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.controller.objectStructure.GroupHandler;
import edu.kit.pse.bdhkw.client.model.database.DBHelperGroup;
import edu.kit.pse.bdhkw.client.view.MainActivity;

/**
 * Test classes for GroupClient. Make sure that request to server were sent and that they carry the
 * right information.
 * @author Thersa Heine
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GroupClient.class)
public class GroupClientTest {

    @Mock
    private MainActivity mainActivityMock;

    @Before
    public void setUp() throws Exception {
        mainActivityMock = Mockito.mock(MainActivity.class);
    }

    @Test
    public void testCreateInviteLink() throws Exception {
            GroupClient groupSpy = Mockito.spy(new GroupClient("Group"));
            CreateLinkRequest createLinkRequestMock = mock(CreateLinkRequest.class);
            PowerMockito.whenNew(CreateLinkRequest.class).withAnyArguments().thenReturn(createLinkRequestMock); //mock Constructor of Request
            Intent intentMock = mock(Intent.class);
            PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                withArguments(any(Context.class),any(Class.class)).thenReturn(intentMock);
            doReturn(intentMock).when(intentMock).putExtra(anyString(),any(Request.class));
            doReturn("asdf1234").when(groupSpy).getDeviceId(any(Activity.class));
            groupSpy.createInviteLink(mainActivityMock);

            verify(createLinkRequestMock).setSenderDeviceId("asdf1234");
            verify(createLinkRequestMock).setTargetGroupName("Group");
            verify(groupSpy,atLeast(1)).getDeviceId(any(Activity.class));
    }

    @Test
    public void testGetGroupUpdate() throws Exception {
        GroupClient groupSpy = Mockito.spy(new GroupClient("Group"));
        UpdateRequest updateRequestMock = mock(UpdateRequest.class);
        PowerMockito.whenNew(UpdateRequest.class).withAnyArguments().thenReturn(updateRequestMock);
        Intent intentMock = mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                withArguments(any(Context.class), any(Class.class)).thenReturn(intentMock);
        doReturn(intentMock).when(intentMock).putExtra(anyString(), any(Request.class));
        doReturn("asdf1234").when(groupSpy).getDeviceId(any(Activity.class));
        groupSpy.getGroupUpdate(mainActivityMock);

        verify(updateRequestMock).setSenderDeviceId("asdf1234");
        verify(updateRequestMock).setTargetGroupName("Group");
        verify(groupSpy,atLeast(1)).getDeviceId(any(Activity.class));
    }

    @Test
    public void testMakeGroupMemberToAdmin() throws Exception {
        GroupClient groupSpy = Mockito.spy(new GroupClient("Group"));
        MakeAdminRequest makeAdminRequestMock = mock(MakeAdminRequest.class);
        PowerMockito.whenNew(MakeAdminRequest.class).withAnyArguments().thenReturn(makeAdminRequestMock);
        Intent intentMock = mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                withArguments(any(Context.class), any(Class.class)).thenReturn(intentMock);
        doReturn(intentMock).when(intentMock).putExtra(anyString(), any(Request.class));
        doReturn("asdf1234").when(groupSpy).getDeviceId(any(Activity.class));
        int userId = 123456789;
        groupSpy.makeGroupMemberToAdmin(mainActivityMock, userId);

        verify(makeAdminRequestMock).setSenderDeviceId("asdf1234");
        verify(makeAdminRequestMock).setTargetUserId(123456789);
        verify(makeAdminRequestMock).setTargetGroupName("Group");
        verify(groupSpy, times(1)).getDeviceId(any(Activity.class));
    }

    @Test
    public void testGetAllGroupMemberNames() throws Exception {
        GroupClient groupSpy = Mockito.spy(new GroupClient("Group"));
        UserService userServiceMock = Mockito.mock(UserService.class);
        PowerMockito.whenNew(UserService.class).withParameterTypes(Context.class).
                withArguments(any(Context.class)).thenReturn(userServiceMock);
        List<String> list = new LinkedList<>();
        list.add("Member1");
        list.add("Member2");
        doReturn(list).when(userServiceMock).readAllGroupMembers("Group");
        List<String> returnList = groupSpy.getAllGroupMemberNames(mainActivityMock);

        Assert.assertEquals("Member1", returnList.get(0));
        Assert.assertEquals("Member2", returnList.get(1));
    }

    @Test
    public void testDeleteGroupMember() throws Exception {
        GroupClient groupSpy = Mockito.spy(new GroupClient("Group"));
        KickMemberRequest kickMemberRequestMock = mock(KickMemberRequest.class);
        PowerMockito.whenNew(KickMemberRequest.class).withAnyArguments().thenReturn(kickMemberRequestMock);
        Intent intentMock = mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                withArguments(any(Context.class), any(Request.class)).thenReturn(intentMock);
        doReturn(intentMock).when(intentMock).putExtra(anyString(), any(Request.class));
        doReturn("asdf1234").when(groupSpy).getDeviceId(any(Activity.class));
        int memberId = 123456789;
        groupSpy.deleteGroupMember(mainActivityMock, memberId);

        verify(kickMemberRequestMock).setSenderDeviceId("asdf1234");
        verify(kickMemberRequestMock).setTargetMemberId(123456789);
        verify(kickMemberRequestMock).setTargetGroupName("Group");
        verify(groupSpy,atLeast(1)).getDeviceId(any(Activity.class));
    }

    @Test
    public void testLeaveGroup() throws Exception {
        GroupClient groupSpy = Mockito.spy(new GroupClient("Group"));
        LeaveGroupRequest leaveGroupRequestMock = mock(LeaveGroupRequest.class);
        PowerMockito.whenNew(LeaveGroupRequest.class).withAnyArguments().thenReturn(leaveGroupRequestMock);
        Intent intentMock = mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                withArguments(any(Context.class), any(Request.class)).thenReturn(intentMock);
        doReturn(intentMock).when(intentMock).putExtra(anyString(), any(Request.class));
        doReturn("asdf1234").when(groupSpy).getDeviceId(any(Activity.class));
        groupSpy.leaveGroup(mainActivityMock);

        verify(leaveGroupRequestMock).setSenderDeviceId("asdf1234");
        verify(leaveGroupRequestMock).setTargetGroupName("Group");
        verify(groupSpy,atLeast(1)).getDeviceId(any(Activity.class));
    }

    @Test
    public void testGetMemberTypeTrue() throws Exception {
        GroupClient groupSpy = Mockito.spy(new GroupClient("Group"));
        UserService userServiceMock = Mockito.mock(UserService.class);
        PowerMockito.whenNew(UserService.class).withParameterTypes(Context.class).
                withArguments(any(Context.class)).thenReturn(userServiceMock);
        doReturn(1).when(userServiceMock).readAdminOrMemberStatus("Group", 123456789);
        boolean value = groupSpy.getMemberType(mainActivityMock, 123456789);

        Assert.assertEquals(true, value);
    }

    @Test
    public void testGetMemberTypeFalse() throws Exception {
        GroupClient groupSpy = Mockito.spy(new GroupClient("Group"));
        UserService userServiceMock = Mockito.mock(UserService.class);
        PowerMockito.whenNew(UserService.class).withParameterTypes(Context.class).
                withArguments(any(Context.class)).thenReturn(userServiceMock);
        doReturn(0).when(userServiceMock).readAdminOrMemberStatus("Group", 123456789);
        boolean value = groupSpy.getMemberType(mainActivityMock, 123456789);

        Assert.assertEquals(false, value);
    }

    @Test
    public void testAcitvateGoService() {
        //TODO klären was genau in der Methode überhaupt passieren soll
    }

    @Test
    public void testDeactivateGoService() {
    //TODO klären was genau in der Methode überhaupt passieren soll
    }

    @Test
    public void testChangeGroupName() throws Exception {
        GroupClient groupSpy = Mockito.spy(new GroupClient("Group"));
        RenameGroupRequest renameGroupRequestMock = mock(RenameGroupRequest.class);
        PowerMockito.whenNew(RenameGroupRequest.class).withAnyArguments().thenReturn(renameGroupRequestMock);
        Intent intentMock = mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withParameterTypes(Context.class, Class.class).
                withArguments(any(Context.class), any(Request.class)).thenReturn(intentMock);
        doReturn(intentMock).when(intentMock).putExtra(anyString(), any(Request.class));
        doReturn("asdf1234").when(groupSpy).getDeviceId(any(Activity.class));
        String newGroupName = "GroupNewName";
        groupSpy.changeGroupName(mainActivityMock, newGroupName);

        verify(renameGroupRequestMock).setSenderDeviceId("asdf1234");
        verify(renameGroupRequestMock).setNewName("GroupNewName");
        verify(renameGroupRequestMock).setTargetGroupName("Group");
        verify(groupSpy,atLeast(1)).getDeviceId(any(Activity.class));
    }

    @Test
    public void testGetGroupName() {
        GroupClient group = new GroupClient("Group");

        Assert.assertEquals("Group", group.getGroupName());
    }

    @Test
    public void testGetGoServiceWhenDeactivated() {
        GroupClient group = new GroupClient("Group");
        GoService goServiceExpected = new GoService(group);

        Assert.assertEquals(goServiceExpected.getGoStatus(), group.getGoService().getGoStatus());
    }

    @Test
    public void testGetGoServiceWhenActivated() {
        GeoPoint geoPoint = new GeoPoint(1.4545, 40.2345);
        GroupClient group = new GroupClient("Group", true, "14.03.2017", "16:15", "Infobau", geoPoint);
        GoService goServiceExpected = new GoService(group);
        goServiceExpected.activateGoStatus();

        Assert.assertEquals(goServiceExpected.getGoStatus(), group.getGoService().getGoStatus());
    }

    @Test
    public void testGetAppointment() {
        GeoPoint geoPoint = new GeoPoint(1.4545, 40.2345);
        GroupClient group = new GroupClient("Group", "14.03.2017", "16:30", "Infobau", geoPoint);
        double delta = 0.0001; //maximum deviation of the values

        Assert.assertEquals("Group", group.getGroupName());
        Assert.assertEquals("14.03.2017", group.getAppointment().getAppointmentDate().getDate());
        Assert.assertEquals("16:30", group.getAppointment().getAppointmentDate().getTime());
        Assert.assertEquals("Infobau", group.getAppointment().getAppointmentDestination().
                getDestinationName());
        Assert.assertEquals(geoPoint.getLatitude(), group.getAppointment().getAppointmentDestination().
                getDestinationPosition().getLatitude(), delta);
        Assert.assertEquals(geoPoint.getLongitude(), group.getAppointment().getAppointmentDestination().
                getDestinationPosition().getLongitude(), delta);
    }

    @Test (expected = NullPointerException.class)
    public void getDeviceId() {
        GroupClient groupClientSpy = new GroupClient("Group");
        groupClientSpy.getDeviceId(mainActivityMock);
    }

}