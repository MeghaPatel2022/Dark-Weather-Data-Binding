package com.darksky.weather.today.weatherforecast.application;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.onesignal.OneSignal;

public class MainApplication extends MultiDexApplication {

    private static final String ONESIGNAL_APP_ID = "55cd9531-fdab-49cf-90a8-aba206b633f1";
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

    }

    @Override
    protected void attachBaseContext(Context base) {
        Context context = setupTheme(base);
        super.attachBaseContext(context);
    }

    public Context setupTheme(Context context) {
        Resources res = context.getResources();
        int mode = res.getConfiguration().uiMode;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Configuration config = new Configuration(res.getConfiguration());
        config.uiMode = mode;
        if (Build.VERSION.SDK_INT >= 17) {
            context = context.createConfigurationContext(config);
        } else {
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

}
