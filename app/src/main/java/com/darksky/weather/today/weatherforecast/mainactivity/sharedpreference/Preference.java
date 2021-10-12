package com.darksky.weather.today.weatherforecast.mainactivity.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Preference {
    public static final String MY_PREFERENCES = "DarkWeatherApp";

    public static final String UNIT = "Unit";
    public static final String LAT_LONG = "Lat_Long";

    public static final String PREF_DEFAULT_LOCATION_LIST = "Pref_default_location_list";
    public static final String VALID_WIDGET_ALARM = "valid_widget_Alarm";
    public static final String NOTIFICATION = "notification_on_off";
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    public static final String KEY = "Key";

    public static String getUnit(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        String ans = sharedpreferences.getString(UNIT, "Metric");
        Log.i("LLL_Unit: ", ans);
        return ans;
    }

    public static void setUnit(Context c1, String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(UNIT, value);
        editor.apply();
    }

    public static String getKey(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        String ans = sharedpreferences.getString(KEY, "");
        Log.i("LLL_Unit: ", ans);
        return ans;
    }

    public static void setKey(Context c1, String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(KEY, value);
        editor.apply();
    }

    public static String getLatitude(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        String ans = sharedpreferences.getString(LATITUDE, "");
        Log.i("LLL_Lat: ", ans);
        return ans;
    }

    public static void setLatitude(Context c1, String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LATITUDE, value);
        editor.apply();
    }

    public static String getLongitude(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        String ans = sharedpreferences.getString(LONGITUDE, "");
        Log.i("LLL_Long: ", ans);
        return ans;
    }

    public static void setLongitude(Context c1, String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LONGITUDE, value);
        editor.apply();
    }

    public static String getDefaultLocationListInfo(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_DEFAULT_LOCATION_LIST, "");
    }

    public static void setDefaultLocationInfo(Context context, String bytes) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_DEFAULT_LOCATION_LIST, bytes).apply();
    }

    public static String getValidAlarm(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(VALID_WIDGET_ALARM, "");
    }

    public static void setValidAlarm(Context context, String bytes) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(VALID_WIDGET_ALARM, bytes).apply();
    }

    public static String getNotification(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(NOTIFICATION, "");
    }

    public static void setNotification(Context context, String bytes) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(NOTIFICATION, bytes).apply();
    }

}
