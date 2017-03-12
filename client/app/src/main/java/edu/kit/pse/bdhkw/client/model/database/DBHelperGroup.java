package edu.kit.pse.bdhkw.client.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * General functions of the group database.
 * @author Theresa Heine
 * @version 1.0
 */

public class DBHelperGroup extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "goapp.db";
    private SQLiteDatabase db;

    /**
     * Constructor to define general information of the database like version an name.
     * @param context of the Activity
     */
    public DBHelperGroup(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the database if it doesn't exist yet.
     * @param sqLiteDatabase to create
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(FeedReaderContract.FeedEntryGroup.SQL_CREATE_ENTRIES_GROUP);
            sqLiteDatabase.execSQL(FeedReaderContract.FeedEntryUser.SQL_CREATE_ENTRIES_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Upgrade database. Drop or add tables/ columns.
     * @param sqLiteDatabase to upgrade
     * @param oldVersion of database
     * @param newVersion of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(FeedReaderContract.FeedEntryGroup.SQL_DELETE_ENTRIES_GROUP);
        sqLiteDatabase.execSQL(FeedReaderContract.FeedEntryUser.SQL_DELETE_ENTRIES_USER);
        onCreate(sqLiteDatabase);
    }

}
