package com.aptzip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class AptzipApplication {

	public static void main(String[] args) {
		SpringApplication.run(AptzipApplication.class, args);
	}

}
