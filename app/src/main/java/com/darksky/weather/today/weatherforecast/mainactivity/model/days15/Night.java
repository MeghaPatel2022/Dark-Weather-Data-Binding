package com.darksky.weather.today.weatherforecast.mainactivity.model.days15;


import com.darksky.weather.today.weatherforecast.mainactivity.model.hours24.Ice;
import com.darksky.weather.today.weatherforecast.mainactivity.model.hours24.Rain;
import com.darksky.weather.today.weatherforecast.mainactivity.model.hours24.Snow;
import com.darksky.weather.today.weatherforecast.mainactivity.model.hours24.Wind;
import com.google.gson.annotations.SerializedName;

public class Night {

    @SerializedName("RainProbability")
    private int rainProbability;

    @SerializedName("Wind")
    private Wind wind;

    @SerializedName("SnowProbability")
    private int snowProbability;

    @SerializedName("Snow")
    private Snow snow;

    @SerializedName("ShortPhrase")
    private String shortPhrase;

    @SerializedName("Ice")
    private Ice ice;

    @SerializedName("HoursOfRain")
    private double hoursOfRain;

    @SerializedName("HoursOfIce")
    private double hoursOfIce;

    @SerializedName("Rain")
    private Rain rain;

    @SerializedName("PrecipitationProbability")
    private int precipitationProbability;

    @SerializedName("HasPrecipitation")
    private boolean hasPrecipitation;

    @SerializedName("ThunderstormProbability")
    private int thunderstormProbability;

    @SerializedName("IceProbability")
    private int iceProbability;

    @SerializedName("IconPhrase")
    private String iconPhrase;

    @SerializedName("CloudCover")
    private int cloudCover;

    @SerializedName("LongPhrase")
    private String longPhrase;

    @SerializedName("Icon")
    private int icon;

    @SerializedName("WindGust")
    private WindGust windGust;

    @SerializedName("HoursOfSnow")
    private int hoursOfSnow;

    @SerializedName("HoursOfPrecipitation")
    private double hoursOfPrecipitation;

    @SerializedName("PrecipitationIntensity")
    private String precipitationIntensity;

    @SerializedName("PrecipitationType")
    private String precipitationType;

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

    public String getShortPhrase() {
        return shortPhrase;
    }

    public void setShortPhrase(String shortPhrase) {
        this.shortPhrase = shortPhrase;
    }

    public Ice getIce() {
        return ice;
    }

    public void setIce(Ice ice) {
        this.ice = ice;
    }

    public double getHoursOfRain() {
        return hoursOfRain;
    }

    public void setHoursOfRain(double hoursOfRain) {
        this.hoursOfRain = hoursOfRain;
    }

    public double getHoursOfIce() {
        return hoursOfIce;
    }

    public void setHoursOfIce(double hoursOfIce) {
        this.hoursOfIce = hoursOfIce;
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

    public int getThunderstormProbability() {
        return thunderstormProbability;
    }

    public void setThunderstormProbability(int thunderstormProbability) {
        this.thunderstormProbability = thunderstormProbability;
    }

    public int getIceProbability() {
        return iceProbability;
    }

    public void setIceProbability(int iceProbability) {
        this.iceProbability = iceProbability;
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

    public String getLongPhrase() {
        return longPhrase;
    }

    public void setLongPhrase(String longPhrase) {
        this.longPhrase = longPhrase;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public WindGust getWindGust() {
        return windGust;
    }

    public void setWindGust(WindGust windGust) {
        this.windGust = windGust;
    }

    public int getHoursOfSnow() {
        return hoursOfSnow;
    }

    public void setHoursOfSnow(int hoursOfSnow) {
        this.hoursOfSnow = hoursOfSnow;
    }

    public double getHoursOfPrecipitation() {
        return hoursOfPrecipitation;
    }

    public void setHoursOfPrecipitation(double hoursOfPrecipitation) {
        this.hoursOfPrecipitation = hoursOfPrecipitation;
    }

    public String getPrecipitationIntensity() {
        return precipitationIntensity;
    }

    public void setPrecipitationIntensity(String precipitationIntensity) {
        this.precipitationIntensity = precipitationIntensity;
    }

    public String getPrecipitationType() {
        return precipitationType;
    }

    public void setPrecipitationType(String precipitationType) {
        this.precipitationType = precipitationType;
    }

    @Override
    public String toString() {
        return
                "Night{" +
                        "rainProbability = '" + rainProbability + '\'' +
                        ",wind = '" + wind + '\'' +
                        ",snowProbability = '" + snowProbability + '\'' +
                        ",snow = '" + snow + '\'' +
                        ",shortPhrase = '" + shortPhrase + '\'' +
                        ",ice = '" + ice + '\'' +
                        ",hoursOfRain = '" + hoursOfRain + '\'' +
                        ",hoursOfIce = '" + hoursOfIce + '\'' +
                        ",rain = '" + rain + '\'' +
                        ",precipitationProbability = '" + precipitationProbability + '\'' +
                        ",hasPrecipitation = '" + hasPrecipitation + '\'' +
                        ",thunderstormProbability = '" + thunderstormProbability + '\'' +
                        ",iceProbability = '" + iceProbability + '\'' +
                        ",iconPhrase = '" + iconPhrase + '\'' +
                        ",cloudCover = '" + cloudCover + '\'' +
                        ",longPhrase = '" + longPhrase + '\'' +
                        ",icon = '" + icon + '\'' +
                        ",windGust = '" + windGust + '\'' +
                        ",hoursOfSnow = '" + hoursOfSnow + '\'' +
                        ",hoursOfPrecipitation = '" + hoursOfPrecipitation + '\'' +
                        ",precipitationIntensity = '" + precipitationIntensity + '\'' +
                        ",precipitationType = '" + precipitationType + '\'' +
                        "}";
    }
}