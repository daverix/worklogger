package net.daverix.worklogger;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import net.daverix.worklogger.lib.WorkLogContract;

import static net.daverix.worklogger.lib.WorkLogContract.PlaceLogs;
import static net.daverix.worklogger.lib.WorkLogContract.Places;
import static net.daverix.worklogger.lib.WorkLogContract.WorkLogStates;

/**
 * Created by daverix on 9/20/13.
 */
public class WorkLogProvider extends ContentProvider {
    private SQLiteOpenHelper mHelper;
    private static final int MATCH_WORK_LOG_STATES = 1;
    private static final int MATCH_WORK_LOG_STATE = 2;
    private static final int MATCH_PLACES = 3;
    private static final int MATCH_PLACE = 4;
    private static final int MATCH_PLACE_LOGS = 5;
    private static final int MATCH_PLACE_LOG = 6;

    private static UriMatcher sUriMatcher = new UriMatcher(0);

    static {
        sUriMatcher.addURI(WorkLogContract.AUTHORITY, WorkLogStates.PATH, MATCH_WORK_LOG_STATES);
        sUriMatcher.addURI(WorkLogContract.AUTHORITY, WorkLogStates.PATH + "/#", MATCH_WORK_LOG_STATE);
        sUriMatcher.addURI(WorkLogContract.AUTHORITY, Places.PATH, MATCH_PLACES);
        sUriMatcher.addURI(WorkLogContract.AUTHORITY, Places.PATH + "/#", MATCH_PLACE);
        sUriMatcher.addURI(WorkLogContract.AUTHORITY, PlaceLogs.PATH, MATCH_PLACE_LOGS);
        sUriMatcher.addURI(WorkLogContract.AUTHORITY, PlaceLogs.PATH + "/#", MATCH_PLACE_LOG);
    }

    @Override
    public boolean onCreate() {
        mHelper = new WorkLogDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        if(db != null && db.isOpen()) {
            final int match = sUriMatcher.match(uri);
            switch (match) {
                case MATCH_WORK_LOG_STATES:
                    Cursor cursor = db.query(false, WorkLogDatabaseHelper.TABLE_NAME_WORK_LOG_STATE, projection, selection, selectionArgs, null, null, sortOrder, null);
                    if(cursor != null) {
                        cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    }
                    return cursor;
                case MATCH_WORK_LOG_STATE:
                    cursor = db.query(false, WorkLogDatabaseHelper.TABLE_NAME_WORK_LOG_STATE, projection, WorkLogStates._ID + "=?",
                            new String[]{uri.getLastPathSegment()}, null, null, sortOrder, null);
                    if(cursor != null) {
                        cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    }
                    return cursor;
            }
        }

        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        if(db != null && db.isOpen()) {
            final int match = sUriMatcher.match(uri);
            switch (match) {
                case MATCH_WORK_LOG_STATES:
                    long id = db.insert(WorkLogDatabaseHelper.TABLE_NAME_WORK_LOG_STATE, null, values);
                    if(id > 0) {
                        getContext().getContentResolver().notifyChange(WorkLogStates.CONTENT_URI, null);
                        return Uri.withAppendedPath(WorkLogStates.CONTENT_URI, String.valueOf(id));
                    }
                    break;
            }
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
