package com.heo.dailycommit.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component // Component를 추가해줘야함
public class DailyScheduler {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0 0 23 * * *") // 초 분 시 일 월 요일
    public void testScheduler(){
        log.info("스케줄러 테스트");
    }
}