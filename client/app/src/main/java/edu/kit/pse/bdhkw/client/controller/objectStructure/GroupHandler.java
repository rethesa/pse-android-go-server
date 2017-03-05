package edu.kit.pse.bdhkw.client.controller.objectStructure;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

import org.osmdroid.util.GeoPoint;

import edu.kit.pse.bdhkw.client.communication.CreateGroupRequest;
import edu.kit.pse.bdhkw.client.communication.DeleteGroupRequest;
import edu.kit.pse.bdhkw.client.communication.JoinGroupRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupMemberClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.Link;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;

import java.util.List;

import static edu.kit.pse.bdhkw.client.controller.NetworkIntentService.REQUEST_TAG;

/**
 * For creating, joining and deleting groups.
 * @author Theresa Heine
 * @version 1.0
 */

public class GroupHandler {

    /**
     * Create an new Group or become member of an existing group.
     * Add Group and it's corresponding appointment to the database. If user is the one who creates
     * the group, add him as group admin. If user is joining an existing group, add him to user.db
     * and after that as group member.
     * @param activity where method is called
     * @param groupName of the group to create
     */
    public void createGroup(Activity activity, String groupName) {
        String deviceId = getDeviceId(activity);
        CreateGroupRequest createGroupRequest = new CreateGroupRequest();
        createGroupRequest.setSenderDeviceId(deviceId);
        createGroupRequest.setNewGroupName(groupName);
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, createGroupRequest);
        activity.startService(intent);
    }

    /**
     * When a link to join a group will be opened with the app this method is called and sends a
     * request to the server  to add this user as group member.
     * @param activity where method is called
     * @param groupAndLink is the group name and secret of the group to join
     */
    public void joinGroup(Activity activity, String[] groupAndLink) {
        String deviceId = getDeviceId(activity);
        JoinGroupRequest joinGroupRequest = new JoinGroupRequest();
        joinGroupRequest.setSenderDeviceId(deviceId);
        joinGroupRequest.setLink(new Link("url", groupAndLink[0], groupAndLink[1]));
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, joinGroupRequest);
        activity.startService(intent);
    }

    /**
     * Delete group and all its group members from android database.
     * @param activity where method is called
     * @param groupName of group to delete
     */
    public void deleteGroup(Activity activity, String groupName){
        String deviceId = getDeviceId(activity);
        DeleteGroupRequest deleteGroupRequest = new DeleteGroupRequest();
        deleteGroupRequest.setSenderDeviceId(deviceId);
        deleteGroupRequest.setTargetGroupName(groupName);
        Intent intent1 = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent1.putExtra(REQUEST_TAG, deleteGroupRequest);
        activity.startService(intent1);
        /*
         * TODO in receiver of fragment or activity
         * sGroup.deleteOneGroupRow(groupClient.getGroupName());
         * sUser.deleteAllGroupMembers(groupClient.getGroupName());
         */
    }

    /**
     * Removed from method for easier testing.
     * @param activity of the group where coresponding mehtod is called.
     * @return value of device id
     */
    protected String getDeviceId(Activity activity) {
        String deviceId = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }
    
}
