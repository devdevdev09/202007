package com.heo.dailycommit.restcontroller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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

    // ssl 에러 처리
    public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
            @Override public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SlackService slackService;

    @Autowired
    private CommonService commonService;

    @GetMapping("/dailycommit/{id}")
    public Map<String, Object> getDailyCommit(@PathVariable String id
                                            , @RequestParam(required = false) String year) {
        
        Map<String,Object> result = new HashMap<String, Object>();

        if(year == null || year.isEmpty()){
            result = slackService.getCommitInfo(id);

            if(commonService.isError(result)){
                return result;
            }

            int continueCommit = (int) result.get("continue");
            int daily = (int) result.get("daily");
            
            result.put("daily", (daily > 0)? true : false);
            result.put("continue", continueCommit + ((daily > 0)? 1 : 0));
        }else{
            result = slackService.getCommitInfo(id, year);

            if(commonService.isError(result)){
                return result;
            }
        }

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
    public Map<String, Object> getAllDailyCommit(@PathVariable String id) {
        Map<String,Object> result = slackService.getAllCommitInfo(id);

        if(commonService.isError(result)){
            return result;
        }

        return result;
    }
}