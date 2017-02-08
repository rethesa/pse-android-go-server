package edu.kit.pse.bdhkw.client.controller.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import edu.kit.pse.bdhkw.client.model.database.DBHelperAllocation;

import java.util.List;

/**
 * Created by Theresa on 11.01.2017.
 */

public class ServiceAllocation {

    private final DBHelperAllocation dbHelperAllocation;
    private SQLiteDatabase db;

    public ServiceAllocation(Context context) {
        dbHelperAllocation = new DBHelperAllocation(context.getApplicationContext());
    }

    /**
     * Add another user to the group.
     * @param userID
     * @param groupID
     */
    public boolean insertNewGroupMemberAlloc(int groupID, int userID) {
        return false;
    }

    /**
     * Get all user Id's which are in the same group.
     * @param groupID
     * @return list of the user id's
     */
    public List<Integer> readAllUserIdsOfOneGroup(int groupID) {
        return null;
    }

    /**
     * Delete connection betwenn an group and its member.
     * @param groupid the group in which to delte the member
     * @param userId meber to delete in group
     * @return true if deletion was successful
     */
    public boolean deleteGroupMemberAlloc(int groupid, int userId) {
        return false;
    }

    /**
     * Deletesa all allocations of group id and usesr id. So the group is not in allocation.db
     * anymore.
     * @param groupId
     * @return true if deletion was successful
     */
    public boolean deleteAllGroupMemberAlloc(int groupId) {
        return false;
    }

    /**
     * Make another group member to admin
     * @param groupId
     * @param userID of the user to become admin
     */
    public boolean updateGroupMemberToAdmin(int groupId, int userID) { //PUBLIC ODER PROTECTED????
        return false;
    }

    public boolean readUserType(int groupID, int userId) {
        return false;
    }

    public void deleteAllAllocations() {
    }
}
