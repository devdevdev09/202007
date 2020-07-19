package com.heo.dailycommit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableScheduling
@RestController
public class DailyCommitApplication {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/")
	public String hello(){
		logger.info("info");
		return "repo : https://github.com/devdevdev09/202007";
	}

	public static void main(String[] args) {
		SpringApplication.run(DailyCommitApplication.class, args);
	}

}
