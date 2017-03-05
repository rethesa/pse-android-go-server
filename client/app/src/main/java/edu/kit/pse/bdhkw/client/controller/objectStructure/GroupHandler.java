package edu.kit.pse.bdhkw.client.controller.objectStructure;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

import org.osmdroid.util.GeoPoint;

import edu.kit.pse.bdhkw.client.communication.CreateGroupRequest;
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
 * Created by Theresa on 21.12.2016.
 */

public class GroupHandler {

    private GroupService sGroup;
    private UserService sUser;


    private SimpleUser simpleUser = null;

    private void updateAllGroups() {

    }

    /**
     * Create an new Group or become member of an existing group.
     * Add Group and it's corresponding appointment to the database. If user is the one who creates
     * the group, add him as group admin. If user is joining an existing group, add him to user.db
     * and after that as group member.
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
     *
     * @param activity
     * @param groupAndLink
     */
    public void joinGroup(Activity activity, String[] groupAndLink) {
        String deviceId = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        JoinGroupRequest joinGroupRequest = new JoinGroupRequest();
        joinGroupRequest.setSenderDeviceId(deviceId);
        joinGroupRequest.setLink(new Link("url", groupAndLink[0], groupAndLink[1]));
        Intent intent1 = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent1.putExtra(REQUEST_TAG, joinGroupRequest);
        activity.startService(intent1);

        //GroupClient groupClient = new GroupClient(groupName, appDate, appTime, appDest,geoPoint, memberList);
        //add group to database and user as first member and group admin
        //sGroup.insertNewGroup(groupClient);
        //GroupMemberClient groupMemberClient = new GroupMemberClient(simpleUser.getName(), simpleUser.getUserID());
        //sUser.insertUserData(groupClient.getGroupName(), groupMemberClient);
    }

    /**
     * Delete groupClient from groupClient.db, delete corresponding appointment, delete groupClient
     * and member from allocation.db and delete users who aren't in any other group with the actual
     * user anymore from the user.db
     * @param groupClient group to delete
     */
    public void deleteGroup(GroupClient groupClient){
        sGroup.deleteOneGroupRow(groupClient.getGroupName());
        sUser.deleteAllGroupMembers(groupClient.getGroupName());
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
