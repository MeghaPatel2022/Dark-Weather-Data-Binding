package com.darksky.weather.today.weatherforecast.mainactivity.model.currentdata;

import com.google.gson.annotations.SerializedName;

public class CurrentDataResponseItem {

    @SerializedName("Wind")
    private Wind wind;

    @SerializedName("Temperature")
    private Temperature temperature;

    @SerializedName("PressureTendency")
    private PressureTendency pressureTendency;

    @SerializedName("ObstructionsToVisibility")
    private String obstructionsToVisibility;

    @SerializedName("Ceiling")
    private Ceiling ceiling;

    @SerializedName("RealFeelTemperatureShade")
    private RealFeelTemperatureShade realFeelTemperatureShade;

    @SerializedName("EpochTime")
    private int epochTime;

    @SerializedName("RealFeelTemperature")
    private RealFeelTemperature realFeelTemperature;

    @SerializedName("PrecipitationType")
    private Object precipitationType;

    @SerializedName("HasPrecipitation")
    private boolean hasPrecipitation;

    @SerializedName("RelativeHumidity")
    private int relativeHumidity;

    @SerializedName("LocalObservationDateTime")
    private String localObservationDateTime;

    @SerializedName("UVIndexText")
    private String uVIndexText;

    @SerializedName("WeatherText")
    private String weatherText;

    @SerializedName("CloudCover")
    private int cloudCover;

    @SerializedName("WindGust")
    private WindGust windGust;

    @SerializedName("UVIndex")
    private int uVIndex;

    @SerializedName("WeatherIcon")
    private int weatherIcon;

    @SerializedName("DewPoint")
    private DewPoint dewPoint;

    @SerializedName("Pressure")
    private Pressure pressure;

    @SerializedName("IsDayTime")
    private boolean isDayTime;

    @SerializedName("IndoorRelativeHumidity")
    private int indoorRelativeHumidity;

    @SerializedName("ApparentTemperature")
    private ApparentTemperature apparentTemperature;

    @SerializedName("Visibility")
    private Visibility visibility;

    @SerializedName("WindChillTemperature")
    private WindChillTemperature windChillTemperature;

    @SerializedName("TemperatureSummary")
    private TemperatureSummary temperatureSummary;

    @SerializedName("Link")
    private String link;

    @SerializedName("MobileLink")
    private String mobileLink;

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public PressureTendency getPressureTendency() {
        return pressureTendency;
    }

    public void setPressureTendency(PressureTendency pressureTendency) {
        this.pressureTendency = pressureTendency;
    }

    public String getObstructionsToVisibility() {
        return obstructionsToVisibility;
    }

    public void setObstructionsToVisibility(String obstructionsToVisibility) {
        this.obstructionsToVisibility = obstructionsToVisibility;
    }

    public Ceiling getCeiling() {
        return ceiling;
    }

    public void setCeiling(Ceiling ceiling) {
        this.ceiling = ceiling;
    }

    public RealFeelTemperatureShade getRealFeelTemperatureShade() {
        return realFeelTemperatureShade;
    }

    public void setRealFeelTemperatureShade(RealFeelTemperatureShade realFeelTemperatureShade) {
        this.realFeelTemperatureShade = realFeelTemperatureShade;
    }

    public int getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(int epochTime) {
        this.epochTime = epochTime;
    }

    public RealFeelTemperature getRealFeelTemperature() {
        return realFeelTemperature;
    }

    public void setRealFeelTemperature(RealFeelTemperature realFeelTemperature) {
        this.realFeelTemperature = realFeelTemperature;
    }

    public Object getPrecipitationType() {
        return precipitationType;
    }

    public void setPrecipitationType(Object precipitationType) {
        this.precipitationType = precipitationType;
    }

    public boolean isHasPrecipitation() {
        return hasPrecipitation;
    }

    public void setHasPrecipitation(boolean hasPrecipitation) {
        this.hasPrecipitation = hasPrecipitation;
    }

    public int getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(int relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public String getLocalObservationDateTime() {
        return localObservationDateTime;
    }

    public void setLocalObservationDateTime(String localObservationDateTime) {
        this.localObservationDateTime = localObservationDateTime;
    }

    public String getUVIndexText() {
        return uVIndexText;
    }

    public void setUVIndexText(String uVIndexText) {
        this.uVIndexText = uVIndexText;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    public WindGust getWindGust() {
        return windGust;
    }

    public void setWindGust(WindGust windGust) {
        this.windGust = windGust;
    }

    public int getUVIndex() {
        return uVIndex;
    }

    public void setUVIndex(int uVIndex) {
        this.uVIndex = uVIndex;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public DewPoint getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(DewPoint dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Pressure getPressure() {
        return pressure;
    }

    public void setPressure(Pressure pressure) {
        this.pressure = pressure;
    }

    public boolean isIsDayTime() {
        return isDayTime;
    }

    public void setIsDayTime(boolean isDayTime) {
        this.isDayTime = isDayTime;
    }

    public int getIndoorRelativeHumidity() {
        return indoorRelativeHumidity;
    }

    public void setIndoorRelativeHumidity(int indoorRelativeHumidity) {
        this.indoorRelativeHumidity = indoorRelativeHumidity;
    }

    public ApparentTemperature getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(ApparentTemperature apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public WindChillTemperature getWindChillTemperature() {
        return windChillTemperature;
    }

    public void setWindChillTemperature(WindChillTemperature windChillTemperature) {
        this.windChillTemperature = windChillTemperature;
    }

    public TemperatureSummary getTemperatureSummary() {
        return temperatureSummary;
    }

    public void setTemperatureSummary(TemperatureSummary temperatureSummary) {
        this.temperatureSummary = temperatureSummary;
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
                "CurrentDataResponseItem{" +
                        "wind = '" + wind + '\'' +
                        ",temperature = '" + temperature + '\'' +
                        ",pressureTendency = '" + pressureTendency + '\'' +
                        ",obstructionsToVisibility = '" + obstructionsToVisibility + '\'' +
                        ",ceiling = '" + ceiling + '\'' +
                        ",realFeelTemperatureShade = '" + realFeelTemperatureShade + '\'' +
                        ",epochTime = '" + epochTime + '\'' +
                        ",realFeelTemperature = '" + realFeelTemperature + '\'' +
                        ",precipitationType = '" + precipitationType + '\'' +
                        ",hasPrecipitation = '" + hasPrecipitation + '\'' +
                        ",relativeHumidity = '" + relativeHumidity + '\'' +
                        ",localObservationDateTime = '" + localObservationDateTime + '\'' +
                        ",uVIndexText = '" + uVIndexText + '\'' +
                        ",weatherText = '" + weatherText + '\'' +
                        ",cloudCover = '" + cloudCover + '\'' +
                        ",windGust = '" + windGust + '\'' +
                        ",uVIndex = '" + uVIndex + '\'' +
                        ",weatherIcon = '" + weatherIcon + '\'' +
                        ",dewPoint = '" + dewPoint + '\'' +
                        ",pressure = '" + pressure + '\'' +
                        ",isDayTime = '" + isDayTime + '\'' +
                        ",indoorRelativeHumidity = '" + indoorRelativeHumidity + '\'' +
                        ",apparentTemperature = '" + apparentTemperature + '\'' +
                        ",visibility = '" + visibility + '\'' +
                        ",temperatureSummary = '" + temperatureSummary + '\'' +
                        ",windChillTemperature = '" + windChillTemperature + '\'' +
                        ",link = '" + link + '\'' +
                        ",mobileLink = '" + mobileLink + '\'' +
                        "}";
    }
}