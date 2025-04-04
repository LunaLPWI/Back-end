package com.luna.luna_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class LunaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LunaProjectApplication.class, args);
	}

}
