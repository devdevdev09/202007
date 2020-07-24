package com.heo.dailycommit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping(value = "/")
public class MainController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @RequestMapping(value = "/nosleep")
    public void main(){
        logger.info("nosleep controller :: ");
    }

    @GetMapping("/dailycommit/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map getMethodName(@PathVariable String id) {

        logger.info("test id :: " + id);

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("id", id);

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