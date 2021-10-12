package com.darksky.weather.today.weatherforecast.mainactivity.model.days15;

import com.google.gson.annotations.SerializedName;

public class Headline {

    @SerializedName("Category")
    private String category;

    @SerializedName("EndEpochDate")
    private int endEpochDate;

    @SerializedName("EffectiveEpochDate")
    private int effectiveEpochDate;

    @SerializedName("Severity")
    private int severity;

    @SerializedName("Text")
    private String text;

    @SerializedName("EndDate")
    private String endDate;

    @SerializedName("Link")
    private String link;

    @SerializedName("EffectiveDate")
    private String effectiveDate;

    @SerializedName("MobileLink")
    private String mobileLink;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getEndEpochDate() {
        return endEpochDate;
    }

    public void setEndEpochDate(int endEpochDate) {
        this.endEpochDate = endEpochDate;
    }

    public int getEffectiveEpochDate() {
        return effectiveEpochDate;
    }

    public void setEffectiveEpochDate(int effectiveEpochDate) {
        this.effectiveEpochDate = effectiveEpochDate;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
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
                "Headline{" +
                        "category = '" + category + '\'' +
                        ",endEpochDate = '" + endEpochDate + '\'' +
                        ",effectiveEpochDate = '" + effectiveEpochDate + '\'' +
                        ",severity = '" + severity + '\'' +
                        ",text = '" + text + '\'' +
                        ",endDate = '" + endDate + '\'' +
                        ",link = '" + link + '\'' +
                        ",effectiveDate = '" + effectiveDate + '\'' +
                        ",mobileLink = '" + mobileLink + '\'' +
                        "}";
    }
}