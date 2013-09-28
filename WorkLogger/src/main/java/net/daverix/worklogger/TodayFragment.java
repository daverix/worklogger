package net.daverix.worklogger;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.daverix.worklogger.WorkLogContract.WorkLogStates;

/**
 * Created by daverix on 9/28/13.
 */
public class TodayFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private TimeHandler mTimeHandler;
    private WorkLogStateMapper mWorkLogStateMapper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTimeHandler = new TimeHandlerImpl();
        mWorkLogStateMapper = new WorkLogStateMapperImpl();
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
                        WorkLogStates.BSSID,
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
                states.add(mWorkLogStateMapper.mapCursor(cursor));
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

                double totalSeconds = (double)(state.getTime() - startState.getTime()) / 1000;
                double totalMinutes = totalSeconds / 60;
                int hours = (int) (totalMinutes / 60);
                int minutes = (int) (totalMinutes - hours * 60);
                double seconds = totalSeconds - minutes * 60;
                float twoDecimalSeconds = (float) ((int)(seconds * 100)) / 100;

                sessions.add(time + ": " + hours + "h " + minutes + "m " + twoDecimalSeconds + "s on " + startState.getBssid());
            }
        }

        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, sessions));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
