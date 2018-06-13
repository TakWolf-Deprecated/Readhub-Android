package me.readhub.android.md.ui.util;

import android.app.Activity;
import android.support.annotation.Nullable;

public final class ActivityUtils {

    private ActivityUtils() {}

    public static boolean isAlive(@Nullable Activity activity) {
        return activity != null && !activity.isDestroyed() && !activity.isFinishing();
    }

}
