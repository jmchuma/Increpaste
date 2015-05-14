package com.xchuma.increpaste.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xchuma.increpaste.R;
import com.xchuma.increpaste.persistence.EntryDS;

import java.sql.SQLException;

public class EditEntryActivity extends ActionBarActivity implements View.OnClickListener {

    private final String TAG = EditEntryActivity.class.getName();

    @Override /* View.OnClickListener */
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_cancel:
                finish();
                break;
            case R.id.button_ok:
                EntryDS ds = new EntryDS(this);
                try {
                    ds.open();

                    EditText text = (EditText) findViewById(R.id.editText_entry);
                    ds.create(text.getText().toString());
                    ds.close();
                    Toast.makeText(this, R.string.toast_entry_added, Toast.LENGTH_SHORT).show();
                    finish();
                } catch (SQLException e) {
                    Log.d(TAG, e.getMessage());
                }

                break;
        }
    }

    @Override /* ActionBarActivity */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        /* TODO do this in onResume?*/
        Button button = (Button) findViewById(R.id.button_cancel);
        button.setOnClickListener(this);

        button = (Button) findViewById(R.id.button_ok);
        button.setOnClickListener(this);
    }


    @Override /* ActionBarActivity */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_entry, menu);
        return true;
    }

    @Override /* ActionBarActivity */
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
