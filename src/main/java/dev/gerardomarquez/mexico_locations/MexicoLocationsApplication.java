package dev.gerardomarquez.mexico_locations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MexicoLocationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MexicoLocationsApplication.class, args);
	}

}
