package com.darksky.weather.today.weatherforecast.mainactivity;

import android.Manifest;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.darksky.weather.today.weatherforecast.R;
import com.darksky.weather.today.weatherforecast.databinding.ActivityMainBinding;
import com.darksky.weather.today.weatherforecast.locationactivity.LocationActivity;
import com.darksky.weather.today.weatherforecast.mainactivity.adapter.HourlyDataAdapter;
import com.darksky.weather.today.weatherforecast.mainactivity.adapter.Weather15Days;
import com.darksky.weather.today.weatherforecast.mainactivity.constant.Const;
import com.darksky.weather.today.weatherforecast.mainactivity.model.currentdata.CurrentDataResponseItem;
import com.darksky.weather.today.weatherforecast.mainactivity.model.days15.DailyForecastsItem;
import com.darksky.weather.today.weatherforecast.mainactivity.model.days15.Days15Response;
import com.darksky.weather.today.weatherforecast.mainactivity.model.hours24.Hours24ResponseItem;
import com.darksky.weather.today.weatherforecast.mainactivity.model.locationdata.AddLocationData;
import com.darksky.weather.today.weatherforecast.mainactivity.service.AlarmReceiver;
import com.darksky.weather.today.weatherforecast.mainactivity.sharedpreference.Preference;
import com.darksky.weather.today.weatherforecast.mainactivity.utils.ConnectionDetector;
import com.darksky.weather.today.weatherforecast.mainactivity.utils.WeatherWidget;
import com.darksky.weather.today.weatherforecast.settingactivity.SettingActivity;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends BaseActivity implements OnChartValueSelectedListener, LocationListener {

    private static final int RC_APP_UPDATE = 101;
    private final List<DailyForecastsItem> dailyForecastsItemList = new ArrayList<>();
    public LocationManager mLocManager;
    Type type = new TypeToken<List<AddLocationData>>() {
    }.getType();
    ActivityMainBinding activityMainBinding;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String mainKey = "";
    boolean isDay15Called = false;
    String[] weekOfDay = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
    MyClickHandlers myClickHandlers;
    private String city = "", area = "", country = "", address = "";

    private HourlyDataAdapter hourlyDataAdapter;
    private Weather15Days weather15Days;
    private AppUpdateManager mAppUpdateManager;
    InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED) {
                        //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                        popupSnackbarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED) {
                        if (mAppUpdateManager != null) {
                            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                        }

                    } else {
                        Log.i("LLL_Update_App: ", "InstallStateUpdatedListener: state: " + state.installStatus());
                    }
                }
            };

    public static void updateAllWidgets(Context context, Intent intentWidget) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        intentWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context.getPackageName(), WeatherWidget.class.getName()));
        intentWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        context.sendBroadcast(intentWidget);
    }

    public static String decodeEmoji(String message) {
        String myString = null;
        try {
            return URLDecoder.decode(
                    message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return message;
        }
    }

    private void popupSnackbarForCompleteUpdate() {

        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.coordinatorLayout_main),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mAppUpdateManager != null) {
                mAppUpdateManager.completeUpdate();
            }
        });

        snackbar.setActionTextColor(getResources().getColor(R.color.teal_200));
        snackbar.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAppUpdateManager != null) {
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAppUpdateManager != null) {
            mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    private void setChartStyle() {
        activityMainBinding.chart1.setOnChartValueSelectedListener(this);

        // no description text
        activityMainBinding.chart1.getDescription().setEnabled(false);

        // enable touch gestures
        activityMainBinding.chart1.setTouchEnabled(true);

        activityMainBinding.chart1.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        activityMainBinding.chart1.setDragEnabled(true);
        activityMainBinding.chart1.setScaleEnabled(true);
        activityMainBinding.chart1.setDrawGridBackground(false);
        activityMainBinding.chart1.setHighlightPerDragEnabled(true);
        activityMainBinding.chart1.setBorderColor(Color.TRANSPARENT);

        // if disabled, scaling can be done on x- and y-axis separately
        activityMainBinding.chart1.setPinchZoom(true);

        // set an alternative background color
        activityMainBinding.chart1.setBackgroundColor(Color.TRANSPARENT);

        activityMainBinding.chart1.animateX(1300);

        // get the legend (only possible after setting data)
        Legend l = activityMainBinding.chart1.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(13f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        XAxis xAxis = activityMainBinding.chart1.getXAxis();
        xAxis.setTextSize(13f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = activityMainBinding.chart1.getAxisLeft();
        leftAxis.setTextColor(Color.TRANSPARENT);
        if (!Preference.getUnit(MainActivity.this).equalsIgnoreCase("Metric")) {
            leftAxis.setAxisMaximum(150f);
        } else {
            leftAxis.setAxisMaximum(70f);
        }

        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularityEnabled(false);

        YAxis rightAxis = activityMainBinding.chart1.getAxisRight();
        rightAxis.setTextColor(Color.TRANSPARENT);
        if (!Preference.getUnit(MainActivity.this).equalsIgnoreCase("Metric")) {
            rightAxis.setAxisMaximum(250f);
        } else {
            rightAxis.setAxisMaximum(140f);
        }
        rightAxis.setAxisMinimum(0f);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);

    }

    @Override
    public void permissionGranted() {
        if (isInternetPresent) {

            activityMainBinding.ivNoInternetConnection.setVisibility(View.GONE);
            activityMainBinding.rlLoading.setVisibility(View.VISIBLE);

            mLocManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    mainactivity#requestPermissions
                    Preference.setLatitude(MainActivity.this, "35.787743");
                    Preference.setLongitude(MainActivity.this, "-78.644257");
                    new getKey(35.787743, -78.644257).execute();
                } else {
                    mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                            this);

                    mLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                            0, this);

                    locationUpdate();
                }
            }

            activityMainBinding.rvHourly.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));

            activityMainBinding.rv15Days.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
            activityMainBinding.rv15Days.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
            activityMainBinding.rv15Days.setNestedScrollingEnabled(false);

            setChartStyle();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        activityMainBinding.setMatrixType(Preference.getUnit(MainActivity.this));

        AndroidNetworking.initialize(MainActivity.this);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();

        myClickHandlers = new MyClickHandlers(MainActivity.this);
        activityMainBinding.setClickHandler(myClickHandlers);
    }

    private void locationUpdate() {
        CellLocation.requestLocationUpdate();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.e("LLL_current: ", location.getLatitude() + "      " + location.getLongitude());
        if (Preference.getKey(MainActivity.this).equals("")) {
            Preference.setLatitude(MainActivity.this, String.valueOf(location.getLatitude()));
            Preference.setLongitude(MainActivity.this, String.valueOf(location.getLongitude()));
            new getKey(location.getLatitude(), location.getLongitude()).execute();
        } else {
            Const.KEY = Preference.getKey(MainActivity.this);
            Log.e("LLL_Key: ", Const.KEY);
            ArrayList<AddLocationData> addLocationDataArrayList;
            if (!Preference.getKey(MainActivity.this).equals("")) {
                Const.KEY = Preference.getKey(MainActivity.this);
                new getCurrentData().execute();
                addLocationDataArrayList = new Gson().fromJson(Preference.getDefaultLocationListInfo(getApplicationContext()), type);
                AddLocationData addLocationData = new AddLocationData();
                addLocationData.setKey(Const.KEY);
                activityMainBinding.setMatrixType(Preference.getUnit(MainActivity.this));
                if (addLocationDataArrayList.contains(addLocationData)) {
                    if (addLocationDataArrayList.indexOf(addLocationData) != -1) {
                        int pos = addLocationDataArrayList.indexOf(addLocationData);
                        address = addLocationDataArrayList.get(pos).getCity() + ", " + addLocationDataArrayList.get(pos).getCountry();
                        Const.KEY = Preference.getKey(MainActivity.this);
                        new getCurrentData().execute();
                    }
                }
            }
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
        Toast.makeText(MainActivity.this, "Gps Disabled", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
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

    private boolean getDay15Data(String KEY) {

        boolean isMatrix = true;
        if (!Preference.getUnit(MainActivity.this).equalsIgnoreCase("Metric")) {
            isMatrix = false;
        }

        AndroidNetworking.get("https://api.accuweather.com/forecasts/v1/daily/15day/" + KEY + ".json")
                .addQueryParameter("apikey", "srRLeAmTroxPinDG8Aus3Ikl6tLGJd94")
                .addQueryParameter("language", "en-gb")
                .addQueryParameter("details", "true")
                .addQueryParameter("metric", String.valueOf(isMatrix))
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dailyForecastsItemList.clear();
                        Days15Response days15Response = new Gson().fromJson(response.toString(), Days15Response.class);

                        for (int i = 1; i < 8; i++) {
                            dailyForecastsItemList.add(days15Response.getDailyForecasts().get(i));
                        }
                        isDay15Called = true;
                        setChart();
                        activityMainBinding.chart1.notifyDataSetChanged();
                        activityMainBinding.chart1.invalidate();

                        List<Object> objectArrayList = new ArrayList<>(days15Response.getDailyForecasts());
                        weather15Days = new Weather15Days(objectArrayList, objectArrayList.size(), MainActivity.this);
                        activityMainBinding.rv15Days.setAdapter(weather15Days);

                        runOnUiThread(() -> {
                            activityMainBinding.rlLoading.setVisibility(View.GONE);
                            activityMainBinding.svMain.setVisibility(View.VISIBLE);

                            mAppUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

                            mAppUpdateManager.registerListener(installStateUpdatedListener);

                            mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

                                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)) {

                                    try {
                                        mAppUpdateManager.startUpdateFlowForResult(
                                                appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/, MainActivity.this, RC_APP_UPDATE);
                                        Log.e("LLL_Update_App: ", "Update available");
                                    } catch (IntentSender.SendIntentException e) {
                                        e.printStackTrace();
                                        Log.e("LLL_Update_App: ", e.getMessage());
                                    }

                                } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                    //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                                    popupSnackbarForCompleteUpdate();
                                } else {
                                    Log.e("LLL_Update_App: ", "checkForAppUpdateAvailability: something else");
                                }
                            });
                        });

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("LLL_Days15_Error: ", anError.getErrorBody());
                    }
                });
        return isDay15Called;
    }

    private void get24HoursData() {

        ArrayList<Hours24ResponseItem> hours24ResponseItems = new ArrayList<>();
        boolean isMatrix = true;
        if (!Preference.getUnit(MainActivity.this).equalsIgnoreCase("Metric")) {
            isMatrix = false;
        }
        AndroidNetworking.get("http://api.accuweather.com/forecasts/v1/hourly/24hour/" + Const.KEY + ".json")
                .addQueryParameter("apikey", "srRLeAmTroxPinDG8Aus3Ikl6tLGJd94")
                .addQueryParameter("language", "en-gb")
                .addQueryParameter("details", "true")
                .addQueryParameter("metric", String.valueOf(isMatrix))
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hours24ResponseItems.clear();
                        try {
                            if (response.length() > 0) {

                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    Hours24ResponseItem newResponse = new Gson().fromJson(jsonObject.toString(), Hours24ResponseItem.class);
                                    hours24ResponseItems.add(newResponse);
                                }
                                hourlyDataAdapter = new HourlyDataAdapter(hours24ResponseItems, MainActivity.this);
                                activityMainBinding.rvHourly.setAdapter(hourlyDataAdapter);

                                new getDays15().execute();
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

                            Calendar calendar = Calendar.getInstance();
                            new AlarmReceiver().setRepeatAlarm(getApplicationContext(), 1001, calendar);
                            address = getAddress(Double.parseDouble(Preference.getLatitude(MainActivity.this)),
                                    Double.parseDouble(Preference.getLongitude(MainActivity.this)));

                            Log.e("LLLL_area: ", area);
                            Log.e("LLLL_area: ", address);

                            ArrayList<AddLocationData> addLocationDataArrayList;
                            if (!Preference.getDefaultLocationListInfo(getApplicationContext()).equals("")) {
                                addLocationDataArrayList = new Gson().fromJson(Preference.getDefaultLocationListInfo(getApplicationContext()), type);
                            } else {
                                addLocationDataArrayList = new ArrayList<>();
                            }
                            AddLocationData addLocationData = new AddLocationData();
                            addLocationData.setKey(mainKey);
                            addLocationData.setCity(city);
                            addLocationData.setCountry(country);
                            addLocationData.setArea(area);
                            addLocationData.setDaynight(0);
                            if (addLocationDataArrayList.size() > 0) {
                                addLocationDataArrayList.remove(0);
                                addLocationDataArrayList.add(0, addLocationData);
                            } else {
                                addLocationDataArrayList.add(addLocationData);
                            }
                            Preference.setDefaultLocationInfo(getApplicationContext(), new Gson().toJson(addLocationDataArrayList));

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

                                activityMainBinding.setCurrentdata(newResponse);
                                activityMainBinding.setCityName(address);


                                String WeatherIcon = String.valueOf(newResponse.getWeatherIcon());

                                if (WeatherIcon.equals("6") || WeatherIcon.equals("7") || WeatherIcon.equals("8") || WeatherIcon.equals("12") || WeatherIcon.equals("13") || WeatherIcon.equals("14") || WeatherIcon.equals("15") || WeatherIcon.equals("16") || WeatherIcon.equals("17") ||
                                        WeatherIcon.equals("18") || WeatherIcon.equals("19") || WeatherIcon.equals("20") || WeatherIcon.equals("21") || WeatherIcon.equals("26") || WeatherIcon.equals("38") || WeatherIcon.equals("39") || WeatherIcon.equals("40") || WeatherIcon.equals("41") || WeatherIcon.equals("42")) {
                                    activityMainBinding.imgIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_monsson_1));
                                    activityMainBinding.rlBg.setBackground(getResources().getDrawable(R.drawable.ic_monsson_bg));
                                } else if (WeatherIcon.equals("11") || WeatherIcon.equals("22") || WeatherIcon.equals("23") || WeatherIcon.equals("24") || WeatherIcon.equals("25") || WeatherIcon.equals("26") || WeatherIcon.equals("29") || WeatherIcon.equals("32") || WeatherIcon.equals("42") || WeatherIcon.equals("43") ||
                                        WeatherIcon.equals("44") || WeatherIcon.equals("31") || WeatherIcon.equals("25") || WeatherIcon.equals("29") || WeatherIcon.equals("24") || WeatherIcon.equals("22") || WeatherIcon.equals("23")) {
                                    activityMainBinding.imgIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_winter));
                                    activityMainBinding.rlBg.setBackground(getResources().getDrawable(R.drawable.ic_winter_bg));
                                } else {
                                    activityMainBinding.imgIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_summar));
                                    activityMainBinding.rlBg.setBackground(getResources().getDrawable(R.drawable.ic_sunny_bg));
                                }

                            }

                            if (Preference.getValidAlarm(MainActivity.this).equals("")) {
                                updateAllWidgets(MainActivity.this, new Intent(MainActivity.this, WeatherWidget.class));
                                Preference.setValidAlarm(MainActivity.this, "1");
                            }


                            new getHours24Data().execute();

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

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());

        activityMainBinding.chart1.centerViewToAnimated(e.getX(), e.getY(), activityMainBinding.chart1.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    private void setChart() {

        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < dailyForecastsItemList.size(); i++) {
            float val = (float) dailyForecastsItemList.get(i).getTemperature().getMaximum().getValue();
            float val2 = (float) dailyForecastsItemList.get(i).getTemperature().getMinimum().getValue();
            values1.add(new Entry(i, val));
            values2.add(new Entry(i, val2));
        }

        LineDataSet set1, set2;

        if (activityMainBinding.chart1.getData() != null &&
                activityMainBinding.chart1.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) activityMainBinding.chart1.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) activityMainBinding.chart1.getData().getDataSetByIndex(1);

            set1.setValues(values1);
            set2.setValues(values2);

            activityMainBinding.chart1.getData().notifyDataChanged();
            activityMainBinding.chart1.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "Maximum Temperature");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(Color.rgb(255, 111, 3));
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(Color.rgb(255, 111, 3));
            set1.setHighLightColor(Color.rgb(255, 111, 3));
            set1.setDrawCircleHole(false);

            // create a dataset and give it a type
            set2 = new LineDataSet(values2, "Minimum Temperature");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(Color.rgb(72, 181, 51));
            set2.setCircleColor(Color.WHITE);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.rgb(72, 181, 51));
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(72, 181, 51));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            // create a data object with the data sets
            LineData data = new LineData(set1, set2);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            final ArrayList<String> xAxisLabel = new ArrayList<>();
            for (int i = 0; i < dailyForecastsItemList.size(); i++) {
                DateTime dt = new DateTime(dailyForecastsItemList.get(i).getDate());
                String date = weekOfDay[dt.getDayOfWeek() - 1] + " " + dt.getDayOfMonth() + "," + " " + dt.getYear();
                String[] date1 = date.split(" ");
                xAxisLabel.add("" + date1[0].substring(0, 1).toUpperCase() + "" + date1[0].substring(1, 3).toLowerCase());
            }
            activityMainBinding.chart1.getXAxis().setTextSize(11f);
            activityMainBinding.chart1.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
            // set data
            activityMainBinding.chart1.setData(data);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 11:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        address = data.getStringExtra("name");
                        mainKey = data.getStringExtra("key");
                        Const.KEY = data.getStringExtra("key");
                        new getCurrentData().execute();
                    }
                }
                break;
            case 12:
                if (resultCode == RESULT_OK) {
                    ArrayList<AddLocationData> addLocationDataArrayList;
                    if (!Preference.getKey(MainActivity.this).equals("")) {
                        Const.KEY = Preference.getKey(MainActivity.this);
                        new getCurrentData().execute();
                        addLocationDataArrayList = new Gson().fromJson(Preference.getDefaultLocationListInfo(getApplicationContext()), type);
                        AddLocationData addLocationData = new AddLocationData();
                        addLocationData.setKey(Const.KEY);
                        activityMainBinding.setMatrixType(Preference.getUnit(MainActivity.this));
                        if (addLocationDataArrayList.contains(addLocationData)) {
                            if (addLocationDataArrayList.indexOf(addLocationData) != -1) {
                                int pos = addLocationDataArrayList.indexOf(addLocationData);
                                address = addLocationDataArrayList.get(pos).getCity() + ", " + addLocationDataArrayList.get(pos).getCountry();
                                Const.KEY = Preference.getKey(MainActivity.this);
                                new getCurrentData().execute();
                            }
                        }
                    }
                }
                break;
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

    private final class getKey extends AsyncTask<Void, Void, String> {

        double latitude, longitude;

        public getKey(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected String doInBackground(Void... params) {
            Const.KEY = getKey(latitude, longitude);
            Preference.setKey(MainActivity.this, Const.KEY);
            return Const.KEY;
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private final class getHours24Data extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            get24HoursData();
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private final class getDays15 extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            isDay15Called = getDay15Data(Const.KEY);
            if (isDay15Called)
                return "Executed";
            else
                return "";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    public class MyClickHandlers {
        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }

        public void onSettingBtnClicked(View view) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivityForResult(intent, 12);
        }

        public void onSearchBtnClick(View view) {
            Intent intent = new Intent(MainActivity.this, LocationActivity.class);
            startActivityForResult(intent, 11);
        }
    }
}