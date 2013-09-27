package net.daverix.worklogger;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by daverix on 9/27/13.
 */
public class WorkLogStateMapperImpl implements WorkLogStateMapper {
    @Override
    public WorkLogState mapCursor(Cursor cursor) {
        if(cursor == null)
            throw new IllegalArgumentException("Cursor must not be null!");

        return new WorkLogState(cursor.getString(cursor.getColumnIndex(WorkLogContract.WorkLogStates.BSSID)),
                cursor.getLong(cursor.getColumnIndex(WorkLogContract.WorkLogStates.TIME)),
                cursor.getInt(cursor.getColumnIndex(WorkLogContract.WorkLogStates.STATE)));
    }

    @Override
    public ContentValues mapValues(WorkLogState state) {
        ContentValues values = new ContentValues();
        values.put(WorkLogContract.WorkLogStates.BSSID, state.getBssid());
        values.put(WorkLogContract.WorkLogStates.STATE, state.getState());
        values.put(WorkLogContract.WorkLogStates.TIME, state.getTime());

        return values;
    }
}
