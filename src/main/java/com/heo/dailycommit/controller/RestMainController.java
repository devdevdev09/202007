package com.heo.dailycommit.controller;

import java.util.Map;

import com.heo.dailycommit.collection.ResultAllList;
import com.heo.dailycommit.entitys.ResultDaily;
import com.heo.dailycommit.entitys.ResultYearly;
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
    private CommitService commitService;

    public RestMainController(SlackService slackService,
                            CommitService commitService){
        this.slackService = slackService;
        this.commitService = commitService;                                

    }

    @GetMapping("/dailycommit/{id}")
    public ResponseEntity<ResultDaily> getDailyCommit(
                            @PathVariable String id,
                            @RequestParam(required = false) String year) throws Exception {
        ResultDaily result = null;

        try {
            result = commitService.getCommitInfo(id);
            return success(result);
        } catch (Exception e) {
            return fail(result);
        }
    }

    @PostMapping("/dailycommit/{id}")
    public ResponseEntity<ResultDaily> postDailyCommit(
                            @PathVariable String id, 
                            @RequestBody Map<String, Object> requestBody) throws Exception{
        String webhook = (String) requestBody.get("webhook");

        ResultDaily result = null;
        try {
            result = commitService.getCommitInfo(id);
            
            String msg = slackService.getSlackMsg(result);
        
            slackService.post(msg, webhook);

            return success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return fail(result);
        }
    }

    @GetMapping("/dailycommit/{id}/lastyear")
    public ResponseEntity<ResultYearly> getLastYearDailyCommit(@PathVariable String id) {
        ResultYearly result = null;

        try{
            result = commitService.getLastYearCommitInfo(id);
            return success(result);
        }catch(Exception e){
            return fail(result);
        }
    }

    @GetMapping("/dailycommit/{id}/all")
    public ResponseEntity<ResultAllList> getAllDailyCommit(@PathVariable String id) {
        ResultAllList result = null;

        try{
            result = commitService.getAllCommitInfo(id);
            return success(result);
        }catch(Exception e){
            return fail(result);
        }
    }
}