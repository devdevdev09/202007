package com.heo.dailycommit.controller;

import com.heo.dailycommit.collection.ResultAllList;
import com.heo.dailycommit.collection.ResultYearlyList;
import com.heo.dailycommit.entitys.ResultDaily;
import com.heo.dailycommit.entitys.ResultYearly;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    public <T> ResponseEntity<?> success(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<ResultDaily> success(ResultDaily result){
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<ResultYearly> success(ResultYearly result){
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<ResultAllList> success(ResultAllList result){
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<ResultDaily> fail(ResultDaily result){
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ResultYearly> fail(ResultYearly result){
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ResultAllList> fail(ResultAllList result){
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
}
