package edu.kit.pse.bdhkw.client.model.database;

import android.provider.BaseColumns;

/**
 * This class designs how all the tables of the client look like. The table name and the names of
 * the columns are given here.
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
         * Define the name of the table
         */
        public static final String TABLE_NAME = "groups_client";

        /**
         * Second column with the group name.
         */
        public static final String COL_GROUP_NAME = "group_name";
        /**
         * Go service of the actual user if it's go_button is pressed for this group or not.
         */
        public static final String COL_GO_STATUS = "group_go_service";
        /**
         * Second column with the date of the appointment.
         */
        public static final String COL_APPOINTMENT_DATE = "group_appointment_date";
        /**
         * Third column with the time of the appointment.
         */
        public static final String COL_APPOINTMENT_TIME = "group_appointment_time";
        /**
         * Fourth column with the destination of the appointment, where the group will meet.
         */
        public static final String COL_APPOINTMENT_DEST = "group_appointment_dest";
        /**
         * Fifth column with the latitude of the appointment to show on the map.
         */
        public static final String COL_APPOINTMENT_LATITUDE = "group_appointment_latitude";
        /**
         * Sixth column with the logitude of the appointment to show on the map.
         */
        public static final String COL_APPOINTMENT_LONGITUDE = "group_appointment_longitude";

        /**
         * Create table with the defined entries.
         */
        protected static final String SQL_CREATE_ENTRIES_GROUP =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL_GROUP_NAME + " TEXT PRIMARY KEY," +
                        COL_GO_STATUS + " TEXT, " + 
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
     * Inner class that defines the structure ot the table contents of all group members of all
     * groups the actual user is member of and how they will be saved on the client.
     */
    public static class FeedEntryUser implements BaseColumns {
        /**
         * Define the name of the table.
         */
        public static final String TABLE_NAME = "user_client";

        /**
         * First column is the user id (PRIMARY KEY).
         */
        public static final String COL1_USER_ID = "user_id";
        /**
         * Second column is the name of the user.
         */
        public static final String COL2_USER_NAME = "user_name";
        /**
         * Third column is the latitude of the users actual position.
         */
        public static final String COL3_USER_LATITUDE = "last_known_user_latitude";
        /**
         * Foruth column is the longitude of the users actual position.
         */
        public static final String COL4_LONGITUDE = "last_known_user_longitude";

        /**
         * Create table with the defined entries.
         */
        protected static final String SQL_CREATE_ENTRIES_USER =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL1_USER_ID + " INTEGER PRIMARY KEY," +
                        FeedEntryUser.COL2_USER_NAME + " TEXT," +
                        FeedEntryUser.COL3_USER_LATITUDE + " DOUBLE," +
                        FeedEntryUser.COL4_LONGITUDE + " DOUBLE)";
        /**
         * Delete table.
         */
        protected static final String SQL_DELETE_ENTRIES_USER =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }



    /**
     * Inner class that defines the structure of the table for the connection between groups and
     * users. All group members of each group the actual user is member of will be listed here.
     */
    public static class FeedEntryAllocation implements BaseColumns {
        /**
         * Define the name of the table.
         */
        public static final String TABLE_NAME = "allocation";

        /**
         * First column in the table is a simple increasing id (PRIMARY KEY).
         */
        public static final String COL1_ID = "allocation_id";
        /**
         * Second column in the table is the group id.
         */
        public static final String COL2_GR_ID = "group_id";
        /**
         * Third column in the table is the user id of all users
         * with the same group id.
         */
        public static final String COL3_US_ID = "user_id";
        /**
         * Foruth column to identify which of the users is/are admin of the group.
         */
        public static final String COL4_ADMIN = "admin";

        /**
         * Create table with the defined entries.
         */
        protected static final String SQL_CREATE_ENTRIES_ALLOCATION =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL1_ID + " INTEGER PRIMARY KEY," +
                        COL2_GR_ID + " INTEGER," +
                        COL3_US_ID + " INTEGER, " +
                        COL4_ADMIN + " STRING)";
        /**
         * Delete table if exists.
         */
        protected static final String SQL_DELETE_ENTRIES_ALLOCATION =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


    /**
     * Inner class that defines the structure of the table and how appointments will be saved on
     * the client with a link to the corresponding group.
     */
    public static class FeedEntryAppointment implements BaseColumns {
        /**
         * Define the name of the table.
         */
        public static final String TABLE_NAME = "appointment";

        /**
         * First column with the group id (PRIMARY KEY) to link the appointment to a group.
         */
        public static final String COL1_GROUP_ID = "group_id";
        /**
         * Second column with the date of the appointment.
         */
        public static final String COL2_APPOINTMENT_DATE = "group_appointment_date";
        /**
         * Third column with the time of the appointment.
         */
        public static final String COL3_APPOINTMENT_TIME = "group_appointment_time";
        /**
         * Fourth column with the destination of the appointment, where the group will meet.
         */
        public static final String COL4_APPOINTMENT_DEST = "group_appointment_dest";
        /**
         * Fifth column with the latitude of the appointment to show on the map.
         */
        public static final String COL5_APPOINTMENT_LATITUDE = "group_appointment_latitude";
        /**
         * Sixth column with the logitude of the appointment to show on the map.
         */
        public static final String COL6_APPOINTMENT_LONGITUDE = "group_appointment_longitude";

        /**
         * Create table.
         */
        protected static final String SQL_CREATE_ENTRIES_APPOINTMENT =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COL1_GROUP_ID + " INTEGER PRIMARY KEY," +
                        COL2_APPOINTMENT_DATE + " INTEGER," +
                        COL3_APPOINTMENT_TIME + " INTEGER, " +
                        COL4_APPOINTMENT_DEST + " TEXT, " +
                        COL5_APPOINTMENT_LATITUDE + " DOUBLE, " +
                        COL6_APPOINTMENT_LONGITUDE + " DOUBLE)";

        /**
         * Delete table if exists.
         */
        protected static final String SQL_DELETE_ENTRIES_APPOINTMENT =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
