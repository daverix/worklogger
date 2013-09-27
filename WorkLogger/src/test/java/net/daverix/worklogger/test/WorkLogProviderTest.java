package net.daverix.worklogger.test;

import android.database.Cursor;
import android.net.Uri;

import net.daverix.worklogger.WorkLogContract;
import net.daverix.worklogger.WorkLogState;
import net.daverix.worklogger.WorkLogStateMapper;
import net.daverix.worklogger.WorkLogStateMapperImpl;
import net.daverix.worklogger.WorkLogStateSaver;
import net.daverix.worklogger.WorkLogStateSaverImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by daverix on 9/22/13.
 */
@RunWith(RobolectricGradleTestRunner.class)
public class WorkLogProviderTest {

    @Test
    public void testShouldVerifyWorkLogStateStartIsSaved() throws Exception {
        //Arrange
        final String expectedBssid = "aa:bb:cc:dd:ee";
        final long expectedTime = 123123123;
        final int expectedState = WorkLogState.STATE_START;

        WorkLogState state = new WorkLogState(expectedBssid, expectedTime, expectedState);

        //Act
        WorkLogState actual = saveAndReturnWorkLogState(state);

        //Assert
        assertThat(actual.getId(), is(not(equalTo(0L))));
        assertThat(actual.getBssid(), is(equalTo(expectedBssid)));
        assertThat(actual.getState(), is(equalTo(expectedState)));
        assertThat(actual.getTime(), is(equalTo(expectedTime)));
    }

    private WorkLogState saveAndReturnWorkLogState(WorkLogState state) throws Exception {
        WorkLogStateMapper mapper = new WorkLogStateMapperImpl();
        WorkLogStateSaver saver = new WorkLogStateSaverImpl(Robolectric.application, mapper);
        Uri uri = saver.saveState(state);

        if(uri == null) {
            throw new Exception("WorkLogState not found");
        }
        Cursor cursor = null;
        try {
            cursor = Robolectric.application.getContentResolver().query(WorkLogContract.WorkLogStates.CONTENT_URI,
                new String[]{
                        WorkLogContract.WorkLogStates._ID,
                        WorkLogContract.WorkLogStates.STATE,
                        WorkLogContract.WorkLogStates.TIME,
                        WorkLogContract.WorkLogStates.BSSID
                }, null, null, null);

            if(cursor == null || !cursor.moveToFirst()) {
                throw new Exception("WorkLogState not found");
            }

            return mapper.mapCursor(cursor);
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
    }
}
