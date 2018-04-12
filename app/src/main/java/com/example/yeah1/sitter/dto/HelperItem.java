package com.example.yeah1.sitter.dto;

/**
 * Created by YEAH1 on 2017. 9. 15..
 */

public class HelperItem {
    private String title;
    private String addr;
    private String number;

    public HelperItem() {}

    public HelperItem(String title, String addr, String number) {
        this.title = title;
        this.addr = addr;
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
