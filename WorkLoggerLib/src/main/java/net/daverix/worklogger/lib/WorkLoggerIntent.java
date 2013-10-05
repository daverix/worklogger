package net.daverix.worklogger.lib;

/**
 * Created by daverix on 10/5/13.
 */
public class WorkLoggerIntent {
    /**
     * service action: Used to save a WorkState to the content provider.
     */
    public static final String ACTION_ADD_STATE = "net.daverix.worklogger.intent.action.ADD_STATE";

    /**
     * Used with {#ACTION_ADD_STATE} to provide a {@link net.daverix.worklogger.lib.WorkLogState} object.
     */
    public static final String EXTRA_STATE = "net.daverix.worklogger.intent.extra.STATE";

    /**
     * Used together with {@link android.content.Intent#ACTION_MAIN} to say that the activity of a plugin provide settings.
     */
    public static final String CATEGORY_PLUGIN_SETTINGS = "net.daverix.worklogger.intent.category.PLUGIN_SETTINGS";
}
