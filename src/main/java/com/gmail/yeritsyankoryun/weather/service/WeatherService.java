package com.gmail.yeritsyankoryun.weather.service;

import com.gmail.yeritsyankoryun.weather.dao.WeatherDataAccessService;
import com.gmail.yeritsyankoryun.weather.dto.WeatherInfoDto;
import com.gmail.yeritsyankoryun.weather.model.WeatherInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private final WeatherDataAccessService weatherDataAccessService;
    private final ConverterService converterService;

    @Autowired
    public WeatherService(WeatherDataAccessService weatherDataAccessService, ConverterService converterService) {
        this.weatherDataAccessService = weatherDataAccessService;
        this.converterService = converterService;
    }


    public List<WeatherInfoDto> getAllWeather() {
        return weatherDataAccessService.selectAllWeather().stream().map(converterService::convertToDto).collect(Collectors.toList());
    }

    public Optional<WeatherInfoModel> getByCC(WeatherInfoDto dto) {
        WeatherInfoModel model = converterService.convertToModel(dto);
        return weatherDataAccessService.selectByCC(dto.getCountry(), model.getCity());
    }

    public void addWeather( WeatherInfoDto dto) throws Exception {
        if (dto.getTemperature() != 0)
            weatherDataAccessService.insertWeather(converterService.convertToModel(dto));
        else throw new Exception();
    }
    public void updateWeather(WeatherInfoDto dto) throws Exception {
        if (dto.getTemperature() != 0 && dto.getWindSpeed()!=0){
            WeatherInfoModel temp=getByCC(dto).get();
            temp.setTemperature(dto.getTemperature());
            temp.setWindSpeed(dto.getWindSpeed());
            temp.setType(dto.getType());
    }
        else throw new Exception();
    }
    public void updateWeatherTemperature(WeatherInfoDto dto) throws Exception {
        if (dto.getTemperature() != 0)
            getByCC(dto).get().setTemperature(dto.getTemperature());
        else throw new Exception();
    }
    public void deleteByCC(WeatherInfoDto dto) {
        weatherDataAccessService.deleteByCC(dto.getCountry(), dto.getCity());
    }

    public void deleteAll() {
        weatherDataAccessService.deleteAll();
    }
}
