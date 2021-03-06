package com.gmail.yeritsyankoryun.weather.dao;

import com.gmail.yeritsyankoryun.weather.dto.WeatherInfoDto;
import com.gmail.yeritsyankoryun.weather.model.WeatherInfoModel;
import com.gmail.yeritsyankoryun.weather.model.WeatherType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class WeatherDataAccessService {
    private static List<WeatherInfoModel> weathers = new ArrayList<>();

    static {
        weathers.add(new WeatherInfoModel("Arm", "Yerevan", 37, WeatherType.SUNNY, 5));
        weathers.add(new WeatherInfoModel("Arm", "Abovyan", 17, WeatherType.RAIN, 17));

    }

    public List<WeatherInfoModel> getAll() {
        return weathers;
    }

    public Optional<WeatherInfoModel> getByCC(String country, String city) {
        return weathers.stream().filter(weather -> weather.getCity().equals(city) && weather.getCountry().equals(country)).findFirst();
    }

    public void persist(WeatherInfoModel weather) {
        if(getByCC(weather.getCountry(), weather.getCity()).isEmpty())
            weathers.add(weather);
        else throw  new IllegalArgumentException("Exist",new Throwable("Model"));

    }

    public void update(WeatherInfoModel newWeather) {
        WeatherInfoModel oldWeather = getByCC(newWeather.getCountry(), newWeather.getCity()).get();
        if (newWeather.getTemperature() != null)
            oldWeather.setTemperature(newWeather.getTemperature());
        if (newWeather.getWindSpeed() != null)
            oldWeather.setWindSpeed(newWeather.getWindSpeed());
        if (newWeather.getType() != null)
            oldWeather.setType(newWeather.getType());
    }

    public void deleteByCC(String country, String city) {
        getByCC(country, city).ifPresent(model -> weathers.remove(model));
    }

    public void deleteAll() {
        weathers.clear();
    }
}
