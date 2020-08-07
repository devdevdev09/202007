package com.heo.dailycommit.error;

public enum SlackErrors {
    NULLDATA("0001","DATA가 없습니다."),
    HOOKS_NULL("0002","WEBHOOK URL을 확인하세요");

    private String code;
    private String description;

    private SlackErrors(String code, String description){
        this.code = code;
        this.description = description;
    }

    public String getCode(){
        return this.code;
    }

    public String getDescription(){
        return this.description;
    }
}