package edu.kit.pse.bdhkw.client.controller.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import edu.kit.pse.bdhkw.client.model.database.DBHelperUser;
import edu.kit.pse.bdhkw.client.model.database.FeedReaderContract;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupAdminClient;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserComponent;
import edu.kit.pse.bdhkw.client.model.objectStructure.UserDecoratorClient;

import java.util.ArrayList;
import java.util.LinkedList;
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

            long newRow = db.insert(FeedReaderContract.FeedEntryUser.TABLE_NAME, null, values);
        } finally {
            db.close();
        }
    }

    /**
     * Delete all entries of table.
     */
    public void deleteAllUserAndGroups() {
        db = dbHelperUser.getWritableDatabase();
        try {
            db.delete(FeedReaderContract.FeedEntryUser.TABLE_NAME, null, null);
        } finally {
            db.close();
        }
    }

    public List<String> readAllGroupMembers(String groupName) {
        db = dbHelperUser.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] projection = {
                    FeedReaderContract.FeedEntryUser.COL_GROUP_NAME,
                    FeedReaderContract.FeedEntryUser.COL_USER_NAME
            };
            String selection = FeedReaderContract.FeedEntryUser.COL_GROUP_NAME + " = ?";
            String[] selectionArgs = { groupName };

            cursor = db.query(
                    FeedReaderContract.FeedEntryUser.TABLE_NAME,
                    projection,
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

    public boolean readAdminOrMemberStatus(String groupName, int userId) {
        db = dbHelperUser.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] projection = {
                    FeedReaderContract.FeedEntryUser.COL_GROUP_NAME,
                    FeedReaderContract.FeedEntryUser.COL_USER_ID,
                    FeedReaderContract.FeedEntryUser.COL_GROUP_ADMIN
            };
            String selection = FeedReaderContract.FeedEntryUser.COL_GROUP_NAME + " = ?";
            String[] selectionArgs = { groupName };

            cursor = db.query(
                    FeedReaderContract.FeedEntryUser.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            String adminValue;
            boolean isAdmin = false;
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntryUser.COL_USER_ID));
                if (id == userId) {
                    adminValue = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryUser.COL_GROUP_ADMIN));
                    isAdmin = Boolean.parseBoolean(adminValue);
                    break;
                }
            }
            return isAdmin;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }


    public boolean updateDataOfOneRow(String groupName, String userName) {
        return false;
    }

    public void updateGroupMemberToAdmin(String groupName, GroupAdminClient groupAdmin) {
    }


    public void deleteUserFromGroup(String groupName, UserDecoratorClient user) {
    }

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
}
