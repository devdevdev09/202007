package com.heo.dailycommit.main;

import org.springframework.web.bind.annotation.RestController;

import com.heo.dailycommit.parse.HtmlParse;
import com.heo.dailycommit.slack.*;
import com.heo.dailycommit.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/")
public class DailyCommit {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Slack slack;

    @Autowired
    private HtmlParse htplparse;

    @Autowired
    public DailyCommit(Slack slack){
        this.slack = slack;
    }

    @RequestMapping(value = "/countRecur")
    // @RequestMapping(value = "/post/slack")
    public void SendToSlack(String targetUser){
        // String user = targetUser.isEmpty() ? "devdevdev09" : targetUser;
        String user = "devdevdev09";
        String url = "https://github.com/" + user;
        String today = Utils.getToday();
        String msg = "";

        try {
            Document doc = Jsoup.connect(url).get();
            int todayCommit = htplparse.getCountByDoc(doc, today);

            String yesterday = Utils.getDate(1);
            int recur_count = 1;
            int continueCommit = htplparse.getCountRecursive(doc, yesterday, recur_count);

            msg = "[" + today + "] :: [" + user + "] :: 오늘 커밋 " + ((todayCommit > 0) ? "함" : "안함") + " :: 연속커밋 "
                    + continueCommit + "일째";

            logger.info(msg);
            slack.send(msg);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
    }
}