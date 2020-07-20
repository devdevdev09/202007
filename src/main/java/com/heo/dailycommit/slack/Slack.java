package com.heo.dailycommit.slack;

import com.heo.dailycommit.error.SlackErrors;
import com.heo.dailycommit.error.SlackException;
import com.heo.dailycommit.utils.RestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;


@Component
public class Slack {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestUtils restClientUtil;

    @Value("${slack.value.hooks}")
    String HOOKS;

    @Value("${slack.value.username}")
    private String USER_NAME;

    public void send(String msg) {
        try {
            Map<String, Object> req = new HashMap<String, Object>();
            req.put("text"    , msg);
            req.put("username", USER_NAME);
            String url = HOOKS;

            if(url == null || url == ""){
                throw new SlackException(SlackErrors.HOOKS_NULL); 
            }
            restClientUtil.post(url, HttpMethod.POST, req);

        } catch (IOException e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
    }
}