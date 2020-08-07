package com.heo.dailycommit.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@RequestMapping(value = "/")
@RestController
public class RestMainController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SlackService slackService;

    @GetMapping("/dailycommit/{id}")
    public Map<String, Object> getDailyCommit(@PathVariable String id
                                            , @RequestParam(required = false) String year) {
        
        Map<String,Object> result = new HashMap<String, Object>();

        if(year == null || year.isEmpty() || year.isBlank()){
            result = slackService.getCommitInfo(id);
            int continueCommit = (int) result.get("continue");
            int daily = (int) result.get("daily");
            
            result.put("daily", (daily > 0)? true : false);
            result.put("continue", continueCommit + ((daily > 0)? 1 : 0));
        }else{
            result = slackService.getCommitInfo(id, year);
        }

        return result;
    }

    @PostMapping("/dailycommit/{id}")
    public Map<String, Object> postDailyCommit(@PathVariable String id
                                             , @RequestBody Map<String, Object> requestBody) {
        String webhook = (String)requestBody.get("webhook");

        Map<String,Object> result = slackService.getCommitInfo(id);

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

    @GetMapping("/dailycommit/[{ids}]")
    public Map<String, Object> getMethodName(@PathVariable List<String> ids) {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("key1", "value1");

        List<String> userList = new ArrayList<String>();

        for (String id : ids) {
            userList.add(id);
        }

        result.put("userList", ids);

        return result;
    }

    @GetMapping("/dailycommit/{id}/all")
    public Map<String, Object> getAllDailyCommit(@PathVariable String id) {
        Map<String,Object> result = slackService.getAllCommitInfo(id);

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
}