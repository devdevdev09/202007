package com.heo.dailycommit.controller;

import com.heo.dailycommit.entitys.ResultDaily;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    public <T> ResponseEntity<?> success(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<ResultDaily> success(ResultDaily obj){
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    public ResponseEntity<ResultDaily> fail(){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public <T> ResponseEntity<?> fail(Object obj){
        return new ResponseEntity<>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public <T> ResponseEntity<?> fail(ResultDaily obj){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
}
