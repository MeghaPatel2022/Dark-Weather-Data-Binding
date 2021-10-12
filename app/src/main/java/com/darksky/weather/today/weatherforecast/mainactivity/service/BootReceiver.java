/*
 * Copyright 2015 Blanyal D'Souza.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.darksky.weather.today.weatherforecast.mainactivity.service;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.darksky.weather.today.weatherforecast.mainactivity.utils.WeatherWidget;

import java.util.Calendar;


public class BootReceiver extends BroadcastReceiver {

    private Calendar mCalendar;

    public static void updateAllWidgets(Context context, Intent intentWidget) {
        Log.e("LLL_Update: ", "Widget");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        intentWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context.getPackageName(), WeatherWidget.class.getName()));
        intentWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        context.sendBroadcast(intentWidget);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Calendar calendar = Calendar.getInstance();
            new AlarmReceiver().setRepeatAlarm(context, 99526463, calendar);

            updateAllWidgets(context, new Intent(context, WeatherWidget.class));

        }
    }

}