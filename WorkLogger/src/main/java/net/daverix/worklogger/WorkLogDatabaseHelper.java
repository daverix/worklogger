package net.daverix.worklogger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static net.daverix.worklogger.lib.WorkLogContract.Places;
import static net.daverix.worklogger.lib.WorkLogContract.WorkLogStates;

/**
 * Created by daverix on 9/20/13.
 */
public class WorkLogDatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String DB_NAME = "WorkLogDB";
    public static final String TABLE_NAME_WORK_LOG_STATE = "workLogState";
    public static final String TABLE_NAME_PLACE = "place";

    private static final String SQL_WORK_LOG_STATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_WORK_LOG_STATE +
            " (" + WorkLogStates._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            WorkLogStates.SOURCE + " TEXT NOT NULL," +
            WorkLogStates.STATE + " INTEGER NOT NULL," +
            WorkLogStates.TIME + " INTEGER NOT NULL," +
            WorkLogStates.PLACE_ID + " INTEGER NOT NULL)";

    private static final String SQL_PLACE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PLACE +
            " (" + Places._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            Places.CREATED + " INTEGER NOT NULL," +
            Places.UPDATED + " INTEGER NOT NULL," +
            Places.IS_WORK + " INTEGER NOT NULL," +
            Places.NAME + " TEXT NOT NULL)";

    public WorkLogDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_PLACE);
        db.execSQL(SQL_WORK_LOG_STATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // when version changes this will be called. Add logic here later for upgrading database.

        if(oldVersion == 1 && newVersion == 2) {
            db.execSQL("DROP TABLE wifiNetwork");
            db.execSQL("ALTER TABLE " + TABLE_NAME_WORK_LOG_STATE + " ADD COLUMN place_id INTEGER NOT NULL DEFAULT -1");
            db.execSQL("ALTER TABLE " + TABLE_NAME_WORK_LOG_STATE + " ADD COLUMN source TEXT NOT NULL DEFAULT 'wifi'");
        }

        onCreate(db);
    }
}
