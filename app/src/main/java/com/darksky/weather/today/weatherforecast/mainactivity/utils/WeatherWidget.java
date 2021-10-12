package com.darksky.weather.today.weatherforecast.mainactivity.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.CellLocation;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.darksky.weather.today.weatherforecast.R;
import com.darksky.weather.today.weatherforecast.mainactivity.MainActivity;
import com.darksky.weather.today.weatherforecast.mainactivity.constant.Const;
import com.darksky.weather.today.weatherforecast.mainactivity.model.currentdata.CurrentDataResponseItem;
import com.darksky.weather.today.weatherforecast.mainactivity.service.WidgetReceiver;
import com.darksky.weather.today.weatherforecast.mainactivity.sharedpreference.Preference;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class WeatherWidget extends AppWidgetProvider implements LocationListener {

    public static String ACTION_WIDGET_ACTIVATE = "com.live.weather.update";
    public static long nextHalfHour;
    static AlarmManager alarms = null;
    private static PendingIntent alarm = null;
    public LocationManager mLocManager;
    Context context;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    double latitude;
    double longitude;
    String[] month = {"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
    String[] weekOfDay = {"Mon", "Tues", "Wed", "Thur", "Fri", "Sat", "Sun"};
    String mainKey = "";
    int Id;
    private String city = "", area = "", country = "", address = "";

    public static void updateAllWidgets(Context context, Intent intentWidget) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        intentWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context.getPackageName(), WeatherWidget.class.getName()));
        intentWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        context.sendBroadcast(intentWidget);
    }

    public static void setAlarm(Context context, Calendar mCalendar) {
        Intent callToAlarm = new Intent(context, WidgetReceiver.class);
        alarm = PendingIntent.getBroadcast(context, 0, callToAlarm,
                PendingIntent.FLAG_CANCEL_CURRENT);
        alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar c = Calendar.getInstance();
        long currentTime = c.getTimeInMillis();
        long diffTime = mCalendar.getTimeInMillis() - currentTime;
        alarms.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + diffTime,
                1800000, alarm);
//        alarms.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1800000, alarm);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.context = context;
        // Проверяем есть ли соединения с сетью интернет
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }


        // Обновляем все виджеты разом, для которых настало время обновления, если есть Интернет
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            cd = new ConnectionDetector(context);
            isInternetPresent = cd.isConnectingToInternet();
            mLocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                }
            }
            mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    this);

            mLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                    0, this);

            locationUpdate();

        }

    }

    private void locationUpdate() {
        CellLocation.requestLocationUpdate();
    }

    public String getAddress(double lat, double lng) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context.getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                city = returnedAddress.getLocality();
                area = strReturnedAddress.toString();
                country = returnedAddress.getCountryName();

                strAdd = returnedAddress.getLocality() + "," + returnedAddress.getCountryName();

            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    private String getKey(double latitude, double longitude) {
        AndroidNetworking.get("https://api.accuweather.com/locations/v1/cities/geoposition/search.json")
                .addQueryParameter("apikey", "d7e795ae6a0d44aaa8abb1a0a7ac19e4")
                .addQueryParameter("q", latitude + "," + longitude)
                .addQueryParameter("language", "en-gb")
                .addQueryParameter("details", "true")
                .addQueryParameter("toplevel", "false")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String key = response.getString("Key");
                            Log.e("LLL_Key: ", key);
                            mainKey = key;
                            Const.KEY = key;

                            address = getAddress(latitude,
                                    longitude);

                            Log.e("LLLL_area: ", area);
                            Log.e("LLLL_area: ", address);

                            new getCurrentData().execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("LLL_GetKey_Error: ", anError.getErrorBody());
                    }
                });
        return mainKey;
    }

    private void getCurrentData() {
        AndroidNetworking.get("https://api.accuweather.com/currentconditions/v1/" + Const.KEY + ".json")
                .addQueryParameter("apikey", "srRLeAmTroxPinDG8Aus3Ikl6tLGJd94")
                .addQueryParameter("language", "en-gb")
                .addQueryParameter("details", "true")
                .addQueryParameter("getphotos", "false")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() > 0) {

                                JSONObject jsonObject = response.getJSONObject(0);
                                CurrentDataResponseItem newResponse = new Gson().fromJson(jsonObject.toString(), CurrentDataResponseItem.class);
                                double value;
                                String unit;
                                if (Preference.getUnit(context.getApplicationContext()).equalsIgnoreCase("Metric")) {
                                    value = newResponse.getTemperature().getMetric().getValue();
                                    unit = newResponse.getTemperature().getMetric().getUnit();
                                } else {
                                    value = newResponse.getTemperature().getImperial().getValue();
                                    unit = newResponse.getTemperature().getImperial().getUnit();
                                }

                                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

                                // Устанавливаем данные в виджете
                                remoteViews.setTextViewText(R.id.tvTemp, "" + Math.round(Float.parseFloat(String.valueOf(value))) + "" + "" + (char) 0x00B0);
                                remoteViews.setTextViewText(R.id.tvTitile, newResponse.getWeatherText() + "");
                                DateTime dt = new DateTime(newResponse.getLocalObservationDateTime());


                                Date callendarDate = Calendar.getInstance().getTime();
                                DateTime dt1 = new DateTime(callendarDate);
                                String hour;
                                String minute;
                                if (dt1.getHourOfDay() <= 9) {
                                    hour = "0" + dt1.getHourOfDay();
                                } else {
                                    hour = "" + dt1.getHourOfDay();
                                }

                                if (dt1.getMinuteOfHour() <= 9) {
                                    minute = "0" + dt1.getMinuteOfHour();
                                } else {
                                    minute = "" + dt1.getMinuteOfHour();
                                }

                                double maxValue, minValue;
                                String maxUnit, minUnit;
                                if (Preference.getUnit(context.getApplicationContext()).equalsIgnoreCase("Metric")) {
                                    maxValue = newResponse.getTemperatureSummary().getPast6HourRange().getMaximum().getMetric().getValue();
                                    maxUnit = newResponse.getTemperatureSummary().getPast6HourRange().getMaximum().getMetric().getUnit();
                                    minValue = newResponse.getTemperatureSummary().getPast6HourRange().getMinimum().getMetric().getValue();
                                    minUnit = newResponse.getTemperatureSummary().getPast6HourRange().getMinimum().getMetric().getUnit();
                                } else {
                                    maxValue = newResponse.getTemperatureSummary().getPast6HourRange().getMaximum().getImperial().getValue();
                                    maxUnit = newResponse.getTemperatureSummary().getPast6HourRange().getMaximum().getImperial().getUnit();
                                    minValue = newResponse.getTemperatureSummary().getPast6HourRange().getMinimum().getImperial().getValue();
                                    minUnit = newResponse.getTemperatureSummary().getPast6HourRange().getMinimum().getImperial().getUnit();
                                }

                                String minmax = "" + Math.round(Float.parseFloat(String.valueOf(minValue))) + " " + "" + (char) 0x00B0 + " | " +
                                        Math.round(Float.parseFloat(String.valueOf(maxValue))) + " " + "" + (char) 0x00B0;

                                remoteViews.setTextViewText(R.id.tvTime, minmax);
                                remoteViews.setTextViewText(R.id.tvCity, getCompleteAddressString(latitude, longitude));
                                remoteViews.setTextViewText(R.id.tvDate, month[dt.getMonthOfYear() - 1] + "," + dt.getDayOfMonth() + "  " + weekOfDay[dt.getDayOfWeek() - 1]);
                                Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                                        Const.weatherIcon[newResponse.getWeatherIcon() - 1]);
                                remoteViews.setImageViewBitmap(R.id.weather_icon, icon);

                                // Делаем обработчик нажатия на виджет для вызова окна программы
                                Intent intentMainActivity = new Intent(context, MainActivity.class);
                                PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 0, intentMainActivity, 0);
                                remoteViews.setOnClickPendingIntent(R.id.main_window, pendingIntent3);

                                // Делаем обработчик нажатия на картинку виджета для обновления
                                Intent activeUpdate = new Intent(context, WeatherWidget.class);
                                activeUpdate.setAction(ACTION_WIDGET_ACTIVATE);
                                PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, activeUpdate, 0);
                                remoteViews.setOnClickPendingIntent(R.id.weather_icon, actionPendingIntent);

                                // Обновляем все виджеты разом
                                ComponentName widgetComponents = new ComponentName(context.getPackageName(), WeatherWidget.class.getName());
                                appWidgetManager.updateAppWidget(widgetComponents, remoteViews);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("LLL_Current_Data: ", anError.getErrorBody());
                    }
                });
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = returnedAddress.getCountryName();

                strAdd = returnedAddress.getLocality() + "," + returnedAddress.getCountryName();

            } else {
                Log.w("My Current l", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current ", "Canont get Address!");
        }
        return strAdd;
    }

    // Прослушивание нажатий на виджет
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();

        if (ACTION_WIDGET_ACTIVATE.equals(action)) {
            updateAllWidgets(context, intent);
        } else {
            super.onReceive(context, intent);
        }

    }

    @Override
    public void onDisabled(Context context) {

        alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Cancel Alarm using Reminder ID
        alarm = PendingIntent.getBroadcast(context, 0, new Intent(context, WidgetReceiver.class), 0);
        alarms.cancel(alarm);
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        long currentTime = System.currentTimeMillis();
        nextHalfHour = currentTime + 1800000;
        setAlarm(context, calendar);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.e("LLL_current: ", location.getLatitude() + "      " + location.getLongitude());
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        new getKey(location.getLatitude(), location.getLongitude()).execute();
        if (location != null) {
            mLocManager.removeUpdates(this);
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    private final class getKey extends AsyncTask<Void, Void, String> {

        double latitude, longitude;

        public getKey(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected String doInBackground(Void... params) {
            Const.KEY = getKey(latitude, longitude);
            return Const.KEY;
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private final class getCurrentData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            getCurrentData();
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }
}

