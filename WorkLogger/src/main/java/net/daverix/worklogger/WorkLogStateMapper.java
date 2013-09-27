package net.daverix.worklogger;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by daverix on 9/27/13.
 */
public interface WorkLogStateMapper {
    WorkLogState mapCursor(Cursor cursor);

    ContentValues mapValues(WorkLogState state);
}
