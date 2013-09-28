package net.daverix.worklogger;

import java.util.Calendar;

/**
 * Created by daverix on 9/28/13.
 */
public class TimeHandlerImpl implements TimeHandler {

    @Override
    public long getStartTimeToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return calendar.getTimeInMillis();
    }

    @Override
    public long getEndTimeToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        calendar.add(Calendar.HOUR, 24);

        return calendar.getTimeInMillis();
    }
}
