package com.gmail.yeritsyankoryun.weather.service;

import com.gmail.yeritsyankoryun.weather.dao.WeatherDataAccessService;
import com.gmail.yeritsyankoryun.weather.dto.WeatherInfoDto;
import com.gmail.yeritsyankoryun.weather.model.WeatherInfoModel;
import com.gmail.yeritsyankoryun.weather.service.converter.WeatherConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<WeatherInfoDto> getWeatherInfo(String country,String city) {
        if(country==null && city==null)
        return weatherDataAccessService.getAll().stream()
                .map(weatherConverter::convertToDto)
                .collect(Collectors.toList());
        else if(country==null || city==null){
            return null;
        }
        return weatherDataAccessService.getAll().stream()
                .filter(weather-> weather.getCountry().equals(country) && weather.getCity().equals(city))
                .map(weatherConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<WeatherInfoModel> getByCC(WeatherInfoDto dto) {
        WeatherInfoModel model = weatherConverter.convertToModel(dto);
        return weatherDataAccessService.getByCC(dto.getCountry(), model.getCity());
    }

    public void addWeather(WeatherInfoDto dto) {
            weatherDataAccessService.insert(weatherConverter.convertToModel(dto));
    }

    public void updateWeather(WeatherInfoDto dto)  {
            WeatherInfoModel temp = getByCC(dto).get();
            if(dto.getTemperature()!=null)
            temp.setTemperature(dto.getTemperature());
            if(dto.getWindSpeed()!=null)
            temp.setWindSpeed(dto.getWindSpeed());
            if(dto.getType()!=null)
            temp.setType(dto.getType());
    }

    public void delete(String country,String city){
        if(country==null && city==null)
            weatherDataAccessService.deleteAll();
        else if(country==null || city==null){
            System.out.println("do Nothing");
        }
        else weatherDataAccessService.deleteByCC(country,city);
    }
}
