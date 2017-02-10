package edu.kit.pse.bdhkw.client.model.objectStructure;

import android.app.Activity;
import android.content.Intent;

import edu.kit.pse.bdhkw.client.communication.GroupAdminRequest;
import edu.kit.pse.bdhkw.client.communication.GroupRequest;
import edu.kit.pse.bdhkw.client.communication.GroupResponse;
import edu.kit.pse.bdhkw.client.controller.NetworkIntentService;
import edu.kit.pse.bdhkw.client.controller.database.ServiceAllocation;
import edu.kit.pse.bdhkw.client.controller.database.ServiceAppointment;
import edu.kit.pse.bdhkw.client.controller.database.ServiceGroup;
import edu.kit.pse.bdhkw.client.controller.database.ServiceUser;
import edu.kit.pse.bdhkw.client.model.GoIntentService;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by Theresa on 20.12.2016.
 */

public class GroupClient {

    private int groupID;
    private String groupName;
    private GoStatus goStatus;
    private Appointment appointment;
    private LinkedList<GpsObject> gpsData;

    private ServiceGroup sGroup;
    private ServiceUser sUser;
    private ServiceAppointment sApp;
    private ServiceAllocation sAlloc;

    /**
     * Constructor of group. When creating a group it gets the given unique name that was checked
     * on the server, it gets a 9 digit id startig with 1 and an appointment and a goStatus for the
     * actual user.
     *
     * @param name
     */
    public GroupClient(String name, Activity activity) {
        this.groupName = name;
        this.groupID = genereateGroupId();
        this.goStatus = new GoStatus(this);
        appointment = new Appointment();
        sendGroupRequest(activity);
    }

    private void sendGroupRequest(Activity activity) {
        GroupRequest groupRequest = new GroupRequest(thisClient());
        groupRequest.setTargetGroup(this);
        groupRequest.setInviteLink(createInviteLink());
        Intent intent = new Intent(activity, NetworkIntentService.class);
        //TODO groupRequest im NetworkIntentService abfangen
        intent.putExtra("groupRequest", groupRequest);
        activity.startService(intent);
        //TODO --> get a Response
        //muss die Response wirklich eine Gruppe mitgeben? Die habe ich hier doch schon erstellt
        //alternativ muss man die ganze Anfrage von dem Fragment aus schicken, sonst macht das keinen Sinn
        GroupResponse groupResponse = new GroupResponse(true);
        if (!groupResponse.getSuccess()) {
            //this.destroy;
        }
    }

    /**
     * Admin can create a Link and send it with an extern messenger to the person he wants to add to
     * the groupClient.
     */
    public Link createInviteLink() {
        //server creates link and it's just saved there
        //TODO
        Link link = null;
        return link;
    }

    /**
     * Adds a new groupClient member to the given groupClient.
     * @param user to be added
     */
    public void addGroupMember(UserComponent user) {
        addUserToUserDbIfNotListedYet(user);
        sAlloc.insertNewGroupMemberAlloc(this.groupID, user.getUserID());
    }

    /**
     * Admin can upgrade a groupClient member to an admin.
     * @param user to become new admin of the groupClient (while the other one still exists)
     */
    public void makeGroupMemberToAdmin(UserComponent user, Activity activity) {
        sAlloc.updateGroupMemberToAdmin(this.getGroupID(), user.getUserID());
        GroupAdminRequest adminRequest = new GroupAdminRequest(thisClient());
        Intent intent = new Intent(activity, NetworkIntentService.class);
        //TODO adminRequest im NetworkIntentService abfangen
        intent.putExtra("adminRequest", adminRequest);
        activity.startService(intent);
        //TODO --> get a Response;
        //GroupAdminResponse
    }

    /**
     * Get all the names of the group members of one group.
     * Compare the user id's which are listed in allocation.db for this group with the ones in user.db
     * and add all names to the lists that are in both.
     * @return names of all users which are in the given group
     */
    public List<String> getAllGroupMemberNames() {
        List<String> groupMemberList = new LinkedList<>();
        List<Integer> userIdList = sAlloc.readAllUserIdsOfOneGroup(this.groupID);
        List<UserComponent> allUserList = sUser.readAllUsers();
        //compare the id's in the lists
        for(Integer userId: userIdList ) {
            for(UserComponent user: allUserList ) {
                if (userId == user.getUserID()) {
                    groupMemberList.add(user.getUserName());
                }
            }
        }
        return groupMemberList;
    }

    /**
     * GroupClient admin deletes one of his groupClient members. After that it has to be checked, if the deleted
     * user is still in any other groupClient with the actual user. If not also delete this user from the
     * user.db
     * @param user of the user to delete
     */
    public void deleteGroupMember(UserComponent user) {
        sAlloc.deleteGroupMemberAlloc(this.getGroupID(), user.getUserID());
        deleteUserFromUserDb();
    }

    /**
     *
     * User leaves groupClient by himself. The group will be deleted from group.db and allocation.db
     * and also the appointment from appointment.db. All users which are in no other group with the actual
     * user will be deleted.
     * @param user
     */
    public void leaveGroup(UserComponent user) {
        deleteUserFromUserDb();
        sGroup.deleteGroupData(this.getGroupID());
        sApp.deleteAppointmentData(this.getGroupID());
        sAlloc.deleteAllGroupMemberAlloc(this.getGroupID());
    }

    /**
     * Activate the go button of the current groupClient of the actual user.
     */
    public void activateGoService(Activity activity) {
        goStatus.activateGoStatus();//sets goStatus to true
        sGroup.updateGroupData(this); //updates go service in database
        Intent intent = new Intent(activity, GoIntentService.class);
        intent.putExtra("groupname", groupName);
        activity.startService(intent);
    }

    /**
     * Deactivate the go button of the current groupClient of the actual user.
     * This normally happens after the appointment is over.
     */
    public void deactivateGoService() {
        goStatus.deactivateGoStatus();//sets goStatus to false
        sGroup.updateGroupData(this);//updates go service in database
    }

    /**
     * Change the name of the groupClient to a different unique one.
     * @param newGroupName of the groupClient
     */
    public void changeGroupName(String newGroupName) {
        groupName = newGroupName;
        sGroup.updateGroupData(this);
    }

    /**
     * Get the group id of the group.
     * @return group id
     */
    public int getGroupID() {
        return groupID;
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
    public GoStatus getGoStatus() {
        return goStatus;
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
    public String getMember(int userId) {
        //find this id in allocation.db corresponding to this group and if admin == true --> return GroupAdminClient else GroupMemberClient
        boolean userType = sAlloc.readUserType(this.getGroupID(), userId);
        if(userType == true) {
            GroupAdminClient gac = null;
            return gac.getClass().getSimpleName();
        } else {
            GroupMemberClient gmc = null;
            return gmc.getClass().getSimpleName();
        }
    }

    /**
     * Gererate 9 digit number starting with 1 to identify the group on the client.
     * @return
     */
    private int genereateGroupId() {
        int number = (int) (Math.floor(Math.random() * 100_000_000) + 100_000_000);
        return number;
    }

    /**
     * Check if user is already listed in user.db and add user if he isn't listed yet.
     * @param user to check if he is in user.db
     */
    private void addUserToUserDbIfNotListedYet(UserComponent user) {
        List<UserComponent> userList =  sUser.readAllUsers();
        int count = 0;
        for (UserComponent userComponent : userList) {
            if(userComponent.getUserID() == user.getUserID()){
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
     */
    private void deleteUserFromUserDb() {
        List<Integer> thisGroupMemberIdList = sAlloc.readAllUserIdsOfOneGroup(this.getGroupID());
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

    private SimpleUser thisClient() {
        //TODO Theresa fragen, woher ich mich = SimpleUser-Object bekomme
        return new SimpleUser("hi", 0);
    }

    public void setGpsData(LinkedList<GpsObject> gpsData) {
        this.gpsData = gpsData;
    }


}