package com.gmail.yeritsyankoryun.weather.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.gmail.yeritsyankoryun.weather.dto.WeatherInfoDto;
import com.gmail.yeritsyankoryun.weather.model.WeatherInfoModel;
import com.gmail.yeritsyankoryun.weather.service.ConverterService;
import com.gmail.yeritsyankoryun.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherService weatherService;
    private final ConverterService converterService;

    @Autowired
    public WeatherController(WeatherService weatherService, ConverterService converterService) {
        this.weatherService = weatherService;
        this.converterService = converterService;
    }

    @GetMapping
    public List<WeatherInfoDto> getAllWeather() {
        List<WeatherInfoModel> weathers = weatherService.getAllWeather();
        return weathers.stream().map(converterService::convertToDto).collect(Collectors.toList());
    }

    @GetMapping(path = "temp")
    public double getTempByCC(@RequestBody WeatherInfoDto dto) {
        WeatherInfoModel model=converterService.convertToModel(dto);
        return weatherService.getByCC(model.getCountry(), model.getCity()).get().getTemperature();
    }

    @PostMapping(path = "add")
    public void add(@RequestBody WeatherInfoDto weatherDto) {
        weatherService.addWeather(converterService.convertToModel(weatherDto));
    }

    @PutMapping(path = "update")
    public void updateTemp(@RequestBody WeatherInfoDto weatherDto) {
         weatherService.getByCC(weatherDto.getCountry(), weatherDto.getCity()).get().setTemperature(weatherDto.getTemperature());
    }

    @DeleteMapping(path = "delete")
    public void delete() {
        weatherService.deleteAll();
    }
}
//convert dto to model
// paths
// validations
// types