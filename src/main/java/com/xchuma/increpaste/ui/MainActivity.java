package com.xchuma.increpaste.ui;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

                Toast.makeText(this, R.string.toast_entry_added, Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }

            finish();
        } else {
            Log.i(TAG, i.getAction());
        }

        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new AllEntriesFragment());
        transaction.addToBackStack(null);
        transaction.commit();
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
        } else if (id == R.id.action_text_view) {
            AllEntriesFragment allEntriesFragment = new AllEntriesFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, allEntriesFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.action_list_view) {
            // Create new fragment and transaction
            EntryListFragment entryListFragment = new EntryListFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, entryListFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        return super.onOptionsItemSelected(item);
    }
}
