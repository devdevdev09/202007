package com.heo.dailycommit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heo.dailycommit.parse.HtmlParse;
import com.heo.dailycommit.slack.Slack;
import com.heo.dailycommit.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    private HtmlParse htmlparse;
    
    public Map<String,Object> getCommitInfo(String user){
        Map<String, Object> result = new HashMap<String, Object>();

        String url = "https://github.com/" + user;
        String today = Utils.getToday();

        try {
            Document doc = Jsoup.connect(url).get();
            
            int todayCommit = htmlparse.getCountByDoc(doc, today);

            String yesterday = Utils.getDate(1);
            int recur_count = 1;
            int continueCommit = htmlparse.getCountRecursive(doc, yesterday, recur_count);

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

    public Map<String,Object> getCommitInfo(String user, String year){
        Map<String, Object> result = new HashMap<String, Object>();

        String url = "https://github.com/" + user;
        String today = Utils.getToday();

        try {
            Document doc = Jsoup.connect(url).get();

            List<Map<String, String>> years = htmlparse.getYearList(doc);
            boolean isYear = false;

            for(Map<String, String> map : years){
                if(map.get("year").equals(year)){
                    isYear = true;
                    url = "https://github.com" + map.get("href");  
                } 

                // System.out.println("year : " + map.get("year"));
                // System.out.println("href : " + map.get("href"));
            }

            if(isYear){
                doc = Jsoup.connect(url).get();

                Elements elements = doc.body().select("[data-date*=" + year + "]");

                int yearCommit = 0;
                for(Element element : elements){
                    int count = Integer.parseInt(element.attr("data-count"));
                    if(count > 0){
                        ++yearCommit;
                    }
                }
                
                result.put("year", year);
                result.put("commit", yearCommit);
            }else{
                int todayCommit = htmlparse.getCountByDoc(doc, today);

                String yesterday = Utils.getDate(1);
                int recur_count = 1;
                int continueCommit = htmlparse.getCountRecursive(doc, yesterday, recur_count);
    
                
                result.put("daily", todayCommit);
                result.put("continue", continueCommit);
                result.put("date", today);                
            }

            result.put("user", user);
            


        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public Map<String,Object> getAllCommitInfo(String user){
        Map<String, Object> result = new HashMap<String, Object>();

        String url = "https://github.com/" + user;
        String today = Utils.getToday();

        try {
            Document doc = Jsoup.connect(url).get();
            int todayCommit = htmlparse.getCountByDoc(doc, today);

            String yesterday = Utils.getDate(1);
            int recur_count = 1;
            int continueCommit = htmlparse.getCountRecursive(doc, yesterday, recur_count);
            int allCommit = htmlparse.getAllCountRecursive(doc, yesterday, recur_count);

            result.put("user", user);
            result.put("daily", todayCommit);
            result.put("continue", continueCommit);
            result.put("date", today);
            result.put("all", allCommit);

        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public String getSlackMsg(Map<String, Object> data){
        String date = (String) data.get("date");
        String user = (String) data.get("user");
        int continueCommit = (int) data.get("continue");
        int daily = (int) data.get("daily");

        String msg = "[" + date + "] :: [" + user + "] :: 오늘 커밋 " + ((daily > 0) ? "함" : "안함") + " :: 연속커밋 "
                    + continueCommit + "일째";
        return msg;
    }

    public void post(String msg, String hooks){
        slack.send(msg, hooks);
    }
}
