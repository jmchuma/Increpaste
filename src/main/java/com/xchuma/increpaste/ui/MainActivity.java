package com.xchuma.increpaste.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.xchuma.increpaste.R;
import com.xchuma.increpaste.persistence.EntryDS;

import java.sql.SQLException;


public class MainActivity extends ActionBarActivity
        implements AllEntriesFragment.OnNewEntryListener {

    private final String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        if(i.getAction().equals(Intent.ACTION_SEND)) {
            EntryDS ds = new EntryDS(this);
            try {
                ds.open();
                ds.create(i.getStringExtra(Intent.EXTRA_TEXT));
                ds.close();
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }

            finish();
        } else {
            Log.i(TAG, i.getAction());
        }

        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override /* AllEntriesFragment.OnNewEntryListener */
    public void onNewEntry() {
        Intent i = new Intent().setClass(this, EditEntryActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
