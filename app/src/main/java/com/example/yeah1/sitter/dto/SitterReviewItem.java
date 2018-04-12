package com.example.yeah1.sitter.dto;

/**
 * Created by YEAH1 on 2017. 9. 17..
 */

public class SitterReviewItem {

    private String content;
    private String name;
    private float rate;

    public SitterReviewItem(){}

    public SitterReviewItem(String content, String name, float rate){
        this.content = content;
        this.name = name;
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
