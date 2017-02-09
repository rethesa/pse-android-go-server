package edu.kit.pse.bdhkw.client.model.objectStructure;


import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.controller.database.UserService;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by Theresa on 20.12.2016.
 */

public class GroupClient {

    private String groupName;
    private GoStatus goStatus;
    private Appointment appointment;

    private List<UserDecoratorClient> groupMemberList;

    private GroupService sGroup;
    private UserService sUser;


    /**
     * Constructor of group. When creating a group it gets the given unique name that was checked
     * on the server, it gets a 9 digit id startig with 1 and an appointment and a goStatus for the
     * actual user.
     *
     * @param name
     */
    public GroupClient(String name) {
        this.groupName = name;
        this.goStatus = new GoStatus(this);
        this.appointment = new Appointment();
        this.groupMemberList = new LinkedList<>(); // MUSS ICH MIR NOCHMAL GEDANKEN DRÜBER MACHEN
    }

    public GroupClient(String name, String date, String time, String destination, List<UserDecoratorClient> memberList) {
        this.groupName = name;
        this.goStatus = new GoStatus(this);
        this.appointment = new Appointment(date, time, destination);
        this.groupMemberList = memberList;
    }

    /**
     * Admin can create a Link and send it with an extern messenger to the person he wants to add to
     * the groupClient.
     */
    public Link createInviteLink() {
        //server creates link and it's just saved there
        //BRAUCH ICH DAS ÜBERHAUPT? ODER WIRD DAS NICHT DIREKT VON DER VIEW AUS AUFGERUFEN?
        //SONST HAB ICH HIER NUR EIN REQUEST UND RESPONSE; FERTIG
        //TODO Link request response hier???
        Link link = null;
        return link;
    }

    /**
     * Adds a new groupClient member to the given groupClient.
     * @param name of user to be added
     * @param userID of user to be added
     */
    public void addGroupMember(String name, int userID) {
        GroupMemberClient groupMemberClient = new GroupMemberClient(name, userID);
        sUser.insertUserData(this.getGroupName(), groupMemberClient);
    }

    /**
     * Admin can upgrade a groupClient member to an admin.
     * @param groupMember to become new admin of the groupClient
     */
    public void makeGroupMemberToAdmin(GroupMemberClient groupMember) {
        GroupAdminClient groupAdminClient = new GroupAdminClient(groupMember.getUserName(), groupMember.getUserID());
        sUser.updateGroupMemberToAdmin(this.getGroupName(), groupAdminClient);
    }

    /**
     * Get all the names of the group members of one group.
     * Compare the user id's which are listed in allocation.db for this group with the ones in user.db
     * and add all names to the lists that are in both.
     * @return names of all users which are in the given group
     */
    public List<String> getAllGroupMemberNames() {
        List<String> memberList = sUser.readAllGroupMembers(this.getGroupName());
        return memberList;
    }

    /**
     * GroupClient admin deletes one of his groupClient members. After that it has to be checked, if the deleted
     * user is still in any other groupClient with the actual user. If not also delete this user from the
     * user.db
     * @param user of the user to delete
     */
    public void deleteGroupMember(UserDecoratorClient user) {
        sUser.deleteUserFromGroup(this.getGroupName(), user);

    }

    /**
     *
     * User leaves groupClient by himself. The group will be deleted from group.db and allocation.db
     * and also the appointment from appointment.db. All users which are in no other group with the actual
     * user will be deleted.
     * @param user
     */
    public void leaveGroup(UserDecoratorClient user) {
        sGroup.deleteOneGroupRow(this.getGroupName());
        deleteGroupMember(user);
    }

    /**
     * Activate the go button of the current groupClient of the actual user.
     */
    public void activateGoService() {
        goStatus.activateGoStatus();//sets goStatus to true
        sGroup.updateGroupData(this); //updates go service in database
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
        boolean userType = sUser.readAdminOrMemberStatus(this.getGroupName(), userId);

        if(userType == true) {
            GroupAdminClient gac = null;
            return gac.getClass().getSimpleName();
        } else {
            GroupMemberClient gmc = null;
            return gmc.getClass().getSimpleName();
        }
    }

}