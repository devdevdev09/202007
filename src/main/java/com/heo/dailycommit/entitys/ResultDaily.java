package com.heo.dailycommit.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultDaily extends ResultDefault {
    // boolean 은 isDaily() 생성
    private String date;
    private int continues;
    private boolean daily;
    private String user;
}