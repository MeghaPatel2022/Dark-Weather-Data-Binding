package com.darksky.weather.today.weatherforecast.mainactivity.model.hours24;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("Speed")
    private Speed speed;

    @SerializedName("Direction")
    private Direction direction;

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return
                "Wind{" +
                        "speed = '" + speed + '\'' +
                        ",direction = '" + direction + '\'' +
                        "}";
    }
}