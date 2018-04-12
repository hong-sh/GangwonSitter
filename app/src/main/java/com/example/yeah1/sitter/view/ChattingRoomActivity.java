package com.example.yeah1.sitter.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.adapter.ChattingRoomRecyclerViewAdapter;
import com.example.yeah1.sitter.dto.ChattingRoom;
import com.example.yeah1.sitter.dto.UserInfo;
import com.example.yeah1.sitter.util.ActivityManager;
import com.example.yeah1.sitter.util.SitterApplication;

import java.util.ArrayList;

public class ChattingRoomActivity extends AppCompatActivity {

    private SitterApplication app;
    private UserInfo userInfo;
    private ArrayList<ChattingRoom> chattingRoomArrayList;
    private ActivityManager activityManager;

    private RecyclerView recyclerViewChattingRoom;
    private ChattingRoomRecyclerViewAdapter chattingRoomRecyclerViewAdapter;

    private ImageButton imageButtonBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);

        app = (SitterApplication)getApplication();
        userInfo = app.getInstanceOfUserInfo();
        chattingRoomArrayList = app.getInstanceOfChattingRoomArrayList();
        activityManager = app.getInstanceOfActivityManager();


        recyclerViewChattingRoom  = (RecyclerView)findViewById(R.id.recyclerview_chattingRoom);
        chattingRoomRecyclerViewAdapter = new ChattingRoomRecyclerViewAdapter(ChattingRoomActivity.this, chattingRoomArrayList, userInfo.isSitter());

        recyclerViewChattingRoom.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerViewChattingRoom.setAdapter(chattingRoomRecyclerViewAdapter);

        imageButtonBack = (ImageButton)findViewById(R.id.imagebutton_back);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
