package com.darksky.weather.today.weatherforecast.locationactivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.darksky.weather.today.weatherforecast.R;
import com.darksky.weather.today.weatherforecast.databinding.ActivityLocationBinding;
import com.darksky.weather.today.weatherforecast.locationactivity.adapter.LocationListAdapter;
import com.darksky.weather.today.weatherforecast.mainactivity.model.locationdata.AddLocationData;
import com.darksky.weather.today.weatherforecast.mainactivity.sharedpreference.Preference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    ActivityLocationBinding activityLocationBinding;
    LocationListAdapter locationListAdapter;

    Type type = new TypeToken<List<AddLocationData>>() {
    }.getType();

    ArrayList<AddLocationData> addLocationDataArrayList;
    MyClickHandlers myClickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        activityLocationBinding = DataBindingUtil.setContentView(LocationActivity.this, R.layout.activity_location);
        if (!Preference.getDefaultLocationListInfo(getApplicationContext()).equals("")) {
            addLocationDataArrayList = new Gson().fromJson(Preference.getDefaultLocationListInfo(getApplicationContext()), type);
           /* AddLocationData addLocationData = new AddLocationData();
            addLocationDataArrayList.add(0, addLocationData);*/
        } else {
            addLocationDataArrayList = new ArrayList<>();
            /*AddLocationData addLocationData = new AddLocationData();
            addLocationDataArrayList.add(0, addLocationData);*/
        }

        activityLocationBinding.rvLocation.setLayoutManager(new LinearLayoutManager(LocationActivity.this, RecyclerView.VERTICAL, false));
        locationListAdapter = new LocationListAdapter(LocationActivity.this, addLocationDataArrayList);
        activityLocationBinding.rvLocation.setAdapter(locationListAdapter);

        myClickHandlers = new MyClickHandlers(LocationActivity.this);
        activityLocationBinding.setOnBackClick(myClickHandlers);

    }

    public class MyClickHandlers {
        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }

        public void onBackBtnClicked(View view) {
            onBackPressed();
        }

    }
}