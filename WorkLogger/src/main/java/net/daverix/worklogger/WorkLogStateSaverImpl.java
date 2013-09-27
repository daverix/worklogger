package net.daverix.worklogger;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * Created by daverix on 9/19/13.
 */
public class WorkLogStateSaverImpl implements WorkLogStateSaver {
    private final Context mContext;
    private final WorkLogStateMapper mMapper;

    public WorkLogStateSaverImpl(Context context, WorkLogStateMapper mapper) {
        if(context == null)
            throw new IllegalArgumentException("context");

        if(mapper == null)
            throw new IllegalArgumentException("mapper");

        mMapper = mapper;
        mContext = context;
    }

    @Override
    public Uri saveState(WorkLogState state) {
        ContentValues values = mMapper.mapValues(state);

        return mContext.getContentResolver().insert(WorkLogContract.WorkLogStates.CONTENT_URI, values);
    }
}
