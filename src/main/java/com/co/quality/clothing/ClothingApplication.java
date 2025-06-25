package com.co.quality.clothing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClothingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothingApplication.class, args);
	}

}
