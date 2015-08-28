package com.xchuma.increpaste.persistence;

import android.content.Context;
import android.database.Cursor;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * Created by xchuma on 8/21/15.
 */
public class EntryAdapter extends CursorAdapter {

    public EntryAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String text = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ENTRY_TEXT));
        ((TextView) view.findViewById(android.R.id.text1)).setText(text);

        Time t = new Time();
        t.set(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ENTRY_DATE)));
        ((TextView) view.findViewById(android.R.id.text2)).setText(t.format("%Y-%m-%d %H:%M"));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_activated_2, parent, false);
    }
}
