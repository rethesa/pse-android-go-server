package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.osmdroid.util.GeoPoint;

import edu.kit.pse.bdhkw.client.communication.CreateLinkRequest;
import edu.kit.pse.bdhkw.client.communication.KickMemberRequest;
import edu.kit.pse.bdhkw.client.communication.MakeAdminRequest;
import edu.kit.pse.bdhkw.client.communication.ObjectResponse;
import edu.kit.pse.bdhkw.client.communication.RenameGroupRequest;
import edu.kit.pse.bdhkw.client.communication.Request;
import edu.kit.pse.bdhkw.client.communication.UpdateRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.GoIntentService;

import java.util.List;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.REQUEST_TAG;
import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.RESPONSE_TAG;

/**
 * This class defines a group on the client.
 * @author Theresa Heine
 * @version 1.0
 */
public class GroupClient {

    private String groupName;
    private GoService goService;
    private Appointment appointment;

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
     */
    public GroupClient(String name, String date, String time, String destination, GeoPoint geoPoint) {
        this.groupName = name;
        this.goService = new GoService(this);
        this.appointment = new Appointment(date, time, destination, geoPoint);
    }

    /**
     * Create a group object with the information of the group table.
     * @param name of the group
     * @param goStatus of the group
     * @param date of the appointment
     * @param time of the appointment
     * @param destination of the appointment
     * @param geoPoint of the appointment
     */
    public GroupClient(String name, boolean goStatus, String date, String time, String destination, GeoPoint geoPoint) {
        this.groupName = name;
        this.goService = new GoService(this);
        if (goStatus) {
            goService.activateGoStatus();
        }
        this.appointment = new Appointment(date, time, destination, geoPoint);
    }

    /**
     * Group admin creates an invite link for this group. Start request and receive response in fragment.
     * The person who clicks on the link will be added to the group hidden as secret in the link.
     * @param activity of the group where create link button was clicked
     * @return link to send
     */
    public void createInviteLink(Activity activity) {
        String deviceId = readDeviceId(activity);
        CreateLinkRequest createLinkRequest = new CreateLinkRequest();
        createLinkRequest.setSenderDeviceId(deviceId);
        createLinkRequest.setTargetGroupName(this.getGroupName());
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, createLinkRequest);
        activity.startService(intent);
    }

    protected String readDeviceId(Activity activity) {
        String deviceId = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                   Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    /**
     * If anything about the group has been changed external like the group name or the appointment,
     * this method calls a request to get the actual information of the group.
     * @param activity where group update is called
     */
    public void getGroupUpdate(Activity activity) {
        String deviceId = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setSenderDeviceId(deviceId);
        updateRequest.setTargetGroupName(this.getGroupName());
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, updateRequest);
        activity.startService(intent);

        //TODO ist das nicht eher ein update, also wenn der Gruppe neue Mitglieder hinzugefügt wurden --> wird trotzdem hinzugefügt
        /**
         * GroupMemberClient groupMemberClient = new GroupMemberClient(name, userID);
         * userService.insertUserData(groupName, groupMemberClient);
        */
    }

    /**
     * Send a request to server to make a group member to an admin. When successful, the android
     * database will be updated with the new information. This will happen in the fragment where
     * this method is called.
     * @param activity of the group where admin makes memeber to admin
     * @param groupMember to be updated
     */
    public void makeGroupMemberToAdmin(Activity activity,GroupMemberClient groupMember) {
        String deviceId = null; //TODO get the deviceId or with SimpleUser.getDeviceId()
        MakeAdminRequest makeAdminRequest = new MakeAdminRequest();
        makeAdminRequest.setSenderDeviceId(deviceId);
        makeAdminRequest.setTargetGroupName(this.getGroupName());
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, makeAdminRequest);
        activity.startService(intent);
    }

    /**
     * Get all the names of the group members of one group.
     * Compare the user id's which are listed in allocation.db for this group with the ones in user.db
     * and add all names to the lists that are in both.
     * @return names of all users which are in the given group
     */
    public List<String> getAllGroupMemberNames(Activity activity) {
        userService = new UserService(activity);
        List<String> memberList = userService.readAllGroupMembers(this.getGroupName());
        return memberList;
    }

    /**
     * GroupClient admin deletes one of his group members. When deletion was successful on the server
     * deletion will be done on the android database as well.
     * @param user of the user to delete
     */
    public void deleteGroupMember(Activity activity, UserDecoratorClient user) {
        userService = new UserService(activity.getApplicationContext());
        String deviceId = null; //TODO
        KickMemberRequest kickMemberRequest = new KickMemberRequest();
        kickMemberRequest.setSenderDeviceId(deviceId);
        kickMemberRequest.setTargetGroupName(this.getGroupName());
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, kickMemberRequest);
        activity.startService(intent);

        //userService = new UserService(activity.getApplicationContext());
        //userService.deleteUserFromGroup(this.getGroupName(), user);
            }

    /**
     * User leaves a group by himself. After he was deleted from the group on the server, the group
     * and all its members will also be deleted from the android database.
     * @param user
     */
    public void leaveGroup(Activity activity, UserDecoratorClient user) {
        //TODO server aktualisieren
        //TODO: Intent goIntentService starten mit intent.putExtra("key", String groupname) --> Key im GoIntentService anpassen
        //TODO datenbank aktualisieren
        groupService.deleteOneGroupRow(this.getGroupName());
        deleteGroupMember(activity, user);
    }

    /**
     * Find out what kind of user (GroupAdminClient or GroupMemberClient) the actual user is, so he gets the
     * right view of the group. The GroupAdminClient has more functionality than a GroupMemberClient and because
     * of that the GroupAdminClient gets a different view.
     * Return true for admin and false for simpleMember
     * @param userId
     * @return the type of the actual user in this group.
     */
    public boolean getMemberType(Activity activity, int userId) {
        userService = new UserService(activity.getApplicationContext());
        int value = userService.readAdminOrMemberStatus(this.getGroupName(), userId);
        if (value == 1) {
            return  true;
        } else {
            return false;
        }
    }

    /**
     * Activate the go button of the current groupClient of the actual user.
     * Then the positions of all group members will be send periodically.
     */
    public void activateGoService(Activity activity) {
        goService.activateGoStatus();//sets goService to true
        //TODO server aktualisieren
        //TODO datenbank aktualisieren
        groupService = new GroupService(activity.getApplicationContext());
        groupService.updateGroupData(this.getGroupName(), this);
    }

    /**
     * Deactivate the go button of the current groupClient of the actual user.
     * This normally happens after the appointment is over.
     */
    public void deactivateGoService(Activity activity) {
        goService.deactivateGoStatus();//sets goService to false
        //TODO server aktualisieren
        //TODO datenbank aktualisieren
        groupService = new GroupService(activity.getApplicationContext());
        groupService.updateGroupData(this.getGroupName(), this);

    }

    /**
     * Change the name of the group to a different unique one.
     * @param oldGroupName of the groupClient
     */
    public void changeGroupName(Activity activity, String oldGroupName) {
        String deviceId = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.i(GroupClient.class.getSimpleName(), deviceId);

        RenameGroupRequest renameGroupRequest = new RenameGroupRequest();
        renameGroupRequest.setSenderDeviceId(deviceId);
        renameGroupRequest.setTargetGroupName(this.getGroupName());
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, renameGroupRequest);
        activity.startService(intent);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i(GroupClient.class.getSimpleName(), "in receiver");
                ObjectResponse obs = intent.getParcelableExtra(RESPONSE_TAG);
                boolean suc = obs.getSuccess();
                Log.i(GroupClient.class.getSimpleName(), String.valueOf(suc));
            }
        };

        //TODO server aktualisieren

        //TODO datenbank aktualisieren
        groupName = oldGroupName;
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

}