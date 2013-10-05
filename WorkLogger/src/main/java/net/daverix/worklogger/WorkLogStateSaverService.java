package net.daverix.worklogger;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import net.daverix.worklogger.lib.WorkLogState;
import net.daverix.worklogger.lib.WorkLoggerIntent;

/**
 * Created by daverix on 9/19/13.
 */
public class WorkLogStateSaverService extends IntentService {
    private WorkLogStateSaver mWorkLogStateSaver;

    public WorkLogStateSaverService() {
        super("WorkLogStateSaverService");

        Log.d("WorkLogStateSaverService", "WorkLogStateSaverService constructor");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("WorkLogStateSaverService", "WorkLogStateSaverService created");

        mWorkLogStateSaver = new WorkLogStateSaverImpl(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        WorkLogState state = intent.getParcelableExtra(WorkLoggerIntent.EXTRA_STATE);

        if(state == null) {
            Log.e("WorkLogStateSaverService", "State must not be null!");
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
