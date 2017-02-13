package edu.kit.pse.bdhkw.client.model.objectStructure;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.communication.CreateLinkRequest;
import edu.kit.pse.bdhkw.client.communication.KickMemberRequest;
import edu.kit.pse.bdhkw.client.communication.MakeAdminRequest;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.UpdateRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;

import java.util.LinkedList;
import java.util.List;

/**
 * This class defines a group on the client.
 * @author Theresa Heine
 * @version 1.0
 */
public class GroupClient {

    private String groupName;
    private GoService goService;
    private Appointment appointment;

    private List<UserDecoratorClient> groupMemberList;
    private Link link;
    private boolean success;

    private GroupService groupService;
    private UserService userService;

    /**
     * Creating a non existing group and choose a unique group name.
     * The goService is for the actual user for that group.
     * @param name of the group
     */
    public GroupClient(String name) {
        this.groupName = name;
        this.goService = new GoService(this);
        this.appointment = new Appointment();
    }

    /**
     * Constructor to create a group, that already has a name, an appointment and members.
     * @param name of the group
     * @param date of the appointment
     * @param time of the appointment
     * @param destination of the appointment
     * @param memberList all the members that are in that group
     */
    public GroupClient(String name, String date, String time, String destination, List<UserDecoratorClient> memberList) {
        this.groupName = name;
        this.goService = new GoService(this);
        this.appointment = new Appointment(date, time, destination);
        this.groupMemberList = memberList;
    }

    /**
     * Group admin creates an invite link for this group.
     * The person who clicks on the link will be added to the group hidden as secret in the link.
     * @param activity of the group where create link button was clicked
     * @return link to send
     */
    public Link createInviteLink(final Activity activity) {
        final Link[] link = null;
        String deviceId = null; //TODO get the deviceId or with SimpleUser.getDeviceId()

        CreateLinkRequest createLinkRequest = new CreateLinkRequest();
        createLinkRequest.setSenderDeviceId(deviceId);
        createLinkRequest.setTargetGroupName(this.getGroupName());
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra("req", createLinkRequest);
        activity.startService(intent);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ObjectResponse obs = intent.getParcelableExtra("res");
                link[0] = (Link) obs.getObject("link"); //TODO überprüfen ob das so klappt
            }
        };
        return link[0];
    }

    /**
     * If anything about the group has been changed external like the group name or the appointment,
     * this method gets the actual data from the server.
     * @param activity wher group update is called
     */
    public void getGroupUpdate(Activity activity) {
        groupService = new GroupService(activity.getApplicationContext());
        userService = new UserService(activity.getApplicationContext());
        String deviceId = null; //TODO

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setSenderDeviceId(deviceId);
        updateRequest.setTargetGroupName(this.getGroupName());
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra("req", updateRequest);
        activity.startService(intent);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ObjectResponse objectResponse = intent.getParcelableExtra("res");
                String name = (String) objectResponse.getObject("group_name");
                Appointment appointment = (Appointment) objectResponse.getObject("appointment_object");
                LinkedList<String> memberList = (LinkedList<String>) objectResponse.getObject("member_list");

            }
        };

        //TODO ist das nicht eher ein update, also wenn der Gruppe neue Mitglieder hinzugefügt wurden --> wird trotzdem hinzugefügt

        /**
         * GroupMemberClient groupMemberClient = new GroupMemberClient(name, userID);
        userService.insertUserData(groupName, groupMemberClient);
        */
    }

    /**
     * Admin can upgrade a groupClient member to another admin of the group.
     * @param activity of the group where admin makes memeber to admin
     * @param groupMember to be updated
     */
    public void makeGroupMemberToAdmin(Activity activity,GroupMemberClient groupMember) {
        userService = new UserService(activity.getApplicationContext());
        MakeAdminRequest makeAdminRequest = new MakeAdminRequest();
        String deviceId = null; //TODO get the deviceId or with SimpleUser.getDeviceId()
        makeAdminRequest.setSenderDeviceId(deviceId);
        makeAdminRequest.setTargetGroupName(this.getGroupName());
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra("req", makeAdminRequest);
        activity.startService(intent);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ObjectResponse objectResponse = intent.getParcelableExtra("res");
                //TODO weiß nicht ob ich da überhaupt eine Rückmeldung bekomme
                boolean success = (boolean) objectResponse.getObject("success");
                setBool(success);
            }
        };
        if (getBool()) {
            GroupAdminClient groupAdminClient = new GroupAdminClient(groupMember.getUserName(), groupMember.getUserID());
            userService.updateGroupMemberToAdmin(this.getGroupName(), groupAdminClient);
            //Toast
        } else {
            Toast.makeText(activity, R.string.errorUpgradeToAdmin, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get all the names of the group members of one group.
     * Compare the user id's which are listed in allocation.db for this group with the ones in user.db
     * and add all names to the lists that are in both.
     * @return names of all users which are in the given group
     */
    public List<String> getAllGroupMemberNames(Activity activity) {
        //TODO davor noch ein request?? oder wird das nur zu langsam?

        List<String> memberList = userService.readAllGroupMembers(this.getGroupName());
        return memberList;
    }

    /**
     * GroupClient admin deletes one of his groupClient members. After that it has to be checked, if the deleted
     * user is still in any other groupClient with the actual user. If not also delete this user from the
     * user.db
     * @param user of the user to delete
     */
    public void deleteGroupMember(Activity activity, UserDecoratorClient user) {
        userService = new UserService(activity.getApplicationContext());
        KickMemberRequest kickMemberRequest = new KickMemberRequest();
        String deviceId = null; //TODO
        kickMemberRequest.setSenderDeviceId(deviceId);
        kickMemberRequest.setTargetGroupName(this.getGroupName());
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra("req", kickMemberRequest);
        activity.startService(intent);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


            }
        };

        userService = new UserService(activity.getApplicationContext());
        userService.deleteUserFromGroup(this.getGroupName(), user);
    }

    /**
     *
     * User leaves groupClient by himself. The group will be deleted from group.db and allocation.db
     * and also the appointment from appointment.db. All users which are in no other group with the actual
     * user will be deleted.
     * @param user
     */
    public void leaveGroup(Activity activity, UserDecoratorClient user) {



        groupService.deleteOneGroupRow(this.getGroupName());
        deleteGroupMember(activity, user);
    }

    /**
     * Activate the go button of the current groupClient of the actual user.
     */
    public void activateGoService() {
        goService.activateGoStatus();//sets goService to true
       // sGroup.updateGroupData(this); //updates go service in database
    }

    /**
     * Deactivate the go button of the current groupClient of the actual user.
     * This normally happens after the appointment is over.
     */
    public void deactivateGoService() {
        goService.deactivateGoStatus();//sets goService to false
        //sGroup.updateGroupData(this);//updates go service in database
    }

    /**
     * Change the name of the groupClient to a different unique one.
     * @param newGroupName of the groupClient
     */
    public void changeGroupName(String newGroupName) {
        groupName = newGroupName;
        //sGroup.updateGroupData(this);
    }

    /**
     * Get the name of the group.
     * @return group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Get the go service of the group of the actual user.
     * @return go service
     */
    public GoService getGoService() {
        return goService;
    }


    /**
     * Get the appointment (time, date, name or destination) of the group.
     * @return appointment information
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Find out what kind of user (GroupAdminClient or GroupMemberClient) the actual user is, so he gets the
     * right view of the group. The GroupAdminClient has more functionality than a GroupMemberClient and because
     * of that the GroupAdminClient gets a different view.
     * @param userId
     * @return the type of the actual user in this group.
     */
    public String getMemberType(int userId) {
       /* boolean userType = sUser.readAdminOrMemberStatus(this.getGroupName(), userId);

        if(userType == true) {
            GroupAdminClient gac = null;
            return gac.getClass().getSimpleName();
        } else {
            GroupMemberClient gmc = null;
            return gmc.getClass().getSimpleName();
        }*/
        return null;
        }

    
    
    
    
    private void setLink(Link link) {
        this.link = link;
    }

    private Link getLink() {
        return link;
    }

    
    
    private void setBool(boolean bool) {
        success = bool;
    }

    private boolean getBool() {
        return success;
    }

}