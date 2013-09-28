package net.daverix.worklogger;

import android.content.ContentValues;
import android.database.Cursor;

import static net.daverix.worklogger.WorkLogContract.WorkLogStates;

/**
 * Created by daverix on 9/27/13.
 */
public class WorkLogStateMapperImpl implements WorkLogStateMapper {
    @Override
    public WorkLogState mapCursor(Cursor cursor) {
        if(cursor == null)
            throw new IllegalArgumentException("Cursor must not be null!");

        return new WorkLogState(cursor.getLong(cursor.getColumnIndex(WorkLogStates._ID)),
                cursor.getString(cursor.getColumnIndex(WorkLogStates.BSSID)),
                cursor.getLong(cursor.getColumnIndex(WorkLogStates.TIME)),
                cursor.getInt(cursor.getColumnIndex(WorkLogStates.STATE)));
    }

    @Override
    public ContentValues mapValues(WorkLogState state) {
        ContentValues values = new ContentValues();
        long id = state.getId();
        if(id > 0) {
            values.put(WorkLogStates._ID, id);
        }

        values.put(WorkLogStates.BSSID, state.getBssid());
        values.put(WorkLogStates.STATE, state.getState());
        values.put(WorkLogStates.TIME, state.getTime());

        return values;
    }
}
