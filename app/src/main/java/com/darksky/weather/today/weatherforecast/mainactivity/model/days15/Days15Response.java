package com.darksky.weather.today.weatherforecast.mainactivity.model.days15;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Days15Response {

    @SerializedName("Headline")
    private Headline headline;

    @SerializedName("DailyForecasts")
    private List<DailyForecastsItem> dailyForecasts;

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<DailyForecastsItem> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<DailyForecastsItem> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    @Override
    public String toString() {
        return
                "Days15Response{" +
                        "headline = '" + headline + '\'' +
                        ",dailyForecasts = '" + dailyForecasts + '\'' +
                        "}";
    }
}