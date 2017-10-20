package me.readhub.android.md.ui.util;

import android.app.Activity;

public final class ActivityUtils {

    private ActivityUtils() {}

    public static boolean isAlive(Activity activity) {
        return activity != null && !activity.isDestroyed() && !activity.isFinishing();
    }

}
