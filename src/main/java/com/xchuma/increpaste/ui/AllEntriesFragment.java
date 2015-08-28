package com.xchuma.increpaste.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xchuma.increpaste.R;
import com.xchuma.increpaste.persistence.Entry;
import com.xchuma.increpaste.persistence.EntryDS;

import java.sql.SQLException;
import java.util.ArrayList;

public class AllEntriesFragment extends Fragment implements View.OnClickListener {

    private final String TAG = AllEntriesFragment.class.getName();


    @Override /* Fragment */
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        Button button = (Button) getView().findViewById(R.id.button_editEntry);
        button.setOnClickListener(this);
    }

    @Override /* View.OnClickListener */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_editEntry:
                ((MainActivity) getActivity()).replaceFragment(new EditEntryFragment());
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_all, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_entries, container, false);
    }

    @Override
    public void onResume(){
        super.onResume();

        Activity activity = getActivity();
        int pos = 0;

        try {
            EntryDS ds = new EntryDS(activity);
            ds.open();

            StringBuilder strBuilder = new StringBuilder();
            for(Entry entry : ds.read()) {
                strBuilder.append(entry.getText()+"\n");
                if(entry.getPos() > pos) pos = entry.getPos();
            }
            TextView textView = (TextView) getView().findViewById(R.id.textView_entries);
            textView.setText(strBuilder);
            textView.setMovementMethod(new ScrollingMovementMethod());

            ds.close();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }

        SharedPreferences entry_extra = activity.getSharedPreferences("ENTRY_EXTRA", 0);
        entry_extra.edit().putInt("LAST_POST", pos).apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_del_dupes) {

            EntryDS ds = new EntryDS(getActivity());
            try {
                ds.open();
                ArrayList<String> unique = new ArrayList<>();

                for(Entry entry : ds.read()) {
                    if(unique.contains(entry.getText())) {
                        ds.delete(entry);
                    } else
                        unique.add(entry.getText());
                }

                ds.close();
            } catch (SQLException e){
                Log.e(TAG, e.getMessage());
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
