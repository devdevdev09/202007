package com.heo.dailycommit.service;

import java.util.HashMap;
import java.util.Map;

import com.heo.dailycommit.parse.HtmlParse;
import com.heo.dailycommit.slack.Slack;
import com.heo.dailycommit.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlackService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Slack slack;

    @Autowired
    private HtmlParse htplparse;
    
    public Map<String,Object> getCommitInfo(){
        Map<String, Object> result = new HashMap<String, Object>();

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

            result.put("user", user);
            result.put("daily", todayCommit);
            result.put("continue", continueCommit);
            result.put("date", today);

        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public void post(String msg){
        slack.send(msg);
    }
}
