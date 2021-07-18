package com.gmail.yeritsyankoryun.weather.controller;

import com.gmail.yeritsyankoryun.weather.dto.WeatherInfoDto;
import com.gmail.yeritsyankoryun.weather.service.WeatherService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public List<WeatherInfoDto> getWeatherInfo(@RequestParam(name = "country", required = false) String country,
                                               @RequestParam(name = "city", required = false) String city) {
        return weatherService.getWeatherInfo(country, city);
    }

    @PostMapping(path = "create")
    public void create(@Valid @RequestBody WeatherInfoDto dto) {
        weatherService.addWeather(dto);
    }

    @PutMapping(path = "update")
    public void update(@Valid @RequestBody WeatherInfoDto dto) throws Exception {
        weatherService.updateWeather(dto);
    }


    @DeleteMapping(path = "delete")
    public void delete(@RequestParam(name = "country", required = false) String country,
                       @RequestParam(name = "city", required = false) String city) {
        weatherService.delete(country, city);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}