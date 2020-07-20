package com.heo.dailycommit;

import com.heo.dailycommit.main.DailyCommit;
import com.heo.dailycommit.slack.Slack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@RestController
public class DailyCommitApplication {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DailyCommit dailyCommit;

	public static void main(String[] args) {
		SpringApplication.run(DailyCommitApplication.class, args);
	}

}
