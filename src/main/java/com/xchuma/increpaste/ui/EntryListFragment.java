package com.xchuma.increpaste.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.xchuma.increpaste.R;

import com.xchuma.increpaste.persistence.DBHelper;
import com.xchuma.increpaste.persistence.EntryAdapter;
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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EntryListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle saved) {
        super.onActivityCreated(saved);

        final ListView listView =  getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                listView.setItemChecked(pos, true);
                return true;
            }
        });

        listView.setMultiChoiceModeListener(new ListView.MultiChoiceModeListener() {
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                final int count = listView.getCheckedItemCount();
                final String message = (count == 1) ? "One item " : count + " items ";

                switch (item.getItemId()) {
                    case R.id.action_copy:
                        SparseBooleanArray positions = listView.getCheckedItemPositions();
                        StringBuilder text = new StringBuilder();

                        Cursor cursor;
                        for(int i = 0; i < positions.size(); ++i) {
                            cursor = (Cursor) listView.getItemAtPosition(positions.keyAt(i));
                            text.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ENTRY_TEXT))+"\n");
                        }

                        ClipboardManager clipboard = (ClipboardManager)
                                getActivity().getSystemService(Activity.CLIPBOARD_SERVICE);
                        clipboard.setPrimaryClip(ClipData.newPlainText(null, text));

                        Toast.makeText(getActivity(), message + " copied to clipboard", Toast.LENGTH_SHORT).show();

                        mode.finish();
                        break;

                    case R.id.action_delete:
                        Toast.makeText(getActivity(), message + " deleted", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        break;

                    default:
                        Toast.makeText(getActivity(), "Clicked " + item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                getActivity().getMenuInflater().inflate(
                        R.menu.menu_entry_list_fragment_context, menu);
                mode.setSubtitle("One item selected");
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int pos,
                                                  long id, boolean checked) {
                final int count = listView.getCheckedItemCount();
                switch (count) {
                    case 0:
                        mode.setSubtitle(null);
                        break;
                    case 1:
                        mode.setSubtitle("One item selected");
                        break;
                    default:
                        mode.setSubtitle("" + count + " items selected");
                        break;
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        Activity activity = getActivity();
        try {
            EntryDS ds = new EntryDS(activity);
            ds.open();
            setListAdapter(new EntryAdapter(activity, ds.getCursor(), 0));

            //ds.close(); --> with CursorAdapter this can't be done
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_fragment, menu);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ((MainActivity) getActivity()).replaceFragment(new EditEntryFragment());
    }

}
