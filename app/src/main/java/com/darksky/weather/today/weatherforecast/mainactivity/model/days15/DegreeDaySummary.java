package com.darksky.weather.today.weatherforecast.mainactivity.model.days15;

import com.google.gson.annotations.SerializedName;

public class DegreeDaySummary {

    @SerializedName("Cooling")
    private Cooling cooling;

    @SerializedName("Heating")
    private Heating heating;

    public Cooling getCooling() {
        return cooling;
    }

    public void setCooling(Cooling cooling) {
        this.cooling = cooling;
    }

    public Heating getHeating() {
        return heating;
    }

    public void setHeating(Heating heating) {
        this.heating = heating;
    }

    @Override
    public String toString() {
        return
                "DegreeDaySummary{" +
                        "cooling = '" + cooling + '\'' +
                        ",heating = '" + heating + '\'' +
                        "}";
    }
}