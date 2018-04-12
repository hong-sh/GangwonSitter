package com.example.yeah1.sitter.dto;

/**
 * Created by YEAH1 on 2017. 9. 23..
 */

public class ChoiceListItem {
    private String email;
    private String name;
    private int age;
    private int career;
    private int hourlywage;
    private int similar;

    private int housework;
    private int play;
    private int study;
    private int heart;
    private int language;

    public ChoiceListItem(String email, String name, int age, int career, int hourlywage, int similar, int housework, int play, int study, int heart, int language) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.career = career;
        this.hourlywage = hourlywage;
        this.similar = similar;
        this.housework = housework;
        this.play = play;
        this.study = study;
        this.heart = heart;
        this.language = language;
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

    public int getCareer() {
        return career;
    }

    public void setCareer(int career) {
        this.career = career;
    }

    public int getHourlywage() {
        return hourlywage;
    }

    public void setHourlywage(int hourlywage) {
        this.hourlywage = hourlywage;
    }

    public int getSimilar() {
        return similar;
    }

    public void setSimilar(int similar) {
        this.similar = similar;
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
