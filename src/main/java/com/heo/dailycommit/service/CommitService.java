package com.heo.dailycommit.service;

import java.util.HashMap;
import java.util.Map;

import com.heo.dailycommit.entitys.Result;
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
public class CommitService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Slack slack;

    @Autowired
    private HtmlParse htmlparse;

    public final String GITHUB_URL = "https://github.com";
    
    public Result getCommitInfo(String user, String year){
        Result result = new Result();

        String url = GITHUB_URL + "/" + user;
        String today = Utils.getToday();

        try {
            Document doc = Jsoup.connect(url).get();
            
            int todayCommit = htmlparse.getCountByDoc(doc, today);

            String yesterday = Utils.getDate(1);
            int recur_count = 1;
            int continueCommit = htmlparse.getCountRecursive(doc, yesterday, recur_count);


            result = new Result(today, continueCommit + ((todayCommit > 0)? 1 : 0) , (todayCommit > 0) ? true : false, user);
        } catch (Exception e) {
            // logger.debug(e.getMessage());
            // e.printStackTrace();
            
            // result.put("Exception", e.toString());
            // return result;
        }

        return result;
    }
}