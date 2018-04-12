package com.example.yeah1.sitter.dto;

/**
 * Created by YEAH1 on 2017. 9. 17..
 */

public class SitterCurrentItem {
    private int jobOfferIdx;
    private String email;
    private String name;
    private int age;
    private String time;
    private int hourlywage;
    private String day;
    private int term;
    private String wish;
    private int similar;

    private int housework;
    private int play;
    private int study;
    private int heart;
    private int language;

    public SitterCurrentItem(){}

    public SitterCurrentItem(int similar, String email, String name, int age, String time, int hourlywage, String day, int term, String wish,
                             int housework, int play, int study, int heart, int language){

        this.similar = similar;
        this.email = email;
        this.name = name;
        this.age = age;
        this.time = time;
        this.hourlywage = hourlywage;
        this.day = day;
        this.term = term;
        this.wish = wish;
        this.housework = housework;
        this.play = play;
        this.study = study;
        this.heart = heart;
        this.language = language;

    }

    public int getJobOfferIdx() {
        return jobOfferIdx;
    }

    public void setJobOfferIdx(int jobOfferIdx) {
        this.jobOfferIdx = jobOfferIdx;
    }

    public int getSimilar() {
        return similar;
    }

    public void setSimilar(int similar) {
        this.similar = similar;
    }

    public String getWish() {
        return wish;
    }

    public void setWish(String wish) {
        this.wish = wish;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHourlywage() {
        return hourlywage;
    }

    public void setHourlywage(int hourlywage) {
        this.hourlywage = hourlywage;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getHousework() {
        return housework;
    }

    public void setHousework(int housework) {
        this.housework = housework;
    }

    public int getPlay() {
        return play;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    public int getStudy() {
        return study;
    }

    public void setStudy(int study) {
        this.study = study;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }
}
