package com.darksky.weather.today.weatherforecast.mainactivity.model.days15;

import com.google.gson.annotations.SerializedName;

public class RealFeelTemperature {

    @SerializedName("Minimum")
    private Minimum minimum;

    @SerializedName("Maximum")
    private Maximum maximum;

    public Minimum getMinimum() {
        return minimum;
    }

    public void setMinimum(Minimum minimum) {
        this.minimum = minimum;
    }

    public Maximum getMaximum() {
        return maximum;
    }

    public void setMaximum(Maximum maximum) {
        this.maximum = maximum;
    }

    @Override
    public String toString() {
        return
                "RealFeelTemperature{" +
                        "minimum = '" + minimum + '\'' +
                        ",maximum = '" + maximum + '\'' +
                        "}";
    }
}