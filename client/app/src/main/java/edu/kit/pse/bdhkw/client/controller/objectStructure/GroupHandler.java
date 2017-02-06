package edu.kit.pse.bdhkw.client.controller.objectStructure;

import edu.kit.pse.bdhkw.client.controller.database.ServiceAllocation;
import edu.kit.pse.bdhkw.client.controller.database.ServiceAppointment;
import edu.kit.pse.bdhkw.client.controller.database.ServiceGroup;
import edu.kit.pse.bdhkw.client.controller.database.ServiceUser;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;
import edu.kit.pse.bdhkw.common.model.SimpleUser;
import edu.kit.pse.bdhkw.common.model.UserComponent;

import java.util.List;

/**
 * Created by Theresa on 21.12.2016.
 */

public class GroupHandler {

    private ServiceAllocation sAlloc;
    private ServiceAppointment sApp;
    private ServiceGroup sGroup;
    private ServiceUser sUser;

    private SimpleUser simpleUser = null;

    private void updateAllGroups() {
    }

    /**
     * Create an new Group or become member of an existing group.
     * Add Group and it's corresponding appointment to the database. If user is the one who creates
     * the group, add him as group admin. If user is joining an existing group, add him to user.db
     * and after that as group member.
     * @param groupName unique name of the group to create or to become member of
     * @param user who creates the group or just joins the group
     */
    public void createGroup(String groupName, UserComponent user) {
        GroupClient groupClient = new GroupClient(groupName);
        //add group to database and user as first member and group admin
        sGroup.insertNewGroup(groupClient);
        sApp.insertAppointment(groupClient.getGroupID(), groupClient.getAppointment());
        //add user as admin if he created the group or add him as member if he didn't
        if (user.getID() == simpleUser.getID()) {
        sAlloc.insertNewGroupMemberAlloc(groupClient.getGroupID(), user.getID());
            sAlloc.updateGroupMemberToAdmin(groupClient.getGroupID(), user.getID());
        } else {
            addUserToUserDbIfNotListedYet(user);
            sAlloc.insertNewGroupMemberAlloc(groupClient.getGroupID(), user.getID());
    }
    }

    /**
     * Delete groupClient from groupClient.db, delete corresponding appointment, delete groupClient
     * and member from allocation.db and delete users who aren't in any other group with the actual
     * user anymore from the user.db
     * @param groupClient group to delete
     */
    public void deleteGroup(GroupClient groupClient){
        deleteUserFromUserDb(groupClient);
        sGroup.deleteGroupData(groupClient.getGroupID());
        sApp.deleteAppointmentData(groupClient.getGroupID());
        sAlloc.deleteAllGroupMemberAlloc(groupClient.getGroupID());
    }

    /**
     * Check if user is already listed in user.db and add user if he isn't listed yet.
     * @param user to check if he is in user.db
     */
    private void addUserToUserDbIfNotListedYet(UserComponent user) {
        List<UserComponent> userList =  sUser.readAllUsers();
        int count = 0;
        for (UserComponent userComponent : userList) {
            if(userComponent.getID() == user.getID()){
                count++;
}
        }
        if (count == 0) {
            sUser.insertUserData(user);
        }
    }

    /**
     * Delete user from user.db if they are in no other group with the actual user.
     * For each group member of the group to delete, go through all groups and check if they are in
     * any other group with the actual user. If not delete them, else keep them.
     * @param groupClient group to delete.
     */
    private void deleteUserFromUserDb(GroupClient groupClient) {
        List<Integer> thisGroupMemberIdList = sAlloc.readAllUserIdsOfOneGroup(groupClient.getGroupID());
        List<Integer> groupList = sGroup.readAllGroupIds();

        for(Integer memberId: thisGroupMemberIdList) {
            int count = 0;
            for(Integer groupId: groupList) {
                List<Integer> allMemberIdList = sAlloc.readAllUserIdsOfOneGroup(groupId);
                for (Integer memId: allMemberIdList){
                    if (memberId == memId){
                        count++;
                    }
                }
            }
            if (count == 1) {
                sUser.deleteUser(memberId);
            }
        }
    }
}
