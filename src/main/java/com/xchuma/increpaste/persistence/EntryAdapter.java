package com.xchuma.increpaste.persistence;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by jmchuma on 8/21/15.
 */
public class EntryAdapter extends CursorAdapter {

    private ArrayList<String> _u_texts = new ArrayList<String>();
    private ArrayList<Long> _u_ids = new ArrayList<Long>();

    public EntryAdapter(Context context, Cursor cursor, int flags){
        super(context, cursor, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COL_ID));

        String text = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ENTRY_TEXT));
        ((TextView) view.findViewById(android.R.id.text1)).setText(text);

        Time t = new Time();
        t.set(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ENTRY_DATE)));
        ((TextView) view.findViewById(android.R.id.text2)).setText(t.format("%Y-%m-%d %H:%M"));

        boolean inText = _u_texts.contains(text);
        boolean inId = _u_ids.contains(id);

        view.setBackgroundColor(Color.WHITE);

        if(!inId) {
            if (inText)
                view.setBackgroundColor(Color.RED);
            else {
                _u_texts.add(text);
                _u_ids.add(id);
            }
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
    }
}
