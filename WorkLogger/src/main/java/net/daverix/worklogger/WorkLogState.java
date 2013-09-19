package net.daverix.worklogger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by daverix on 9/19/13.
 */
public class WorkLogState implements Parcelable {
    public static final int STATE_START = 1;
    public static final int STATE_END = 2;

    private String mBssid;
    private long mTime;
    private int mState;

    public WorkLogState(String ssid, long time, int state) {
        mBssid = ssid;
        mTime = time;
        mState = state;
    }

    public WorkLogState(Parcel in) {
        mBssid = in.readString();
        mTime = in.readLong();
        mState = in.readInt();
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
        dest.writeString(mBssid);
        dest.writeLong(mTime);
        dest.writeInt(mState);
    }

    public String getBssid() {
        return mBssid;
    }

    public void setBssid(String bssid) {
        mBssid = bssid;
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
}
