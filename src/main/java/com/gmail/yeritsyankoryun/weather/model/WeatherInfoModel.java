package com.gmail.yeritsyankoryun.weather.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

public class WeatherInfoModel {
    @NotBlank
    @Size(min = 2, max = 3)
    private String country;
    @NotBlank
    @Size(min = 2)
    private String city;
    @Min(-90)
    @Max(60)
    private double temperature; // in Celsius
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private WeatherType type;
    @Min(0)
    @Max(372)
    private int windSpeed; // in km/h

    public WeatherInfoModel() {
    }

    public WeatherInfoModel(String country, String city, double temperature, WeatherType type, int windSpeed) {
        this.country = country;
        this.city = city;
        this.temperature = temperature;
        this.type = type;
        this.windSpeed = windSpeed;

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public WeatherType getType() {
        return type;
    }

    public void setType(WeatherType type) {
        this.type = type;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
