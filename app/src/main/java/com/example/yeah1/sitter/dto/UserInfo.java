package com.example.yeah1.sitter.dto;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by YEAH1 on 2017. 9. 23..
 */

public class UserInfo {

    private String userEmail;
    private String userPass;
    private String userName;
    private String userPhone;
    private String userAddr;
    private boolean sitter;
    private Context mContext;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public boolean isSitter() {
        return sitter;
    }

    public void setSitter(boolean sitter) {
        this.sitter = sitter;
    }

    public void saveLogin() {
        SharedPreferences pref = this.mContext.getSharedPreferences("login", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_email", this.userEmail);
        editor.putString("user_pass", this.userPass);
        editor.commit();

    }

    public void removeLogin(){

        SharedPreferences pref = this.mContext.getSharedPreferences("login", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

        this.userEmail = null;
        this.userPass = null;
    }

    public void getPreferences(){
        SharedPreferences pref = mContext.getSharedPreferences("login", mContext.MODE_PRIVATE);
        this.userEmail = pref.getString("user_email", null);
        this.userPass = pref.getString("user_pass", null);
    }
}
