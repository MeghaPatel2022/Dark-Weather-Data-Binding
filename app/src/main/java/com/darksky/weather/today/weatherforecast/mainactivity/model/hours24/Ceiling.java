package com.darksky.weather.today.weatherforecast.mainactivity.model.hours24;

import com.google.gson.annotations.SerializedName;

public class Ceiling {

    @SerializedName("UnitType")
    private int unitType;

    @SerializedName("Value")
    private int value;

    @SerializedName("Unit")
    private String unit;

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return
                "Ceiling{" +
                        "unitType = '" + unitType + '\'' +
                        ",value = '" + value + '\'' +
                        ",unit = '" + unit + '\'' +
                        "}";
    }
}