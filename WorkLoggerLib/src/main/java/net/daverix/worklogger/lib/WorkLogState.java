package net.daverix.worklogger.lib;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static net.daverix.worklogger.lib.WorkLogContract.WorkLogStates;

/**
 * Created by daverix on 9/19/13.
 */
public class WorkLogState implements Parcelable {
    public static final int STATE_START = 1;
    public static final int STATE_END = 2;

    private long mId;
    private String mSource;
    private long mTime;
    private int mState;
    private long mPlaceId;

    public WorkLogState(long id, String source, long time, int state, long placeId) {
        mId = id;
        mSource = source;
        mTime = time;
        mState = state;
        mPlaceId = placeId;
    }

    public WorkLogState(String source, long time, int state, long placeId) {
        mSource = source;
        mTime = time;
        mState = state;
        mPlaceId = placeId;
    }

    public WorkLogState(Parcel in) {
        mId = in.readLong();
        mSource = in.readString();
        mTime = in.readLong();
        mState = in.readInt();
        mPlaceId = in.readLong();
    }

    public WorkLogState(Cursor cursor) {
        if(cursor == null)
            throw new IllegalArgumentException("Cursor must not be null!");

        mId = cursor.getLong(cursor.getColumnIndex(WorkLogStates._ID));
        mSource = cursor.getString(cursor.getColumnIndex(WorkLogStates.SOURCE));
        mTime = cursor.getLong(cursor.getColumnIndex(WorkLogStates.TIME));
        mState = cursor.getInt(cursor.getColumnIndex(WorkLogStates.STATE));
        mPlaceId = cursor.getLong(cursor.getColumnIndex(WorkLogStates.PLACE_ID));
    }

    public ContentValues getValues() {
        ContentValues values = new ContentValues();

        if(mId > 0) {
            values.put(WorkLogStates._ID, mId);
        }

        values.put(WorkLogStates.SOURCE, mSource);
        values.put(WorkLogStates.STATE, mState);
        values.put(WorkLogStates.TIME, mTime);
        values.put(WorkLogStates.PLACE_ID, mPlaceId);

        return values;
    }

    public static final Creator<WorkLogState> CREATOR = new Creator<WorkLogState>() {
        @Override
        public WorkLogState createFromParcel(Parcel source) {
            return new WorkLogState(source);
        }

        @Override
        public WorkLogState[] newArray(int size) {
            return new WorkLogState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mSource);
        dest.writeLong(mTime);
        dest.writeInt(mState);
        dest.writeLong(mPlaceId);
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId(long placeId) {
        mPlaceId = placeId;
    }
}
