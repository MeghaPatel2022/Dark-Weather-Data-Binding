/*
The MIT License (MIT)

Copyright (c) 2013 Milenko Jovanovic

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/


package com.darksky.weather.today.weatherforecast.mainactivity.service;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.darksky.weather.today.weatherforecast.mainactivity.utils.WeatherWidget;


public class WidgetReceiver extends BroadcastReceiver {

    public static void updateAllWidgets(Context context, Intent intentWidget) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        intentWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context.getPackageName(), WeatherWidget.class.getName()));
        intentWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        context.sendBroadcast(intentWidget);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        updateAllWidgets(context, new Intent(context, WeatherWidget.class));
    }


}
