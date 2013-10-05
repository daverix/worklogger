package net.daverix.worklogger;

import android.content.Context;
import android.net.Uri;

import net.daverix.worklogger.lib.WorkLogContract;
import net.daverix.worklogger.lib.WorkLogState;

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
        return mContext.getContentResolver().insert(WorkLogContract.WorkLogStates.CONTENT_URI, state.getValues());
    }
}
