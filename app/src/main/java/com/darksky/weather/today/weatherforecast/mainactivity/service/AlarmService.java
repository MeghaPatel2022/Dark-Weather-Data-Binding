package com.darksky.weather.today.weatherforecast.mainactivity.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.darksky.weather.today.weatherforecast.R;
import com.darksky.weather.today.weatherforecast.mainactivity.MainActivity;
import com.darksky.weather.today.weatherforecast.mainactivity.constant.Const;
import com.darksky.weather.today.weatherforecast.mainactivity.model.currentdata.CurrentDataResponseItem;
import com.darksky.weather.today.weatherforecast.mainactivity.sharedpreference.Preference;
import com.darksky.weather.today.weatherforecast.mainactivity.utils.ConnectionDetector;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class AlarmService extends JobIntentService implements LocationListener {

    public static final String ANDROID_CHANNEL_ID = "com.darksky.weather.today.weatherforecast";
    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private static boolean isNotify = false;
    public LocationManager mLocManager;
    public LocationManager locationManager;
    public MyLocationListener listener;
    public Location previousBestLocation = null;
    String mainKey = "";
    int Id;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    NotificationCompat.Builder notificationBuilder;
    Intent intent;
    int counter = 0;
    private String city = "", area = "", country = "", address = "";

    /**
     * Convenience method for enqueuing work in to this service.
     */
    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, AlarmService.class, JOB_ID, work);
        if (Preference.getNotification(context.getApplicationContext()).equals("")) {
            isNotify = true;
        }
    }


    @Override
    protected void onHandleWork(@NonNull @NotNull Intent intent) {
        if (Preference.getNotification(getApplicationContext()).equals("")) {
            if (Preference.getNotification(getApplicationContext()).equals("")) {
                Id = intent.getIntExtra("id", 0);
                cd = new ConnectionDetector(getApplicationContext());
                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent) {
                    mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            listener = new MyLocationListener();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    Activity#requestPermissions
                                    if (isInternetPresent) {
                                        new getKey(35.787743, -78.644257).execute();
                                    }
                                } else {
                                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
                                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);

                                    if (isInternetPresent) {
                                        new getKey(Double.parseDouble(Preference.getLatitude(getApplicationContext())), Double.parseDouble(Preference.getLongitude(getApplicationContext()))).execute();
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else return isNewer && !isSignificantlyLessAccurate && isFromSameProvider;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
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
                                if (Preference.getUnit(getApplicationContext()).equalsIgnoreCase("Metric")) {
                                    value = newResponse.getTemperature().getMetric().getValue();
                                    unit = newResponse.getTemperature().getMetric().getUnit();
                                } else {
                                    value = newResponse.getTemperature().getImperial().getValue();
                                    unit = newResponse.getTemperature().getImperial().getUnit();
                                }

                                Intent editIntent = new Intent(getApplicationContext(), MainActivity.class);
                                PendingIntent mClick = PendingIntent.getActivity(getApplicationContext(), Id, editIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                                        .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.icon))
                                        .setSmallIcon(R.drawable.icon)
                                        .setContentTitle(getApplicationContext().getResources().getString(R.string.app_name))
                                        .setContentText("" + value + "" + "" + (char) 0x00B0 + unit + " (" +
                                                newResponse.getWeatherText() + ")")
                                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                        .setContentIntent(mClick)
                                        .setAutoCancel(true)
                                        .setOnlyAlertOnce(true);
                                notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

                                NotificationManager nManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    NotificationChannel notificationChannel = new NotificationChannel(ANDROID_CHANNEL_ID, getApplicationContext().getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);

                                    // Configure the notification channel.
                                    notificationChannel.setDescription("" + value + "" + "" + (char) 0x00B0 + unit + " (" +
                                            newResponse.getWeatherText() + ")");
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                        notificationChannel.setAllowBubbles(true);
                                    }
                                    notificationChannel.setShowBadge(true);
                                    notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                                    notificationChannel.enableLights(true);
                                    notificationChannel.setLightColor(Color.RED);
                                    notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                                    notificationChannel.enableVibration(true);
                                    notificationBuilder.setGroup(ANDROID_CHANNEL_ID);
                                    nManager.createNotificationChannel(notificationChannel);
                                }
                                Notification note = notificationBuilder.build();
                                note.defaults |= Notification.DEFAULT_SOUND;
                                note.defaults |= Notification.DEFAULT_VIBRATE;
                                nManager.notify(Id, note);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    public String getAddress(double lat, double lng) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
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
                            mainKey = key;
                            Const.KEY = key;

                            address = getAddress(latitude,
                                    longitude);

                            new getCurrentData().execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
        return mainKey;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (isInternetPresent) {
            new getKey(location.getLatitude(), location.getLongitude()).execute();
        }
        if (location != null) {
            mLocManager.removeUpdates(this);
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Intent intent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(final Location loc) {
            if (isInternetPresent) {
                new getKey(loc.getLatitude(), loc.getLongitude()).execute();
                if (isBetterLocation(loc, previousBestLocation)) {
                    loc.getLatitude();
                    loc.getLongitude();
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        public void onProviderDisabled(String provider) {
        }


        public void onProviderEnabled(String provider) {
        }
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
