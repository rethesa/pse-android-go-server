package edu.kit.pse.bdhkw.client.controller.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.kit.pse.bdhkw.client.model.database.DBHelperUser;
import edu.kit.pse.bdhkw.client.model.database.FeedReaderContract;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserComponent;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Insert, read, delete and update of user.db.
 * @author Theresa Heine
 * @version 1.0
 */
public class UserService {

    private final DBHelperUser dbHelperUser;
    private SQLiteDatabase db;

    // Increasing integer for first column (PRIMARY KEY).
    private static AtomicInteger next_id = new AtomicInteger(0);

    /**
     * Constructur that creates the database if it doesen't exist yet.
     * @param context of the activity.
     */
    public UserService(Context context) {
        dbHelperUser = new DBHelperUser(context.getApplicationContext());
    }

    /**
     * Add actual user and all his groups and their members to user.db.
     * @param groupName of the group to add a member
     * @param user to add to the group
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

            db.insert(FeedReaderContract.FeedEntryUser.TABLE_NAME, null, values);
        } finally {
            db.close();
        }
    }

    /**
     * Read all names of the members of one group.
     * @param groupName of the group to get the members from.
     * @return list of Strings with member names.
     */
    public List<String> readAllGroupMembers(String groupName) {
        db = dbHelperUser.getReadableDatabase();
        Cursor cursor = null;
        try {
            String selection = FeedReaderContract.FeedEntryUser.COL_GROUP_NAME + " = ?";
            String[] selectionArgs = { groupName };
            cursor = db.query(
                    FeedReaderContract.FeedEntryUser.TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            List<String> memberNames = new LinkedList<>();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryUser.COL_USER_NAME));
                memberNames.add(name);
            }
            return memberNames;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    /**
     * Read if one user of a group is admin or just group member.
     * @param groupName of the group to get the member status.
     * @param userId of the member to get status in that group.
     * @return 0 for member and 1 for admin and -1 if userId is not listed.
     */
    public int readAdminOrMemberStatus(String groupName, int userId) {
        db = dbHelperUser.getReadableDatabase();
        Cursor cursor = null;
        try {
            String selection = FeedReaderContract.FeedEntryUser.COL_GROUP_NAME + " = ?";
            String[] selectionArgs = { groupName };
            cursor = db.query(
                    FeedReaderContract.FeedEntryUser.TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            int adminValue;
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntryUser.COL_USER_ID));
                if (id == userId) {
                    adminValue = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntryUser.COL_GROUP_ADMIN));
                    return adminValue;
                }
            }
            return -1;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    /**
     * Delete all groups and members of the table.
     */
    public void deleteAllUserAndGroups() {
        db = dbHelperUser.getWritableDatabase();
        try {
            db.delete(FeedReaderContract.FeedEntryUser.TABLE_NAME, null, null);
        } finally {
            db.close();
        }
    }

    /**
     * Delete one member of one group.
     * @param groupName of the group to delete the memeber
     * @param user to be deleted
     */
    public void deleteUserFromGroup(String groupName, UserDecoratorClient user) {
        db = dbHelperUser.getWritableDatabase();
        Cursor cursor = null;
        try {
            String selection = FeedReaderContract.FeedEntryUser.COL_GROUP_NAME + " LIKE ?";
            String[] selectionArgs = { groupName };
            cursor = db.query(FeedReaderContract.FeedEntryUser.TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntryUser.COL_USER_ID));
                if (id == user.getUserID()) {
                    int allocId = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntryUser.COL_ALLOC_ID));
                    String deleteSelection = FeedReaderContract.FeedEntryUser.COL_ALLOC_ID + " LIKE ?";
                    String[] deleteSelectionArgs = { allocId + ""};
                    db.delete(FeedReaderContract.FeedEntryUser.TABLE_NAME, deleteSelection, deleteSelectionArgs);
                    break;
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    /**
     * Delete all group members of one group.
     * @param groupName to delete from the database
     */
    public void deleteAllGroupMembers(String groupName) {
        db = dbHelperUser.getWritableDatabase();
        try {
            String selection = FeedReaderContract.FeedEntryUser.COL_GROUP_NAME + " LIKE ?";
            String[] selectionArgs = { groupName };
            db.delete(FeedReaderContract.FeedEntryUser.TABLE_NAME, selection, selectionArgs);
        } finally {
            db.close();
        }
    }

    /**
     * Update when group name has changed.
     * @param oldGroupName of the group
     * @param newGroupName of the group
     */
    public void updateGroupNameInAlloc(String oldGroupName,String newGroupName) {
        db = dbHelperUser.getWritableDatabase();
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntryUser.COL_GROUP_NAME, newGroupName);
            String selection = FeedReaderContract.FeedEntryUser.COL_GROUP_NAME + " LIKE ?";
            String[] selectionArgs = { oldGroupName };
            cursor = db.query(FeedReaderContract.FeedEntryUser.TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            while (cursor.moveToNext()) {
                db.update(FeedReaderContract.FeedEntryUser.TABLE_NAME, values, selection, selectionArgs);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    /**
     * Update the user name of any user in all groups.
     * @param user who changed his name
     */
    public void updateUserName(UserComponent user) {
        db = dbHelperUser.getWritableDatabase();
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntryUser.COL_USER_NAME, user.getUserName());
            String selection = FeedReaderContract.FeedEntryUser.COL_USER_ID + " LIKE ?";
            String[] selectionArgs = { user.getUserID() + "" };
            cursor = db.query(FeedReaderContract.FeedEntryUser.TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            while (cursor.moveToNext()) {
                db.update(FeedReaderContract.FeedEntryUser.TABLE_NAME, values, selection, selectionArgs);
            }
        } finally {
            if(cursor != null) {
                cursor.close();
            }
            db.close();
        }

    }

    /**
     * Update one group member to admin of this group.
     * @param groupName in which user will become admin
     * @param user to make to admin
     */
    public void updateGroupMemberToAdmin(String groupName, UserDecoratorClient user) {
        db = dbHelperUser.getWritableDatabase();
        Cursor cursor = null;
        try {
            GroupAdminClient newUser = new GroupAdminClient(user.getUserName(), user.getUserID());
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntryUser.COL_GROUP_ADMIN, newUser.isAdmin());
            String selection = FeedReaderContract.FeedEntryUser.COL_GROUP_NAME + " LIKE ?";
            String[] selectionArgs = { groupName };
            cursor = db.query(FeedReaderContract.FeedEntryUser.TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntryUser.COL_USER_ID));
                if (id == newUser.getUserID()) {
                    int allocId = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntryUser.COL_ALLOC_ID));
                    String updateSelection = FeedReaderContract.FeedEntryUser.COL_ALLOC_ID + " LIKE ?";
                    String[] updateSelectionArgs = { allocId + ""};
                    db.update(FeedReaderContract.FeedEntryUser.TABLE_NAME, values, updateSelection, updateSelectionArgs);
                    break;
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

}
