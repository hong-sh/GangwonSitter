package com.example.yeah1.sitter.dto;

/**
 * Created by hong on 2017. 9. 27..
 */

public class SurveyListData {

    private String message;
    private boolean send;

    public SurveyListData(){}

    public SurveyListData(String message, boolean send) {
        this.message = message;
        this.send = send;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}
