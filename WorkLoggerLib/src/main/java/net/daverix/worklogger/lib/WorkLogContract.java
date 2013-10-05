package net.daverix.worklogger.lib;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by daverix on 9/19/13.
 */
public class WorkLogContract {
    public static final String AUTHORITY = "net.daverix.worklogger.provider";

    protected interface WorkLogStateColumns {
        public static final String SOURCE = "source";
        public static final String TIME = "time";
        public static final String STATE = "state";
        public static final String PLACE_ID = "place_id";
    }

    protected interface PlaceColumns {
        public static final String NAME = "name";
        public static final String CREATED = "created";
        public static final String UPDATED = "updated";
        public static final String IS_WORK = "is_work";
    }

    public static class WorkLogStates implements BaseColumns, WorkLogStateColumns {
        public static final String PATH = "workLogStates";
        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme("content")
                .authority(AUTHORITY)
                .path(PATH)
                .build();
    }

    public static class Places implements BaseColumns, PlaceColumns {
        public static final String PATH = "places";
        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme("content")
                .authority(AUTHORITY)
                .path(PATH)
                .build();
    }

    public static class PlaceLogs implements BaseColumns, PlaceColumns, WorkLogStateColumns {
        public static final String PATH = "placeLogs";
        public static final Uri CONTENT_URI = new Uri.Builder()
                .scheme("content")
                .authority(AUTHORITY)
                .path(PATH)
                .build();
    }
}
