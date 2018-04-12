package com.example.yeah1.sitter.dto;

/**
 * Created by hong on 2017. 11. 19..
 */

public class ChattingRoom {

    private String momName;
    private String momID;
    private String sitterName;
    private String sitterID;
    private String chattingRoomName;

    public ChattingRoom() {}

    public ChattingRoom(String momName, String momID, String sitterName, String sitterID, String chattingRoomName) {
        this.momName = momName;
        this.momID = momID;
        this.sitterName = sitterName;
        this.sitterID = sitterID;
        this.chattingRoomName = chattingRoomName;
    }

    public String getChattingRoomName() {
        return chattingRoomName;
    }

    public void setChattingRoomName(String chattingRoomName) {
        this.chattingRoomName = chattingRoomName;
    }

    public String getMomName() {
        return momName;
    }

    public void setMomName(String momName) {
        this.momName = momName;
    }

    public String getMomID() {
        return momID;
    }

    public void setMomID(String momID) {
        this.momID = momID;
    }

    public String getSitterName() {
        return sitterName;
    }

    public void setSitterName(String sitterName) {
        this.sitterName = sitterName;
    }

    public String getSitterID() {
        return sitterID;
    }

    public void setSitterID(String sitterID) {
        this.sitterID = sitterID;
    }
}
