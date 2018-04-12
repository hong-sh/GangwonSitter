package com.example.yeah1.sitter.dto;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hong on 2017. 9. 27..
 */

public class SurveyData {


    private HashMap<Integer, String> surveyQeustionMap;
    private HashMap<Integer, ArrayList<String>> surveyAnswerMap;

    public SurveyData() {
        surveyQeustionMap = new HashMap<>();
        surveyAnswerMap = new HashMap<>();
    }

    public void addSurveyQuestion(int num, String question) {
        surveyQeustionMap.put(num, question);
    }

    public void addSurveyAnswer(int num, ArrayList<String> answer) {
        surveyAnswerMap.put(num, answer);
    }

    public String getSurveyItem(int num) {
        StringBuilder sb = new StringBuilder();

        String question = surveyQeustionMap.get(num);
        sb.append("Q" +num+". " +question +"\n\n");

        ArrayList<String> answerList = surveyAnswerMap.get(num);
        for(int i=0; i<answerList.size(); i++) {
            sb.append("\tA" +(i+1) +". " +answerList.get(i) +"\n");
        }
        return sb.toString();
    }

    public String getSurveyQuestion(int num) {
        return surveyQeustionMap.get(num);
    }

    public ArrayList<String> getSurveyAnswer(int num) {
        return surveyAnswerMap.get(num);
    }
}
