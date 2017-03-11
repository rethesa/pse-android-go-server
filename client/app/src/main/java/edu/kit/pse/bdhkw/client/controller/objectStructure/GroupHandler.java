package edu.kit.pse.bdhkw.client.controller.objectStructure;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

import edu.kit.pse.bdhkw.client.communication.CreateGroupRequest;
import edu.kit.pse.bdhkw.client.communication.JoinGroupRequest;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;

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
        String deviceId = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        CreateGroupRequest createGroupRequest = new CreateGroupRequest();
        createGroupRequest.setSenderDeviceId(deviceId);
        createGroupRequest.setNewGroupName(groupName);
        Intent intent = new Intent(activity.getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(REQUEST_TAG, createGroupRequest);
        activity.startService(intent);
    }

    //(String groupName, List<UserDecoratorClient> memberList, String appDate, String appTime, String appDest, GeoPoint geoPoint)
    public void joinGroup(Activity activity, String groupName) {
        String deviceId = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        JoinGroupRequest joinGroupRequest = new JoinGroupRequest();

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
    
}
