package edu.kit.pse.bdhkw.client.model.database;

import android.provider.BaseColumns;

/**
 * The tables of the database are defined here.
 * @author Theresa Heine
 * @version 1.0
 */

public class FeedReaderContract {

    /**
     * To prevent someone from accidentally instantiating the contract class, make the constructor
     * private.
     */
    private FeedReaderContract() {
    }

    /**
     * Inner class that defines the structure of the table contents and how the groups will be
     * saved on the client.
     */
    public static class FeedEntryGroup implements BaseColumns {

        /**
         * Define the name of the table.
         */
        public static final String TABLE_NAME = "groups_client";

        /**
         * Primary key is group name.
         */
        public static final String COL_GROUP_NAME = "group_name";
        /**
         * Go service of the actual user if go_button is pressed for this group or not.
         */
        public static final String COL_GO_STATUS = "group_go_status";
        /**
         * Date of the appointment.
         */
        public static final String COL_APPOINTMENT_DATE = "group_appointment_date";
        /**
         * Time of the appointment.
         */
        public static final String COL_APPOINTMENT_TIME = "group_appointment_time";
        /**
         * Destination of the appointment, where the group will meet.
         */
        public static final String COL_APPOINTMENT_DEST = "group_appointment_dest";
        /**
         * Latitude of the appointment to show on the map.
         */
        public static final String COL_APPOINTMENT_LATITUDE = "group_appointment_latitude";
        /**
         * Longitude of the appointment to show on the map.
         */
        public static final String COL_APPOINTMENT_LONGITUDE = "group_appointment_longitude";

        /**
         * Create table with the defined entries.
         */
        protected static final String SQL_CREATE_ENTRIES_GROUP =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_GROUP_NAME + " TEXT PRIMARY KEY," +
                        COL_GO_STATUS + " INTEGER, " +
                        COL_APPOINTMENT_DATE + " INTEGER," +
                        COL_APPOINTMENT_TIME + " INTEGER, " +
                        COL_APPOINTMENT_DEST + " TEXT, " +
                        COL_APPOINTMENT_LATITUDE + " REAL, " +
                        COL_APPOINTMENT_LONGITUDE + " REAL)";

        /**
         * Delete table.
         */
        protected static final String SQL_DELETE_ENTRIES_GROUP =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * Inner class that defines the structure of the table that saves the users and in what groups
     * they are memeber of. All listed groups are just the ones the actual user is memeber of.
     */
    public static class FeedEntryUser implements BaseColumns {
        /**
         * Define the name of the table.
         */
        public static final String TABLE_NAME = "user_client";

        /**
         * Primary key increment (PRIMARY KEY).
         */
        public static final String COL_ALLOC_ID =  "alloc_id";
        /**
         * Group name of the group the user is member of.
         */
        public static final String COL_GROUP_NAME = "group_name";
        /**
         * User ID to identify each user.
         */
        public static final String COL_USER_ID = "user_id";
        /**
         * Corresponding name to unique user ID.
         */
        public static final String COL_USER_NAME = "user_name";
        /**
         * True if member is group amdin.
         */
        public static final String COL_GROUP_ADMIN = "group_admin";
        /**
         * Create table with the defined entries.
         */
        protected static final String SQL_CREATE_ENTRIES_USER =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_ALLOC_ID + " INTEGER PRIMARY KEY," +
                        COL_GROUP_NAME + " TEXT, " +
                        COL_USER_ID + " INTEGER, " +
                        COL_USER_NAME + " TEXT, " +
                        COL_GROUP_ADMIN + " INTEGER)";

        /**
         * Delete table.
         */
        protected static final String SQL_DELETE_ENTRIES_USER =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
    
}
