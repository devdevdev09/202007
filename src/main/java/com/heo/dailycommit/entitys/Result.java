package com.heo.dailycommit.entitys;

import lombok.Data;

@Data
public class Result {
    private String date   = "0000-00-00";
    private String user   = "";
    private String msg    = "";
    private String year   = "0000";
    
    private int commit    = 0;
    private int continues = 0;
    
    private boolean daily = false;

    public Result(){

    }

    public Result(String date, int continues, boolean daily, String user){
        this.date = date;
        this.continues = continues;
        this.daily = daily;
        this.user = user;
    }

    public Result(String year, int commit, String user){
        this.year = year;
        this.commit = commit;
        this.user = user;
    }

}