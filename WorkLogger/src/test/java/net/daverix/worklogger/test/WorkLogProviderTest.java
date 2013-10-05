package net.daverix.worklogger.test;

import android.database.Cursor;
import android.net.Uri;

import net.daverix.worklogger.lib.WorkLogContract;
import net.daverix.worklogger.WorkLogProvider;
import net.daverix.worklogger.lib.WorkLogState;
import net.daverix.worklogger.WorkLogStateSaver;
import net.daverix.worklogger.WorkLogStateSaverImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowContentResolver;

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
        final String expectedSource = "wifi";
        final long expectedTime = 123123123;
        final int expectedState = WorkLogState.STATE_START;
        final int expectedPlaceId = 42;

        WorkLogState state = new WorkLogState(expectedSource, expectedTime, expectedState, expectedPlaceId);

        //Act
        WorkLogState actual = saveAndReturnWorkLogState(state);

        //Assert
        assertThat(actual.getId(), is(not(equalTo(0L))));
        assertThat(actual.getSource(), is(equalTo(expectedSource)));
        assertThat(actual.getState(), is(equalTo(expectedState)));
        assertThat(actual.getTime(), is(equalTo(expectedTime)));
        assertThat(actual.getPlaceId(), is(equalTo(expectedPlaceId)));
    }

    private WorkLogState saveAndReturnWorkLogState(WorkLogState state) throws Exception {
        WorkLogProvider provider = new WorkLogProvider();
        provider.onCreate();

        ShadowContentResolver.registerProvider(WorkLogContract.AUTHORITY, provider);

        WorkLogStateSaver saver = new WorkLogStateSaverImpl(Robolectric.application);
        Uri uri = saver.saveState(state);

        if(uri == null) {
            throw new Exception("WorkLogState not inserted!");
        }
        Cursor cursor = null;
        try {
            cursor = Robolectric.application.getContentResolver().query(WorkLogContract.WorkLogStates.CONTENT_URI,
                new String[]{
                        WorkLogContract.WorkLogStates._ID,
                        WorkLogContract.WorkLogStates.STATE,
                        WorkLogContract.WorkLogStates.TIME,
                        WorkLogContract.WorkLogStates.SOURCE
                }, null, null, null);

            if(cursor == null || !cursor.moveToFirst()) {
                throw new Exception("WorkLogState not found");
            }

            return new WorkLogState(cursor);
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
    }
}
