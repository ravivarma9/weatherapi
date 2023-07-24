package com.weather.controller;

import com.weather.entity.User_Info;
import com.weather.entity.WeatherDataEntity;
import com.weather.filter.AuthRequest;
import com.weather.filter.WeatherData;
import com.weather.service.JwtService;
import com.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    //to signup as a new user;
    @PostMapping("/new")
    public String addNewUser(@RequestBody User_Info user_info) {
        return weatherService.addUser(user_info);
    }

    //to authenticate using existing user and generate a new JWT token;
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }


    //to get the data from other microservice which is running on port 8080;
    @GetMapping("/products/all")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Object> getAllTheProducts() {
        Object[] getProductId = restTemplate.getForObject("http://localhost:8080/products/all", Object[].class);
        return Arrays.asList(getProductId);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public WeatherDataEntity getReportById(@PathVariable int id) {
        return weatherService.getReportById(id);
    }

    @GetMapping("/data/{city}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public WeatherData getWeatherData(@PathVariable("city") String city) {
        return weatherService.getWeatherData(city);
    }

}
