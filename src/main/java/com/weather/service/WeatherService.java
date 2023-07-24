package com.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.entity.User_Info;
import com.weather.entity.WeatherDataEntity;
import com.weather.entity.WeatherDataRepository;
import com.weather.filter.WeatherData;
import com.weather.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    List<WeatherDataEntity> weatherDataEntityList=null;

    private final RestTemplate restTemplate;
    private final WeatherDataRepository weatherDataRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoRepository repository;

    // private final ObjectMapper objectMapper;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Autowired
    public WeatherService(RestTemplate restTemplate, WeatherDataRepository weatherDataRepository, ObjectMapper objectMapper) throws JsonProcessingException {
        this.restTemplate = restTemplate;
        this.weatherDataRepository = weatherDataRepository;
    }

    public WeatherData getWeatherData(String city) {
        String url = apiUrl + "?q=" + city + "&appid=" + apiKey;
        WeatherData response = restTemplate.getForObject(url, WeatherData.class);
        saveWeatherData(response);
        return response;
    }

    public WeatherDataEntity getReportById(int id) {
        return weatherDataEntityList.stream()
                .filter(weatherData -> weatherData.getWeatherId()== id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Report " + id + " not found"));
    }

    public void saveWeatherData(WeatherData weatherData) {

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

    public String addUser(User_Info user_info) {
        user_info.setPassword(passwordEncoder.encode(user_info.getPassword()));
        repository.save(user_info);
        return "user added to system ";
    }

}

