package com.xchuma.increpaste.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry data source
 */
public class EntryDS {

    /**
     * For logging.
     */
    private final String TAG = Entry.class.getName();
    private DBHelper _dHelper;
    private SQLiteDatabase _db;

    public EntryDS(Context context) {
        _dHelper = new DBHelper(context);
    }

    public void open() throws SQLException {
        _db = _dHelper.getWritableDatabase();
    }

    public void close() {
        _dHelper.close();
    }

    public long create(String text) {
        ContentValues values = new ContentValues();
        values.put(_dHelper.ENTRY_TEXT, text);

        Log.d(TAG, "Adding "+values);
        long ans = _db.insert(DBHelper.TABLE_ENTRIES, null, values);
        if(ans > 0)
            Log.d(TAG, "Added with id "+ans);
        else
            Log.e(TAG, "Error adding "+values);

        return ans;
    }

    public void delete() {

    }

    /**
     *
     * @return a list with all the entries in the database.
     */
    public List<Entry> read() {
        List<Entry> list = new ArrayList<Entry>();
        Cursor cursor = _db.query(DBHelper.TABLE_ENTRIES,
                new String[]{DBHelper.COL_ID, DBHelper.ENTRY_POS, DBHelper.ENTRY_DATE, DBHelper.ENTRY_TEXT},
                null, null, null, null, DBHelper.ENTRY_POS+" ASC");

        if(cursor.moveToFirst())
            do {
                Entry e = new Entry();//toEvent(cursor);
                e.setId(cursor.getLong(0));
                e.setPos(cursor.getInt(1));
                e.setDate(cursor.getLong(2));
                e.setText(cursor.getString(3));
                list.add(e);
            } while(cursor.moveToNext());

        cursor.close();
        return list;
    }

    public void update() {

    }
}
