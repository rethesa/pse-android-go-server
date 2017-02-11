package edu.kit.pse.bdhkw.client.controller.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.kit.pse.bdhkw.client.model.database.DBHelperGroup;
import edu.kit.pse.bdhkw.client.model.database.FeedReaderContract;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

import java.util.LinkedList;
import java.util.List;

/**
 * Insert, read, delete and update the entries of the group.db.
 * @author Theresa Heine
 * @version 1.0
 */
public class GroupService {

    private final DBHelperGroup dbHelperGroup;
    private SQLiteDatabase db;

    /**
     * Constructur that creates the database if it doesen't exist yet.
     * @param context of the activity.
     */
    public GroupService(Context context) {
        dbHelperGroup = new DBHelperGroup(context.getApplicationContext());
    }

    /**
     * Add a new group with coresponding appointment to group.db.
     * @param groupClient to add to database.
     */
    public void insertNewGroup(GroupClient groupClient) {
        db = dbHelperGroup.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntryGroup.COL_GROUP_NAME, groupClient.getGroupName());
            values.put(FeedReaderContract.FeedEntryGroup.COL_GO_STATUS, groupClient.getGoService()
                    .getGoStatus());
            values.put(FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_DATE, groupClient
                    .getAppointment().getAppointmentDate().getDate().toString());
            values.put(FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_TIME, groupClient
                    .getAppointment().getAppointmentDate().getTime().toString());
            values.put(FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_DEST, groupClient
                    .getAppointment().getAppointmentDestination().getDestinationName());
            values.put(FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_LATITUDE, groupClient
                    .getAppointment().getAppointmentDestination().getDestinationPosition().getLatitude());
            values.put(FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_LONGITUDE, groupClient
                    .getAppointment().getAppointmentDestination().getDestinationPosition().getLongitude());

            long newRow = db.insert(FeedReaderContract.FeedEntryGroup.TABLE_NAME, null, values);
        } finally {
            db.close();
        }
    }

    /**
     * Get one row of the group.db.
     * @param groupName of the group to get information about
     * @return cursor object
     */
    public Cursor readOneGroupRow(String groupName) {
        db = dbHelperGroup.getReadableDatabase();
        Cursor cursor = null;
        try {
            String selection = FeedReaderContract.FeedEntryGroup.COL_GROUP_NAME + " = ?";
            String[] selectionArgs = { groupName };
            cursor = db.query(
                    FeedReaderContract.FeedEntryGroup.TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            cursor.moveToFirst();
            return cursor;
        } finally {
            //don't close cursor. Can't access it when closed.
            db.close();
        }
    }

    /**
     * To get all group names the actual user is member of.
     * @return list of all names that are listed in the group.db
     */
    public List<String> readAllGroupNames() {
        db = dbHelperGroup.getReadableDatabase();
        Cursor cursor = null;
        try {
            String[] projection = {FeedReaderContract.FeedEntryGroup.COL_GROUP_NAME};
            cursor = db.query(
                    FeedReaderContract.FeedEntryGroup.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            List<String> groupNames = new LinkedList<>();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntryGroup.COL_GROUP_NAME));
                groupNames.add(name);
            }
            return groupNames;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    /**
     * Delete all groups of the table.
     */
    public void deleteAllGroups() {
        db = dbHelperGroup.getWritableDatabase();
        try {
            db.delete(FeedReaderContract.FeedEntryGroup.TABLE_NAME, null, null);
        } finally {
            db.close();
        }
    }

    /**
     * Delete one group listed in group.db.
     * @param groupName of the group to delete
     */
    public void deleteOneGroupRow(String groupName) {
        db = dbHelperGroup.getWritableDatabase();
        try {
            String selection = FeedReaderContract.FeedEntryGroup.COL_GROUP_NAME + " LIKE ?";
            String[] selectionArgs = { groupName };
            db.delete(FeedReaderContract.FeedEntryGroup.TABLE_NAME, selection, selectionArgs);
        } finally {
            db.close();
        }
    }

    /**
     * Update data when name, appointment or go service have changed.
     * @param oldGroupName of the group to identify the row
     * @param groupClient to update
     */
    public void updateGroupData(String oldGroupName, GroupClient groupClient) {
        db = dbHelperGroup.getReadableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntryGroup.COL_GROUP_NAME, groupClient.getGroupName());
            values.put(FeedReaderContract.FeedEntryGroup.COL_GO_STATUS, (groupClient.getGoService().
                    getGoStatus()));
            values.put(FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_DATE, groupClient
                    .getAppointment().getAppointmentDate().getDate().toString());
            values.put(FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_TIME, groupClient
                    .getAppointment().getAppointmentDate().getTime().toString());
            values.put(FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_DEST, groupClient
                    .getAppointment().getAppointmentDestination().getDestinationName());
            values.put(FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_LATITUDE, groupClient
                    .getAppointment().getAppointmentDestination().getDestinationPosition().getLatitude());
            values.put(FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_LONGITUDE, groupClient
                    .getAppointment().getAppointmentDestination().getDestinationPosition().getLongitude());

            String selection = FeedReaderContract.FeedEntryGroup.COL_GROUP_NAME + " LIKE ?";
            String[] selectionArgs = { oldGroupName };
            db.update(FeedReaderContract.FeedEntryGroup.TABLE_NAME, values, selection, selectionArgs);
        } finally {
            db.close();
        }
    }

}
