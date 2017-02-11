package edu.kit.pse.bdhkw.client.controller.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.kit.pse.bdhkw.client.model.database.DBHelperGroup;
import edu.kit.pse.bdhkw.client.model.database.FeedReaderContract;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Theresa on 11.01.2017.
 *
 * Gruppe hinzufügen
 * Gruppe löschen
 * Gruppe aktualisieren
 * alle informationen zu einer Gruppe abrufen
 *
 */

public class GroupService {

    private final DBHelperGroup dbHelperGroup;
    private SQLiteDatabase db;

    public GroupService(Context context) {
        dbHelperGroup = new DBHelperGroup(context.getApplicationContext());
    }

    /**
     * Add a new groupClient to groupClient.db database
     * @param groupClient to add to database
     * @return return true if inserting was successful
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
     * Delete all entries of the table.
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
     * Delete a group in group.db.
     * @param groupName of the group to delete
     * @return true if deletion was successful
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
     * Get name and go service of the group with the given group id.
     * @param groupName of the group to get information about
     * @return group object
     */
    public Cursor readOneGroupRow(String groupName) {
        db = dbHelperGroup.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Specify which columns of the database one will actually use after this query
            String[] projection = {
                    FeedReaderContract.FeedEntryGroup.COL_GROUP_NAME,
                    FeedReaderContract.FeedEntryGroup.COL_GO_STATUS,
                    FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_DATE,
                    FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_TIME,
                    FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_DEST,
                    FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_LATITUDE,
                    FeedReaderContract.FeedEntryGroup.COL_APPOINTMENT_LONGITUDE,
            };
            // Filter results wehre the name of the group = "groupName"
            String selection = FeedReaderContract.FeedEntryGroup.COL_GROUP_NAME + " = ?";
            String[] selectionArgs = { groupName };
            cursor = db.query(
                    FeedReaderContract.FeedEntryGroup.TABLE_NAME,   // The table to query
                    //null,
                    projection,                                     // The columns to return
                    selection,                                      // The columns for the WHERE clause
                    selectionArgs,                                  // The values for the WHERE clause
                    null,                                           // don't group the rows
                    null,                                           // don't filter by row groups
                    null                                      // The sort order
            );
            cursor.moveToFirst();
            return cursor;
        } finally {
            if (cursor != null) {
                //cursor.close();
            }
            db.close();
        }
    }

    /**
     * To get all groups the actual user is member of and because all the groups saved in the database
     * are just the ones he is member of, we can go through the list and return all group names
     * @return list of all names that are listed in the group database.
     */
    public List<String> readAllGroupNames() {
        db = dbHelperGroup.getReadableDatabase();
        Cursor cursor = null;
        try {
            // Only use group name after this query.
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
     * Update data when groupClient name or go service of the groupClient have changed.
     * @param groupClient to update name or go service
     * @return true if update was successful
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

            db.update(FeedReaderContract.FeedEntryGroup.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        } finally {
            db.close();
        }

    }


}
