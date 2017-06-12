package me.readhub.android.md.app;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);
    }

}
