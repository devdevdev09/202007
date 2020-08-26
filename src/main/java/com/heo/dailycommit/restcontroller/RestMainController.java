package com.heo.dailycommit.restcontroller;

import java.util.Map;
import java.util.Random;

import com.heo.dailycommit.collection.ResultAllList;
import com.heo.dailycommit.entitys.ResultDaily;
import com.heo.dailycommit.service.CommitService;
import com.heo.dailycommit.service.CommonService;
import com.heo.dailycommit.service.SlackService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class RestMainController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SlackService slackService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommitService commitService;

    @GetMapping("/test/string")
    public String test_string(){
        return "test string";
    }

    @GetMapping("/test/number/random")
    public int test_random_num(){
        Random rand = new Random();
        int num = rand.nextInt(5);
        return num;
    }

    @GetMapping("/dailycommit/{id}")
    public ResultDaily getDailyCommit(@PathVariable String id, 
                                      @RequestParam(required = false) String year) throws Exception {
        
        ResultDaily result = commitService.getCommitInfo(id, year);

        return result;
    }

    @PostMapping("/dailycommit/{id}")
    public Map<String, Object> postDailyCommit(@PathVariable String id
                                             , @RequestBody Map<String, Object> requestBody) {
        String webhook = (String)requestBody.get("webhook");

        Map<String,Object> result = slackService.getCommitInfo(id);

        if(commonService.isError(result)){
            return result;
        }

        int daily = (int) result.get("daily");
        int continueCommit = (int) result.get("continue");
        
        result.put("continue", continueCommit + ((daily > 0)? 1 : 0));

        if(webhook == null || webhook.isEmpty()){
            result.put("msg", "slack webhook url is Empty!!");
            result.put("daily", (daily > 0)? true : false);
            return result;
        }
    
        String msg = slackService.getSlackMsg(result);
    
        slackService.post(msg, webhook);

        result.put("daily", (daily > 0)? true : false);
    
        return result;
    }

    // contribution in last year
    @GetMapping("/dailycommit/{id}/lastyear")
    public Map<String, Object> getLastYearDailyCommit(@PathVariable String id) {
        Map<String,Object> result = slackService.getLastYearCommitInfo(id);

        if(commonService.isError(result)){
            return result;
        }

        String date = (String) result.get("date");
        String user = (String) result.get("user");
        int continueCommit = (int) result.get("continue");
        int daily = (int) result.get("daily");
        int all = (int) result.get("all");
        
        result.put("user", user);
        result.put("daily", (daily > 0)? true : false);
        result.put("continue", continueCommit + ((daily > 0)? 1 : 0));
        result.put("date", date);
        result.put("all", all);

        return result;
    }

    // yearlist all
    @GetMapping("/dailycommit/{id}/all")
    public ResultAllList getAllDailyCommit(@PathVariable String id) {
        ResultAllList result = slackService.getAllCommitInfo(id);

        return result;
    }
}