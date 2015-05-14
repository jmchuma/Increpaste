package com.xchuma.increpaste.ui;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xchuma.increpaste.R;

import com.xchuma.increpaste.persistence.Entry;
import com.xchuma.increpaste.persistence.EntryDS;

import java.sql.SQLException;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {}@link OnFragmentInteractionListener
 * interface.
 */
public class EntryListFragment extends ListFragment {

    private static final String TAG = EntryListFragment.class.getName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        Activity activity = getActivity();
        try {
            EntryDS ds = new EntryDS(activity);
            ds.open();
            setListAdapter(new ArrayAdapter<Entry>(activity,  android.R.layout.simple_list_item_1,
                    android.R.id.text1, ds.read()));

            ds.close();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_fragment, menu);
    }
}
