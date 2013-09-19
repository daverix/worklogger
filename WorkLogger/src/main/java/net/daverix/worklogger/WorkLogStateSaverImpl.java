package net.daverix.worklogger;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * Created by daverix on 9/19/13.
 */
public class WorkLogStateSaverImpl implements WorkLogStateSaver {
    private final Context mContext;

    public WorkLogStateSaverImpl(Context context) {
        if(context == null)
            throw new IllegalArgumentException("context");

        mContext = context;
    }

    @Override
    public Uri saveState(WorkLogState state) {
        ContentValues values = createContentValues(state);

        //TODO: if state is END then look at the latest existing work log state with state START and take BSSID from that can not be more than 24 hours? Nobody works that long?

        return mContext.getContentResolver().insert(WorkLogContract.WorkLogStates.CONTENT_URI, values);
    }

    protected ContentValues createContentValues(WorkLogState state) {
        ContentValues values = new ContentValues();
        values.put(WorkLogContract.WorkLogStates.BSSID, state.getBssid());
        values.put(WorkLogContract.WorkLogStates.STATE, state.getState());
        values.put(WorkLogContract.WorkLogStates.TIME, state.getTime());

        return values;
    }
}
