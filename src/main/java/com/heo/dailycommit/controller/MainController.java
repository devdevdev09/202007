package com.heo.dailycommit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class MainController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @RequestMapping(value = "/nosleep")
    public void main(){
        logger.info("nosleep controller :: ");
    }
}