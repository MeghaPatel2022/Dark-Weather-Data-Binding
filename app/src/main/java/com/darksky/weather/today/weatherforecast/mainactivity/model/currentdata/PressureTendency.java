package com.darksky.weather.today.weatherforecast.mainactivity.model.currentdata;

import com.google.gson.annotations.SerializedName;

public class PressureTendency {

    @SerializedName("Code")
    private String code;

    @SerializedName("LocalizedText")
    private String localizedText;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocalizedText() {
        return localizedText;
    }

    public void setLocalizedText(String localizedText) {
        this.localizedText = localizedText;
    }

    @Override
    public String toString() {
        return
                "PressureTendency{" +
                        "code = '" + code + '\'' +
                        ",localizedText = '" + localizedText + '\'' +
                        "}";
    }
}