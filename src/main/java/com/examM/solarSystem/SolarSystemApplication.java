package com.examM.solarSystem;

import com.examM.solarSystem.SolarModel.Simulation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SolarSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolarSystemApplication.class, args);
	}

}

