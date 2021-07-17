package com.gmail.yeritsyankoryun.weather.dto;


import com.gmail.yeritsyankoryun.weather.model.WeatherType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WeatherInfoDto {

    @NotNull
    @Size(min = 2,max = 3)
    private String country;
    @NotNull
    @Size(min=2)
    private String city;
    @NotNull
    private double temperature;
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private WeatherType type;
    @NotNull
    private int windSpeed; // in km/h

    public String getCountry() {
        return country;
    }

    public void setCountry(@Valid String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(@Valid String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(@Valid double temperature) {
        if(temperature>-90 && temperature<60)
            this.temperature = temperature;
    }

    public WeatherType getType() {
        return type;
    }

    public void setType(@Valid WeatherType type) {
        this.type = type;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(@Valid int windSpeed) {
        if(windSpeed>0 && windSpeed<372)
            this.windSpeed = windSpeed;
    }
}
