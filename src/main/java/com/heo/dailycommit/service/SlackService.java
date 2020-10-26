package com.heo.dailycommit.service;

import java.util.Map;

import com.heo.dailycommit.entitys.ResultDaily;
import com.heo.dailycommit.slack.Slack;

import org.springframework.stereotype.Service;

@Service
public class SlackService {
    // private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Slack slack;

    public SlackService(Slack slack){
        this.slack = slack;
    }

    public final String GITHUB_URL = "https://github.com";
    
    // slack
    public String getSlackMsg(ResultDaily data){
        String date = data.getDate();
        String user = data.getUser();
        int continueCommit = data.getContinues();
        boolean daily = data.isDaily();

        String msg = "[" + date + "] :: [" + user + "] :: 오늘 커밋 " + ((daily) ? "함" : "안함") + " :: 연속커밋 "
                    + continueCommit + "일째";
        return msg;
    }

    // slack
    public String getSlackMsg(Map<String, Object> data){
        String date = (String) data.get("date");
        String user = (String) data.get("user");
        int continueCommit = (int) data.get("continue");
        int daily = (int) data.get("daily");

        String msg = "[" + date + "] :: [" + user + "] :: 오늘 커밋 " + ((daily > 0) ? "함" : "안함") + " :: 연속커밋 "
                    + continueCommit + "일째";
        return msg;
    }

    // slack
    public void post(String msg, String hooks){
        slack.send(msg, hooks);
    }
    

}
