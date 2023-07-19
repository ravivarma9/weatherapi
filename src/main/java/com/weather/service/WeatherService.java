package com.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.entity.WeatherDataEntity;
import com.weather.entity.WeatherDataRepository;
import com.weather.filter.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    private final WeatherDataRepository weatherDataRepository;

    // private final ObjectMapper objectMapper;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Autowired
    public WeatherService(RestTemplate restTemplate, WeatherDataRepository weatherDataRepository, ObjectMapper objectMapper) throws JsonProcessingException {
        this.restTemplate = restTemplate;
        this.weatherDataRepository = weatherDataRepository;
        // this.objectMapper = new ObjectMapper();
    }


    public WeatherData getWeatherData(String city) {
        String url = apiUrl + "?q=" + city + "&appid=" + apiKey;
        WeatherData response = restTemplate.getForObject(url, WeatherData.class);
        saveWeatherData(response);
        return response;
    }

    public void saveWeatherData(WeatherData weatherData) {
/*
        System.out.println(ReflectionToStringBuilder.toString(weatherData));
*/
        WeatherDataEntity weatherDataEntity = new WeatherDataEntity();

        weatherDataEntity.setCity(weatherData.getName());
        weatherDataEntity.setTemperature(weatherData.getMain().getTemp());
        weatherDataEntity.setHumidity(weatherData.getMain().getHumidity());
        weatherDataEntity.setWindSpeed(weatherData.getWind().getSpeed());
        weatherDataEntity.setWeatherDescription(weatherData.getWeather().get(0).getDescription());

        Optional<WeatherDataEntity> existingDataOptional = weatherDataRepository.findByCity(weatherData.getName());
        if (existingDataOptional.isPresent()) {
            WeatherDataEntity existingData = existingDataOptional.get();
            existingData.setTemperature(weatherDataEntity.getTemperature());
            existingData.setHumidity(weatherDataEntity.getHumidity());
            existingData.setWindSpeed(weatherDataEntity.getWindSpeed());
            existingData.setWeatherDescription(weatherDataEntity.getWeatherDescription());
            weatherDataRepository.save(existingData);
        }
        else {
            weatherDataRepository.save(weatherDataEntity);
        }

    }

}

