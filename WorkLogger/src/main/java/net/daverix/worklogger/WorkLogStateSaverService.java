package net.daverix.worklogger;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by daverix on 9/19/13.
 */
public class WorkLogStateSaverService extends IntentService {
    public static final String EXTRA_WORK_LOG_STATE = "workLogState";
    private WorkLogStateSaver mWorkLogStateSaver;

    public WorkLogStateSaverService() {
        super("WorkLogStateSaverService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mWorkLogStateSaver = new WorkLogStateSaverImpl(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        WorkLogState state = intent.getParcelableExtra(EXTRA_WORK_LOG_STATE);

        if(state == null) {
            Log.e("WorkLogStateSaverService", "WorkLogState is null");
            return;
        }

        Uri uri = mWorkLogStateSaver.saveState(state);
        if(uri != null) {
            Log.i("WorkLogStateSaverService", "WorkLogState created with uri " + uri);
        }
        else {
            Log.e("WorkLogStateSaverService", "WorkLogState could not be created");
        }
    }
}
