package com.darksky.weather.today.weatherforecast.mainactivity.model.days15;

import com.google.gson.annotations.SerializedName;

public class Sun {

    @SerializedName("EpochSet")
    private int epochSet;

    @SerializedName("Set")
    private String set;

    @SerializedName("EpochRise")
    private int epochRise;

    @SerializedName("Rise")
    private String rise;

    public int getEpochSet() {
        return epochSet;
    }

    public void setEpochSet(int epochSet) {
        this.epochSet = epochSet;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public int getEpochRise() {
        return epochRise;
    }

    public void setEpochRise(int epochRise) {
        this.epochRise = epochRise;
    }

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    @Override
    public String toString() {
        return
                "Sun{" +
                        "epochSet = '" + epochSet + '\'' +
                        ",set = '" + set + '\'' +
                        ",epochRise = '" + epochRise + '\'' +
                        ",rise = '" + rise + '\'' +
                        "}";
    }
}