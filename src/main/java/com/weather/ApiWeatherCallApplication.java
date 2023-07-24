package com.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*import springfox.documentation.swagger2.annotations.EnableSwagger2;*/

@SpringBootApplication


@ComponentScan(basePackages = "com.weather")
public class ApiWeatherCallApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiWeatherCallApplication.class, args);
	}

}
