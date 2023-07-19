package com.weather.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherDataEntity, Long> {
    Optional<WeatherDataEntity> findByCity(String city);
}
