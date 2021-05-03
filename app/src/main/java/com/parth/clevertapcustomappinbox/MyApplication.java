package com.parth.clevertapcustomappinbox;

import android.app.Application;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;

public class MyApplication extends Application {
    public static CleverTapAPI clevertapDefaultInstance;

    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this); // Must be called before super.onCreate()
        super.onCreate();

        CleverTapAPI.getDefaultInstance(getApplicationContext()).enableDeviceNetworkInfoReporting(true);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
    }
}