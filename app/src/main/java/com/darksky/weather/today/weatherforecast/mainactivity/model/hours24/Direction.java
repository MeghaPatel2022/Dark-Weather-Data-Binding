package com.darksky.weather.today.weatherforecast.mainactivity.model.hours24;

import com.google.gson.annotations.SerializedName;

public class Direction {

    @SerializedName("English")
    private String english;

    @SerializedName("Degrees")
    private int degrees;

    @SerializedName("Localized")
    private String localized;

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public int getDegrees() {
        return degrees;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    public String getLocalized() {
        return localized;
    }

    public void setLocalized(String localized) {
        this.localized = localized;
    }

    @Override
    public String toString() {
        return
                "Direction{" +
                        "english = '" + english + '\'' +
                        ",degrees = '" + degrees + '\'' +
                        ",localized = '" + localized + '\'' +
                        "}";
    }
}