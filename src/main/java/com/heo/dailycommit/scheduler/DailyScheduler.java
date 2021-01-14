package com.heo.dailycommit.scheduler;

import com.heo.dailycommit.entitys.ResultDaily;
import com.heo.dailycommit.service.CommitService;
import com.heo.dailycommit.service.SlackService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component // Component를 추가해줘야함
public class DailyScheduler {
    Logger log = LoggerFactory.getLogger(this.getClass());

    private SlackService slackService;
    private CommitService commitService;
    private String id = "devdevdev09";

    public DailyScheduler(SlackService slackService,
                            CommitService commitService){
        this.slackService = slackService;
        this.commitService = commitService;                                
    }

    // TODO :: 서비스로 분리, WEBHOOK 분리
    @Scheduled(cron = "0 */5 22,23 * * *") // 초 분 시 일 월 요일
    public void dailySchedule(){
        String webhook = "https://hooks.slack.com/services/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";

        ResultDaily result = null;
        try {
            result = commitService.getCommitInfo(id);
            String msg = slackService.getSlackMsg(result);
            slackService.post(msg, webhook);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
