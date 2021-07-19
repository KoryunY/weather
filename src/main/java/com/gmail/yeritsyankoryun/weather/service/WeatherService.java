package com.gmail.yeritsyankoryun.weather.service;

import com.gmail.yeritsyankoryun.weather.controller.WeatherController;
import com.gmail.yeritsyankoryun.weather.dao.WeatherDataAccessService;
import com.gmail.yeritsyankoryun.weather.dto.WeatherInfoDto;
import com.gmail.yeritsyankoryun.weather.model.WeatherInfoModel;
import com.gmail.yeritsyankoryun.weather.service.converter.WeatherConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private final WeatherDataAccessService weatherDataAccessService;
    private final WeatherConverter weatherConverter;

    @Autowired
    public WeatherService(WeatherDataAccessService weatherDataAccessService, WeatherConverter weatherConverter) {
        this.weatherDataAccessService = weatherDataAccessService;
        this.weatherConverter = weatherConverter;
    }

    public Object getWeatherInfo(String country, String city) {
        if (country == null && city == null)
            return weatherDataAccessService.getAll().stream()
                    .map(weatherConverter::convertToDto)
                    .collect(Collectors.toList());
        else if (country == null || city == null) {
            throw  new IllegalArgumentException("NUll",new Throwable(city==null?"City":"Country"));
        }
        return weatherConverter.convertToDto(weatherDataAccessService.getByCC(country,city).get());
    }

    public void addWeather(WeatherInfoDto dto) {
        if(weatherDataAccessService.getByCC(dto.getCountry(), dto.getCity()).isEmpty())
            weatherDataAccessService.insert(weatherConverter.convertToModel(dto));
        else updateWeather(dto);
    }

    public void updateWeather(WeatherInfoDto dto) {
        weatherDataAccessService.update(dto);
    }

    public void delete(String country, String city) throws IllegalArgumentException {
        if (country == null && city == null)
            weatherDataAccessService.deleteAll();
        else if (country == null || city == null) {
            throw  new IllegalArgumentException("NUll",new Throwable(city==null?"City":"Country"));
        } else weatherDataAccessService.deleteByCC(country, city);
    }
}
