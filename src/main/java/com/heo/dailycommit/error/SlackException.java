package com.heo.dailycommit.error;

public class SlackException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 857830231620313665L;

    public SlackException(SlackErrors error) {
        this(error.getDescription());
    }

    public SlackException(String msg){
        super(msg);
    }
    
}