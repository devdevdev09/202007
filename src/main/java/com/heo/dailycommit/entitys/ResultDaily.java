package com.heo.dailycommit.entitys;

import lombok.Data;

@Data
public class ResultDaily {
    private boolean daily;
    private String date;
    private String user;
    private int continues;

    public ResultDaily(String date, int continues, boolean daily, String user){
        this.date = date;
        this.continues = continues;
        this.daily = daily;
        this.user = user;
    }
}