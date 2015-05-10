/**
 *
 */
package com.xchuma.increpaste.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Stores things in the database
 */
public class DBHelper extends SQLiteOpenHelper {

    /**
     * For logging.
     */
    private final String TAG = DBHelper.class.getName();

    /**
     * Database schema version. Incremented when the database structure is changed.
     */
    private static final int VERSION = 0;

    /**
     * Name of database file.
     */
    private static final String NAME = "entries.db";

    /**
     * Name of the ID column for all tables.
     */
    public static final String COL_ID = "_id";

    /**
     * Table for the estries. Holds each line of the paste bin.
     */
    public static final String TABLE_ENTRIES = "entries";
    /**
     * Date of creation.
     */
    public static final String ENTRY_DATE = "date";
    /**
     * Position of the enty.
     */
    public static final String ENTRY_POS = "pos";
    /**
     * Content of the entry
     */
    public static final String ENTRY_TEXT = "text";

    public DBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_ENTRIES+"("
            + COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ENTRY_POS +" INTEGER NOT NULL, "
            + ENTRY_DATE +" DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + ENTRY_TEXT +" TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldV, final int newV) {
        Log.w(TAG,
                "Upgrading DB from version " + oldV + " to " + newV + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
        onCreate(db);
    }
}
