package net.daverix.worklogger;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;

import net.daverix.worklogger.lib.WorkLogState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.daverix.worklogger.lib.WorkLogContract.WorkLogStates;

/**
 * Created by daverix on 9/28/13.
 */
public class TodayFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private TimeHandler mTimeHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTimeHandler = new TimeHandlerImpl();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(),
                WorkLogStates.CONTENT_URI,
                new String[] {
                        WorkLogStates._ID,
                        WorkLogStates.TIME,
                        WorkLogStates.SOURCE,
                        WorkLogStates.STATE
                },
                WorkLogStates.TIME + " >= ? AND " + WorkLogStates.TIME + " < ?",
                new String[]{
                        String.valueOf(mTimeHandler.getStartTimeToday()),
                        String.valueOf(mTimeHandler.getEndTimeToday())
                },
                WorkLogStates.TIME);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<WorkLogState> states = new ArrayList<WorkLogState>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                states.add(new WorkLogState(cursor));
            } while(cursor.moveToNext());
        }

        List<String> sessions = new ArrayList<String>();
        WorkLogState startState = null;
        for(WorkLogState state : states) {
            if(state.getState() == WorkLogState.STATE_START) {
                startState = state;
            }
            else if(state.getState() == WorkLogState.STATE_END && startState != null) {
                String time = DateFormat.getTimeFormat(getActivity()).format(new Date(startState.getTime()));
                String date = DateFormat.getDateFormat(getActivity()).format(new Date(startState.getTime()));

                double hours = (double)(state.getTime() - startState.getTime()) / 1000 / 60 / 60;
                float twoDecimalHours = (int)(hours * 100) / 100f;

                sessions.add(twoDecimalHours + "h on " + startState.getSource() + "\n" + date + " " + time);
            }
        }

        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sessions));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
