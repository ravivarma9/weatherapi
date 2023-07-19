package com.weather.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "weather")
@Getter
@Setter
public class WeatherDataEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private String city;

        private Double temperature;
        private Double humidity;
        private Double windSpeed;
        private String weatherDescription;
}
