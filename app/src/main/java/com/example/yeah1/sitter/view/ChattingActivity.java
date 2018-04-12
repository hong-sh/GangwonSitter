package com.example.yeah1.sitter.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.adapter.ChattingRecyclerViewAdapter;
import com.example.yeah1.sitter.dto.ChattingMessage;
import com.example.yeah1.sitter.dto.UserInfo;
import com.example.yeah1.sitter.util.ActivityManager;
import com.example.yeah1.sitter.util.FirebaseBackgroundService;
import com.example.yeah1.sitter.util.SitterApplication;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ChattingActivity extends AppCompatActivity implements View.OnClickListener{

    private SitterApplication app;
    private HashMap<String, ArrayList<ChattingMessage>> chattingMessageArrayList;
    private UserInfo userInfo;
    private ActivityManager activityManager;

    private RecyclerView recyclerViewChatting;
    private ChattingRecyclerViewAdapter chattingRecyclerViewAdapter;

    private EditText editTextChatting;
    private Button buttonSend;

    private String chattingRoomName;
    private String receiverEmail;

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        Intent intent = getIntent();
        chattingRoomName = intent.getStringExtra("chattingRoomName");
        receiverEmail = intent.getStringExtra("receiverEmail");

        Intent receiverIntent = new Intent(this, FirebaseBackgroundService.class);


        app = (SitterApplication)getApplication();
        chattingMessageArrayList = app.getInstanceOfChattingMessageList();
        userInfo = app.getInstanceOfUserInfo();
        activityManager = app.getInstanceOfActivityManager();

        recyclerViewChatting = (RecyclerView)findViewById(R.id.recyclerView_chatting);

        chattingRecyclerViewAdapter = new ChattingRecyclerViewAdapter(getApplicationContext(), chattingMessageArrayList.get(chattingRoomName), userInfo.getUserEmail());
        recyclerViewChatting.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewChatting.setAdapter(chattingRecyclerViewAdapter);
        recyclerViewChatting.scrollToPosition(chattingRecyclerViewAdapter.getItemCount()-1);

        buttonSend = (Button)findViewById(R.id.button_send);
        buttonSend.setOnClickListener(this);
        editTextChatting = (EditText)findViewById(R.id.editText_chating);

        backButton = (ImageButton)findViewById(R.id.imagebutton_back);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_send:
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReferenceChatting = firebaseDatabase.getReference(chattingRoomName);
                ChattingMessage chattingMessage = new ChattingMessage();

                chattingMessage.setSender(userInfo.getUserEmail());
                chattingMessage.setContent(editTextChatting.getText().toString());
                chattingMessage.setReceiver(receiverEmail);
                DateFormat df = new SimpleDateFormat("MM-dd, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());
//        Date currentTime = Calendar.getInstance().getTime();
                chattingMessage.setDate(date);


                databaseReferenceChatting.push().setValue(chattingMessage);

                editTextChatting.setText("");
                break;
            case R.id.imagebutton_back:
                finish();
                break;
        }


    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateRecyclerView();
        }

    };

    private void updateRecyclerView() {

        chattingRecyclerViewAdapter.notifyDataSetChanged();
        recyclerViewChatting.invalidate();
        recyclerViewChatting.scrollToPosition(chattingRecyclerViewAdapter.getItemCount()-1);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(FirebaseBackgroundService.NEWCHATTINGMESSAGE_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
