package com.weather.controller;

import com.weather.filter.WeatherData;
import com.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService service;
    public WeatherController(WeatherService weatherService){
        this.service = weatherService;
    }
    @GetMapping("/data/{city}")
    public WeatherData getWeatherData(@PathVariable("city") String city) {
        return service.getWeatherData(city);
    }
}

/*
    When a GET request is made to /weather/data/{city}, the getWeatherData method will be invoked, and the returned WeatherData object will be serialized to JSON and sent back as the response.
*/
