package com.heo.dailycommit.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultYearly extends ResultDefault {
    private String user;
    private int commit;
    private String year;
}