package com.gmail.yeritsyankoryun.weather.dto;


import com.gmail.yeritsyankoryun.weather.model.WeatherType;
import org.springframework.lang.Nullable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

public class WeatherInfoDto {
    @NotBlank
    @Size(min = 2, max = 3)
    private String country;
    @NotBlank
    @Size(min = 2)
    private String city;
    @Min(-90)
    @Max(60)
    @Nullable
    private Double temperature; // in Celsius
    @Nullable
    @Enumerated(EnumType.ORDINAL)
    private WeatherType type;
    @Nullable
    @Min(0)
    @Max(372)
    private Integer windSpeed; // in km/h

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

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public WeatherType getType() {
        return type;
    }

    public void setType(WeatherType type) {
        this.type = type;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }
}
