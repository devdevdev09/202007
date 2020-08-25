package com.heo.dailycommit.collection;

import java.util.ArrayList;
import java.util.List;

import com.heo.dailycommit.entitys.ResultYearly;

import lombok.Data;

@Data
public class ResultYearlyList {
    private List<ResultYearly> list = new ArrayList<ResultYearly>();

    public void add(ResultYearly resultYearly){
        this.list.add(resultYearly);
    }
}