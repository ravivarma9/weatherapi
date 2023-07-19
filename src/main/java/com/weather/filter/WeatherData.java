package com.weather.filter;

import java.util.*;

import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

@Getter
@Setter
@Data
@RequiredArgsConstructor
public class WeatherData {

        private Coord coord;
        private List<Weather> weather;
        private String base;
        private Main main;
        private int visibility;
        private Wind wind;
        private Clouds clouds;
        private long dt;
        private Sys sys;
        private int timezone;
        private long id;
        private String name;
        private int cod;

        @Getter
        @Setter
        public static class Coord {
            private double lon;
            private double lat;
        }

        @Getter
        @Setter
        public static class Weather {
            private int id;
            private String main;
            private String description;
            private String icon;
        }

        @Getter
        @Setter
        public static class Main {
            private double temp;
            private double feels_like;
            private double temp_min;
            private double temp_max;
            private int pressure;
            private double humidity;
        }

        @Getter
        @Setter
        public static class Wind {
            private double speed;
            private int deg;
        }

        @Getter
        @Setter
        public static class Clouds {
            private int all;
        }

        @Getter
        @Setter
        public static class Sys {
            private int type;
            private int id;
            private String country;
            private long sunrise;
            private long sunset;
        }

    /*public static interface UserRepository extends JpaRepository<User, Long> {
        User findByUsername(String username);
    }*/
}
