package com.darksky.weather.today.weatherforecast.mainactivity.model.days15;

import com.google.gson.annotations.SerializedName;

public class Moon {

    @SerializedName("EpochSet")
    private int epochSet;

    @SerializedName("Set")
    private String set;

    @SerializedName("Phase")
    private String phase;

    @SerializedName("EpochRise")
    private int epochRise;

    @SerializedName("Age")
    private int age;

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

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public int getEpochRise() {
        return epochRise;
    }

    public void setEpochRise(int epochRise) {
        this.epochRise = epochRise;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
                "Moon{" +
                        "epochSet = '" + epochSet + '\'' +
                        ",set = '" + set + '\'' +
                        ",phase = '" + phase + '\'' +
                        ",epochRise = '" + epochRise + '\'' +
                        ",age = '" + age + '\'' +
                        ",rise = '" + rise + '\'' +
                        "}";
    }
}