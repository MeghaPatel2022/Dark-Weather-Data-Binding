package com.darksky.weather.today.weatherforecast.mainactivity.model.days15;

import com.google.gson.annotations.SerializedName;

public class Minimum {

    @SerializedName("UnitType")
    private int unitType;

    @SerializedName("Value")
    private double value;

    @SerializedName("Unit")
    private String unit;

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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
                "Minimum{" +
                        "unitType = '" + unitType + '\'' +
                        ",value = '" + value + '\'' +
                        ",unit = '" + unit + '\'' +
                        "}";
    }
}