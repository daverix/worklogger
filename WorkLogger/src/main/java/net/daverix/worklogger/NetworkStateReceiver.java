package net.daverix.worklogger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by daverix on 9/19/13.
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long time = System.currentTimeMillis();
        String bssid = intent.getStringExtra(WifiManager.EXTRA_BSSID);
        if(bssid == null) {
            Log.e("NetworkStateReceiver", "BSSID is null!");
            return;
        }
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

        if(info == null) {
            Log.e("NetworkStateReceiver", "Network info is null!");
            return;
        }

        switch (info.getState()) {
            case CONNECTED:
                Log.i("NetworkStateReceiver", "Saving started state with bssid " + bssid);
                saveWorkLogState(context, bssid, WorkLogState.STATE_START, time);
                break;
            case DISCONNECTED:
                Log.i("NetworkStateReceiver", "Saving ended state with bssid " + bssid);
                saveWorkLogState(context, bssid, WorkLogState.STATE_END, time);
                break;
            default:
                Log.d("NetworkStateReceiver", "Ignoring network state " + info.getState().name() + " with bssid " + bssid);
        }
    }

    protected void saveWorkLogState(Context context, String bssid, int state, long time) {
        Intent serviceIntent = new Intent(context, WorkLogStateSaver.class);
        WorkLogState workLogState = new WorkLogState(bssid, time, state);
        serviceIntent.putExtra(WorkLogStateSaverService.EXTRA_WORK_LOG_STATE, workLogState);

        context.startService(serviceIntent);
    }
}
