package com.heo.dailycommit.error;

public class NotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    NotFoundException(String id){
        super("Not Found :: User Id :: " + id);
    }
}