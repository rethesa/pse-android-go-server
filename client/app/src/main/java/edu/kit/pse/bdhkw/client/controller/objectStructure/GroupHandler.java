package edu.kit.pse.bdhkw.client.controller.objectStructure;

import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupMemberClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.SimpleUser;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;

import java.util.List;

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
     * @param groupName unique name of the group to create or to become member of
     */
    public void createGroup(String groupName) {
        GroupAdminClient groupAdminClient = new GroupAdminClient(simpleUser.getUserName(), simpleUser.getUserID());
        GroupClient groupClient = new GroupClient(groupName);
        //add group to database and user as first member and group admin
        sGroup.insertNewGroup(groupClient);
        sUser.insertUserData(groupClient.getGroupName(), groupAdminClient);
    }

    public void joinGroup(String groupName, List<UserDecoratorClient> memberList, String appDate, String appTime, String appDest) {
        GroupClient groupClient = new GroupClient(groupName, appDate, appTime, appDest, memberList);
        //add group to database and user as first member and group admin
        sGroup.insertNewGroup(groupClient);
        GroupMemberClient groupMemberClient = new GroupMemberClient(simpleUser.getUserName(), simpleUser.getUserID());
        sUser.insertUserData(groupClient.getGroupName(), groupMemberClient);
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
