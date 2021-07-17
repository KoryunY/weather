package com.gmail.yeritsyankoryun.weather.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.gmail.yeritsyankoryun.weather.dto.WeatherInfoDto;
import com.gmail.yeritsyankoryun.weather.model.WeatherInfoModel;
import com.gmail.yeritsyankoryun.weather.service.ConverterService;
import com.gmail.yeritsyankoryun.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public List<WeatherInfoDto> getAllWeather() {
        return weatherService.getAllWeather();
    }

    @GetMapping(path = "temperature")
    public double getTempByCC(@RequestBody WeatherInfoDto dto) {
        return weatherService.getByCC(dto).get().getTemperature();
    }

    @PostMapping(path = "create")
    public void create(@RequestBody WeatherInfoDto dto) throws Exception {
        weatherService.addWeather(dto);
    }

    @PutMapping(path = "updateall")
    public void updateAll(@RequestBody WeatherInfoDto dto) throws Exception {
        weatherService.updateWeather(dto);
    }

    @PutMapping(path = "update")
    public void updateTemp(@RequestBody WeatherInfoDto dto) throws Exception {
        weatherService.updateWeatherTemperature(dto);
    }

    @DeleteMapping(path = "deletecc")
    public void deleteByCC(@RequestBody WeatherInfoDto dto) {
        weatherService.deleteByCC(dto);
    }

    @DeleteMapping(path = "delete")
    public void delete() {
        weatherService.deleteAll();
    }
}
