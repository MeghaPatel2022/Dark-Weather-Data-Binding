package com.darksky.weather.today.weatherforecast.mainactivity.model.days15;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyForecastsItem {

    @SerializedName("Temperature")
    private Temperature temperature;

    @SerializedName("Night")
    private Night night;

    @SerializedName("EpochDate")
    private int epochDate;

    @SerializedName("Moon")
    private Moon moon;

    @SerializedName("DegreeDaySummary")
    private DegreeDaySummary degreeDaySummary;

    @SerializedName("RealFeelTemperatureShade")
    private RealFeelTemperatureShade realFeelTemperatureShade;

    @SerializedName("AirAndPollen")
    private List<AirAndPollenItem> airAndPollen;

    @SerializedName("HoursOfSun")
    private double hoursOfSun;

    @SerializedName("Sun")
    private Sun sun;

    @SerializedName("Sources")
    private List<String> sources;

    @SerializedName("Date")
    private String date;

    @SerializedName("RealFeelTemperature")
    private RealFeelTemperature realFeelTemperature;

    @SerializedName("Day")
    private Day day;

    @SerializedName("Link")
    private String link;

    @SerializedName("MobileLink")
    private String mobileLink;

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Night getNight() {
        return night;
    }

    public void setNight(Night night) {
        this.night = night;
    }

    public int getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(int epochDate) {
        this.epochDate = epochDate;
    }

    public Moon getMoon() {
        return moon;
    }

    public void setMoon(Moon moon) {
        this.moon = moon;
    }

    public DegreeDaySummary getDegreeDaySummary() {
        return degreeDaySummary;
    }

    public void setDegreeDaySummary(DegreeDaySummary degreeDaySummary) {
        this.degreeDaySummary = degreeDaySummary;
    }

    public RealFeelTemperatureShade getRealFeelTemperatureShade() {
        return realFeelTemperatureShade;
    }

    public void setRealFeelTemperatureShade(RealFeelTemperatureShade realFeelTemperatureShade) {
        this.realFeelTemperatureShade = realFeelTemperatureShade;
    }

    public List<AirAndPollenItem> getAirAndPollen() {
        return airAndPollen;
    }

    public void setAirAndPollen(List<AirAndPollenItem> airAndPollen) {
        this.airAndPollen = airAndPollen;
    }

    public double getHoursOfSun() {
        return hoursOfSun;
    }

    public void setHoursOfSun(double hoursOfSun) {
        this.hoursOfSun = hoursOfSun;
    }

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RealFeelTemperature getRealFeelTemperature() {
        return realFeelTemperature;
    }

    public void setRealFeelTemperature(RealFeelTemperature realFeelTemperature) {
        this.realFeelTemperature = realFeelTemperature;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMobileLink() {
        return mobileLink;
    }

    public void setMobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
    }

    @Override
    public String toString() {
        return
                "DailyForecastsItem{" +
                        "temperature = '" + temperature + '\'' +
                        ",night = '" + night + '\'' +
                        ",epochDate = '" + epochDate + '\'' +
                        ",moon = '" + moon + '\'' +
                        ",degreeDaySummary = '" + degreeDaySummary + '\'' +
                        ",realFeelTemperatureShade = '" + realFeelTemperatureShade + '\'' +
                        ",airAndPollen = '" + airAndPollen + '\'' +
                        ",hoursOfSun = '" + hoursOfSun + '\'' +
                        ",sun = '" + sun + '\'' +
                        ",sources = '" + sources + '\'' +
                        ",date = '" + date + '\'' +
                        ",realFeelTemperature = '" + realFeelTemperature + '\'' +
                        ",day = '" + day + '\'' +
                        ",link = '" + link + '\'' +
                        ",mobileLink = '" + mobileLink + '\'' +
                        "}";
    }
}