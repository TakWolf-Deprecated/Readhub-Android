package me.readhub.android.md.model.api;

import android.os.Build;

import me.readhub.android.md.BuildConfig;

public final class ApiDefine {

    private ApiDefine() {}

    public static final String BASE_URL = "https://api.readhub.me/";
    public static final String USER_AGENT = "Readhub-Material/" + BuildConfig.VERSION_NAME + " (Android " + Build.VERSION.RELEASE + "; " + Build.MODEL + "; " + Build.MANUFACTURER + ")";

}
