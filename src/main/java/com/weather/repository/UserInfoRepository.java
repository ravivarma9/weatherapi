package com.weather.repository;

import com.weather.entity.User_Info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<User_Info, Integer> {
    Optional<User_Info> findByName(String username);
}
