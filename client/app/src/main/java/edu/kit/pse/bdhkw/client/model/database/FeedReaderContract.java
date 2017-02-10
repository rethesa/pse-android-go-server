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
     * saved on the client. Appointment and group will be listed in one table.
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
         * Go service of the actual user if it's go_button is pressed for this group or not.
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
     * groups the actual user is member of. Saves user and the groups he is a member of.
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
         * Group name the user is member of.
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
                        COL_GROUP_ADMIN + " TEXT)";

        /**
         * Delete table.
         */
        protected static final String SQL_DELETE_ENTRIES_USER =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
