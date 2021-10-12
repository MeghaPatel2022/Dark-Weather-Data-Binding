package com.darksky.weather.today.weatherforecast.mainactivity.model.locationdata;

public class AddLocationData {

    public String city;
    public String country;
    public String area;
    public String key;
    public int daynight;

    @Override
    public String toString() {
        return "AddLocationData{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", area='" + area + '\'' +
                ", key='" + key + '\'' +
                ", daynight=" + daynight +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getDaynight() {
        return daynight;
    }

    public void setDaynight(int daynight) {
        this.daynight = daynight;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;

        if (object != null && object instanceof AddLocationData) {
            sameSame = this.getKey().equalsIgnoreCase(((AddLocationData) object).getKey());
        }

        return sameSame;
    }


}
