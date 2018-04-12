package com.example.yeah1.sitter.dto;

/**
 * Created by hong on 2017. 11. 18..
 */

public class ChattingMessage {
    private String sender;
    private String receiver;
    private String content;
    private String date;

    public ChattingMessage(){}

    public ChattingMessage(String sender, String receiver, String content, String date) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
