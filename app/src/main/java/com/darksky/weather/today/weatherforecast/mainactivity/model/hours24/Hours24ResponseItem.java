package com.darksky.weather.today.weatherforecast.mainactivity.model.hours24;

import com.google.gson.annotations.SerializedName;

public class Hours24ResponseItem {

    @SerializedName("RainProbability")
    private int rainProbability;

    @SerializedName("Wind")
    private Wind wind;

    @SerializedName("Temperature")
    private Temperature temperature;

    @SerializedName("SnowProbability")
    private int snowProbability;

    @SerializedName("Snow")
    private Snow snow;

    @SerializedName("Ceiling")
    private Ceiling ceiling;

    @SerializedName("DateTime")
    private String dateTime;

    @SerializedName("RealFeelTemperature")
    private RealFeelTemperature realFeelTemperature;

    @SerializedName("Rain")
    private Rain rain;

    @SerializedName("PrecipitationProbability")
    private int precipitationProbability;

    @SerializedName("HasPrecipitation")
    private boolean hasPrecipitation;

    @SerializedName("RelativeHumidity")
    private int relativeHumidity;

    @SerializedName("UVIndexText")
    private String uVIndexText;

    @SerializedName("IconPhrase")
    private String iconPhrase;

    @SerializedName("CloudCover")
    private int cloudCover;

    @SerializedName("WindGust")
    private WindGust windGust;

    @SerializedName("UVIndex")
    private int uVIndex;

    @SerializedName("WeatherIcon")
    private int weatherIcon;

    @SerializedName("Ice")
    private Ice ice;

    @SerializedName("DewPoint")
    private DewPoint dewPoint;

    @SerializedName("IndoorRelativeHumidity")
    private int indoorRelativeHumidity;

    @SerializedName("IceProbability")
    private int iceProbability;

    @SerializedName("EpochDateTime")
    private int epochDateTime;

    @SerializedName("Visibility")
    private Visibility visibility;

    @SerializedName("IsDaylight")
    private boolean isDaylight;

    @SerializedName("Link")
    private String link;

    @SerializedName("MobileLink")
    private String mobileLink;

    public int getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(int rainProbability) {
        this.rainProbability = rainProbability;
    }

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

    public int getSnowProbability() {
        return snowProbability;
    }

    public void setSnowProbability(int snowProbability) {
        this.snowProbability = snowProbability;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public Ceiling getCeiling() {
        return ceiling;
    }

    public void setCeiling(Ceiling ceiling) {
        this.ceiling = ceiling;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public RealFeelTemperature getRealFeelTemperature() {
        return realFeelTemperature;
    }

    public void setRealFeelTemperature(RealFeelTemperature realFeelTemperature) {
        this.realFeelTemperature = realFeelTemperature;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
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

    public String getUVIndexText() {
        return uVIndexText;
    }

    public void setUVIndexText(String uVIndexText) {
        this.uVIndexText = uVIndexText;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
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

    public Ice getIce() {
        return ice;
    }

    public void setIce(Ice ice) {
        this.ice = ice;
    }

    public DewPoint getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(DewPoint dewPoint) {
        this.dewPoint = dewPoint;
    }

    public int getIndoorRelativeHumidity() {
        return indoorRelativeHumidity;
    }

    public void setIndoorRelativeHumidity(int indoorRelativeHumidity) {
        this.indoorRelativeHumidity = indoorRelativeHumidity;
    }

    public int getIceProbability() {
        return iceProbability;
    }

    public void setIceProbability(int iceProbability) {
        this.iceProbability = iceProbability;
    }

    public int getEpochDateTime() {
        return epochDateTime;
    }

    public void setEpochDateTime(int epochDateTime) {
        this.epochDateTime = epochDateTime;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public boolean isIsDaylight() {
        return isDaylight;
    }

    public void setIsDaylight(boolean isDaylight) {
        this.isDaylight = isDaylight;
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
                "Hours24ResponseItem{" +
                        "rainProbability = '" + rainProbability + '\'' +
                        ",wind = '" + wind + '\'' +
                        ",temperature = '" + temperature + '\'' +
                        ",snowProbability = '" + snowProbability + '\'' +
                        ",snow = '" + snow + '\'' +
                        ",ceiling = '" + ceiling + '\'' +
                        ",dateTime = '" + dateTime + '\'' +
                        ",realFeelTemperature = '" + realFeelTemperature + '\'' +
                        ",rain = '" + rain + '\'' +
                        ",precipitationProbability = '" + precipitationProbability + '\'' +
                        ",hasPrecipitation = '" + hasPrecipitation + '\'' +
                        ",relativeHumidity = '" + relativeHumidity + '\'' +
                        ",uVIndexText = '" + uVIndexText + '\'' +
                        ",iconPhrase = '" + iconPhrase + '\'' +
                        ",cloudCover = '" + cloudCover + '\'' +
                        ",windGust = '" + windGust + '\'' +
                        ",uVIndex = '" + uVIndex + '\'' +
                        ",weatherIcon = '" + weatherIcon + '\'' +
                        ",ice = '" + ice + '\'' +
                        ",dewPoint = '" + dewPoint + '\'' +
                        ",indoorRelativeHumidity = '" + indoorRelativeHumidity + '\'' +
                        ",iceProbability = '" + iceProbability + '\'' +
                        ",epochDateTime = '" + epochDateTime + '\'' +
                        ",visibility = '" + visibility + '\'' +
                        ",isDaylight = '" + isDaylight + '\'' +
                        ",link = '" + link + '\'' +
                        ",mobileLink = '" + mobileLink + '\'' +
                        "}";
    }
}