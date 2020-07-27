package com.heo.dailycommit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value = "/")
public class MainController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/")
    public String main(){
        String result = "Daily Commit Counter";
        return "index";
    }
}