package net.daverix.worklogger;

import android.net.Uri;

/**
 * Created by daverix on 9/19/13.
 */
public interface WorkLogStateSaver {
    public Uri saveState(WorkLogState state);
}
