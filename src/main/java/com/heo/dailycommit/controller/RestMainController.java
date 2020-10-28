package com.heo.dailycommit.controller;

import java.util.Map;

import com.heo.dailycommit.collection.ResultAllList;
import com.heo.dailycommit.entitys.ResultDaily;
import com.heo.dailycommit.service.CommitService;
import com.heo.dailycommit.service.CommonService;
import com.heo.dailycommit.service.SlackService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class RestMainController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private SlackService slackService;
    private CommonService commonService;
    private CommitService commitService;

    public RestMainController(SlackService slackService,
                            CommonService commonService,
                            CommitService commitService){
        this.slackService = slackService;
        this.commonService = commonService;
        this.commitService = commitService;                                

    }

    @GetMapping("/dailycommit/{id}")
    public ResponseEntity<ResultDaily> getDailyCommit(
                            @PathVariable String id,
                            @RequestParam(required = false) String year) throws Exception {
        try {
            ResultDaily result = commitService.getCommitInfo(id);
            return success(result);
        } catch (Exception e) {
            return fail();
        }
    }

    @PostMapping("/dailycommit/{id}")
    public ResponseEntity<ResultDaily> postDailyCommit(
                            @PathVariable String id, 
                            @RequestBody Map<String, Object> requestBody) throws Exception{
        String webhook = (String) requestBody.get("webhook");

        ResultDaily result;
        try {
            result = commitService.getCommitInfo(id);
            
            String msg = slackService.getSlackMsg(result);
        
            slackService.post(msg, webhook);

            return success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
    }

    @GetMapping("/dailycommit/{id}/lastyear")
    public Map<String, Object> getLastYearDailyCommit(@PathVariable String id) {
        Map<String,Object> result = commitService.getLastYearCommitInfo(id);

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

    @GetMapping("/dailycommit/{id}/all")
    public ResultAllList getAllDailyCommit(@PathVariable String id) {
        ResultAllList result = commitService.getAllCommitInfo(id);

        return result;
    }
}