package com.darksky.weather.today.weatherforecast.mainactivity.model.days15;


import com.darksky.weather.today.weatherforecast.mainactivity.model.hours24.Direction;
import com.darksky.weather.today.weatherforecast.mainactivity.model.hours24.Speed;
import com.google.gson.annotations.SerializedName;

public class WindGust {

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
                "WindGust{" +
                        "speed = '" + speed + '\'' +
                        ",direction = '" + direction + '\'' +
                        "}";
    }
}