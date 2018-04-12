package com.example.yeah1.sitter.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.example.yeah1.sitter.dto.ChattingMessage;
import com.example.yeah1.sitter.dto.ChattingRoom;
import com.example.yeah1.sitter.dto.SitterInfo;
import com.example.yeah1.sitter.dto.UserInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YEAH1 on 2017. 9. 23..
 */

public class SitterApplication extends Application {

    private UserInfo userInfo;
    private SitterInfo sitterInfo;
    private ActivityManager activityManager;
    private HashMap<String, ArrayList<ChattingMessage>> chattingMessageList;
    private ArrayList<ChattingRoom> chattingRoomArrayList;

    public HashMap<String, ArrayList<ChattingMessage>> getInstanceOfChattingMessageList() {
        if(chattingMessageList == null)
            chattingMessageList = new HashMap<>();

        return chattingMessageList;
    }

    public ArrayList<ChattingRoom> getInstanceOfChattingRoomArrayList() {
        if(chattingRoomArrayList == null)
            chattingRoomArrayList = new ArrayList<>();

        return chattingRoomArrayList;
    }



    public ActivityManager getInstanceOfActivityManager() {
        if(activityManager == null)
            activityManager = new ActivityManager();

        return activityManager;
    }

    public UserInfo getInstanceOfUserInfo() {
        if(userInfo == null)
            userInfo = new UserInfo();

        return userInfo;
    }

    public SitterInfo getInstanceOfSitterInfo() {
        if(sitterInfo == null)
            sitterInfo = new SitterInfo();

        return sitterInfo;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        setDefaultFont( this,"SERIF", "NanumBarunpenR.ttf" );
    }


    private void setDefaultFont(Context context,
                                String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    private void replaceFont(String staticTypefaceFieldName,
                             final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}