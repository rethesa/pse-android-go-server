package edu.kit.pse.bdhkw.client.controller.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import edu.kit.pse.bdhkw.client.model.database.DBHelperUser;
import edu.kit.pse.bdhkw.client.model.database.FeedReaderContract;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserComponent;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Theresa on 11.01.2017.
 *  * user hinzufügen
 * user löschen
 * user updaten
 * user lesen
 */

public class UserService {

    private final DBHelperUser dbHelperUser;
    private SQLiteDatabase db;

    private static AtomicInteger next_id = new AtomicInteger(0);

    public UserService(Context context) {
        dbHelperUser = new DBHelperUser(context.getApplicationContext());

    }

    /**
     * Add a new user to the database (it just happens when when adding a new user to a group, and
     * also then not always, just if the user doesn't exist already).
     * @param user object
     * @return true if insertion was successful
     */
    public void insertUserData(String groupName, UserDecoratorClient user) {
        db = dbHelperUser.getWritableDatabase();
        int id = next_id.incrementAndGet();
        try {
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntryUser.COL_ALLOC_ID, id);
            values.put(FeedReaderContract.FeedEntryUser.COL_GROUP_NAME, groupName);
            values.put(FeedReaderContract.FeedEntryUser.COL_USER_ID, user.getUserID());
            values.put(FeedReaderContract.FeedEntryUser.COL_USER_NAME, user.getUserName());
            values.put(FeedReaderContract.FeedEntryUser.COL_GROUP_ADMIN, user.isAdmin());

            long newRow = db.insert(FeedReaderContract.FeedEntryGroup.TABLE_NAME, null, values);
        } finally {
            db.close();
        }

    }

    /**
     * Get name, or the latitude and longitude of the user.
     * @param userID of the user to get information about
     * @return user object
     */
    public UserComponent readUserData(int userID) {
        //TODO
        return null;
    }

    /**
     * Get all User objects of all groups the actual user is member of.
     * @return
     */
    public List<UserComponent> readAllUsers() {
        //TODO
        db = dbHelperUser.getReadableDatabase();
        List<UserComponent> res = null;
        return res;
    }

    /**
     * Delete user in user.db
     * @param userID of the user to delete
     * @return true if deletion was successful
     */
    public boolean deleteUser(int userID) {
        return false;
    }

    /**
     * Update inforamtion about user.
     * @param userID of the user to update
     * @return true if update was successful
     */
    public boolean updateData(int userID) {
        return false;
    }

    public void deleteAllUsers() {
    }


    public void updateGroupMemberToAdmin(String groupName, GroupAdminClient groupAdmin) {
    }



    public List<String> readAllGroupMembers(String groupName) {
        return null;
    }

    public void deleteUserFromGroup(String groupName, UserDecoratorClient user) {
    }


    public boolean readAdminData(String groupName, int userId) {
        return false;
    }

    public void deleteGroupAllocation(String groupName) {

    }
}
