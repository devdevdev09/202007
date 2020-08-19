package com.heo.dailycommit.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CommonService {

    public boolean isError(Map<String, Object> result){
        if(result.get("Exception") != null){
            return true;
        }else{
            return false;
        }
    }
    
}