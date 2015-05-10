package com.xchuma.increpaste.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xchuma.increpaste.R;
import com.xchuma.increpaste.persistence.Entry;
import com.xchuma.increpaste.persistence.EntryDS;

import java.sql.SQLException;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private final String TAG = MainActivity.class.getName();

    @Override /* View.OnClickListener */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_editEntry:
                Intent i = new Intent().setClass(this, EditEntryActivity.class);
                startActivity(i);
                break;
        }
    }

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

        Button button = (Button) findViewById(R.id.button_editEntry);
        button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    public void onResume() {
        super.onResume();

        int pos = 0;

        try {
            EntryDS ds = new EntryDS(this);
            ds.open();

            StringBuilder strBuilder = new StringBuilder();
            for(Entry entry : ds.read()) {
                strBuilder.append(entry.getText()+"\n");
                if(entry.getPos() > pos) pos = entry.getPos();
            }
            TextView textView = (TextView) findViewById(R.id.textView_entries);
            textView.setText(strBuilder);

            ds.close();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        SharedPreferences entry_extra = getSharedPreferences("ENTRY_EXTRA", 0);
        entry_extra.edit().putInt("LAST_POST", pos).commit();
    }
}
