package com.docker.example.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DailyCommitApplication {

	@RequestMapping("/")
	public String hello(){
		return "repo : https://github.com/devdevdev09/202007";
	}

	public static void main(String[] args) {
		SpringApplication.run(DailyCommitApplication.class, args);
	}

}
