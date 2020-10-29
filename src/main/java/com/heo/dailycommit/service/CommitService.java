package com.heo.dailycommit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heo.dailycommit.collection.ResultAllList;
import com.heo.dailycommit.collection.ResultYearlyList;
import com.heo.dailycommit.entitys.ResultDaily;
import com.heo.dailycommit.entitys.ResultYearly;
import com.heo.dailycommit.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommitService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private HtmlService htmlService;

    public CommitService(HtmlService htmlService){
        this.htmlService = htmlService;
    }

    public final String GITHUB_URL = "https://github.com";

    public Map<String,Object> getCommitInfo(String user, String year){
        Map<String, Object> result = new HashMap<String, Object>();

        String url = GITHUB_URL + "/" + user;
        String today = Utils.getToday();

        try {
            Document doc = Jsoup.connect(url).get();

            List<Map<String, String>> years = htmlService.getYearList(doc);
            boolean isYear = false;

            for(Map<String, String> map : years){
                if(map.get("year").equals(year)){
                    isYear = true;
                    url = GITHUB_URL + map.get("href");  
                } 
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
                int todayCommit = htmlService.getCountByDoc(doc, today);

                String yesterday = Utils.getDate(1);
                int recur_count = 1;
                int continueCommit = htmlService.getCountRecursive(doc, yesterday, recur_count);
                
                result.put("daily", todayCommit);
                result.put("continue", continueCommit);
                result.put("date", today);                
            }

            result.put("user", user);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
            
            result.put("Exception", e.toString());
            return result;
        }

        return result;
    }

    public ResultYearly getLastYearCommitInfo(String user){
        ResultYearly result = null;

        String url = GITHUB_URL + "/" + user;
        String today = Utils.getToday();

        try {
            Document doc = Jsoup.connect(url).get();
            int todayCommit = htmlService.getCountByDoc(doc, today);

            String yesterday = Utils.getDate(1);
            int recur_count = 1;
            int continueCommit = htmlService.getCountRecursive(doc, yesterday, recur_count);
            int allCommit = htmlService.getLastYearCountRecursive(doc, yesterday, recur_count);

            result = new ResultYearly(user, allCommit, user);

        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        return result;
    }


    public ResultAllList getAllCommitInfo(String user){
        ResultAllList resultAllList = new ResultAllList();

        String url = GITHUB_URL + "/" + user;

        try {
            Document doc = Jsoup.connect(url).get();

            List<Map<String, String>> years = htmlService.getYearList(doc);
            ResultYearlyList resultYearlyList = new ResultYearlyList();

            int allCommit = 0;

            for(Map<String, String> item : years){
                String year = item.get("year");
                String href = item.get("href");

                url = GITHUB_URL + href;  
                doc = Jsoup.connect(url).get();

                int yearCommit = htmlService.getYearCount(year, doc);
                ResultYearly resultYear = new ResultYearly(year, yearCommit, user);

                resultYearlyList.add(resultYear);
                allCommit += yearCommit;
            }

            resultAllList.setAllCommit(allCommit);
            resultAllList.setList(resultYearlyList);

        } catch (Exception e) {
            logger.info(e.toString());
            e.printStackTrace();
        }

        return resultAllList;

    }

    public ResultDaily getCommitInfo(String user) throws Exception{
        ResultDaily result = null;

        String url = GITHUB_URL + "/" + user;
        String today = Utils.getToday();

        try {
            Document doc = Jsoup.connect(url).get();
            
            int todayCommit = htmlService.getCountByDoc(doc, today);

            String yesterday   = Utils.getDate(1);
            int recur_count    = 1;
            int continueCommit = htmlService.getCountRecursive(doc, yesterday, recur_count);

            result = new ResultDaily(today, continueCommit + ((todayCommit > 0)? 1 : 0) , (todayCommit > 0) ? true : false, user);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }

        return result;
    }
}