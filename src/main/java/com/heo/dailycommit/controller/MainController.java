package com.heo.dailycommit.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/")
public class MainController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SlackService slackService;
    
    @RequestMapping(value = "/nosleep")
    public void main(){
        logger.info("nosleep controller :: ");
    }

    @GetMapping("/dailycommit/{id}")
    public Map getMethodName(@PathVariable String id) {
        Map<String,Object> result = slackService.getCommitInfo();

        String date = (String) result.get("date");
        String user = (String) result.get("user");
        int continueCommit = (int) result.get("continue");
        int daily = (int) result.get("daily");
        

        String msg = "[" + date + "] :: [" + user + "] :: 오늘 커밋 " + ((daily > 0) ? "함" : "안함") + " :: 연속커밋 "
                    + continueCommit + "일째";
        
        result.put("user", user);
        result.put("daily", (daily > 0)? true : false);
        result.put("continue", continueCommit);
        result.put("date", date);

        slackService.post(msg);

        return result;
    }

    @GetMapping("/dailycommit/[{ids}]")
    public Map getMethodName(@PathVariable List<String> ids) {

        logger.info("test id :: " + ids);

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("key1", "value1");

        List<String> userList = new ArrayList<String>();

        for (String id : ids) {
            userList.add(id);
        }

        result.put("userList", ids);

        return result;
    }
    
}