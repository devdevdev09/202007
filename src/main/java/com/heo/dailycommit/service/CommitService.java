package com.heo.dailycommit.service;

import com.heo.dailycommit.entitys.ResultDaily;
import com.heo.dailycommit.parse.HtmlParse;
import com.heo.dailycommit.slack.Slack;
import com.heo.dailycommit.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommitService {
    // private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Slack slack;

    @Autowired
    private HtmlParse htmlparse;

    public final String GITHUB_URL = "https://github.com";
    
    // year 아무것도 안함????
    public ResultDaily getCommitInfo(String user, String year) throws Exception{
        ResultDaily result = null;

        String url = GITHUB_URL + "/" + user;
        String today = Utils.getToday();

        try {
            Document doc = Jsoup.connect(url).get();
            
            int todayCommit = htmlparse.getCountByDoc(doc, today);

            String yesterday   = Utils.getDate(1);
            int recur_count    = 1;
            int continueCommit = htmlparse.getCountRecursive(doc, yesterday, recur_count);

            result = new ResultDaily(today, continueCommit + ((todayCommit > 0)? 1 : 0) , (todayCommit > 0) ? true : false, user);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }

        return result;
    }
}