package com.gmail.yeritsyankoryun.weather.controller;

import com.gmail.yeritsyankoryun.weather.dto.WeatherInfoDto;
import com.gmail.yeritsyankoryun.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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
    public Object getWeatherInfo(@RequestParam(name = "country", required = false) String country,
                                 @RequestParam(name = "city", required = false) String city) {
        return weatherService.getWeatherInfo(country, city);
    }

    @PostMapping(path = "create")
    public void create(@Valid @RequestBody WeatherInfoDto dto) {
        weatherService.addWeather(dto);
    }

    @PutMapping(path = "update")
    public void update(@Valid @RequestBody WeatherInfoDto dto) {
        weatherService.updateWeather(dto);
    }


    @DeleteMapping(path = "delete")
    public void delete(@RequestParam(name = "country",required = false) String country,
                       @RequestParam(name = "city", required = false) String city) throws IllegalArgumentException {
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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleValidationExceptions(
            IllegalArgumentException ex) {
        return String.valueOf(ex.getCause().getMessage()+" is "+ex.getMessage());
    }
}