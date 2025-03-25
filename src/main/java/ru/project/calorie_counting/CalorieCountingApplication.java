package ru.project.calorie_counting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CalorieCountingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalorieCountingApplication.class, args);

		log.info("Приложение CalorieCountingApplication запущено");
	}

}
