package com.heo.dailycommit.entitys;

import lombok.Data;

@Data
public class ResultYearly {
    private String user;
    private String msg;
    private String year;
    private int commit;

    public ResultYearly(String year, int commit, String user){
        this.year = year;
        this.commit = commit;
        this.user = user;
    }
}