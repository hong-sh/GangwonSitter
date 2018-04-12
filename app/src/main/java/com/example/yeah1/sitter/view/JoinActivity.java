package com.example.yeah1.sitter.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.util.ActivityManager;
import com.example.yeah1.sitter.util.SitterApplication;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton imageButtonBack;
    private LinearLayout linearLayoutParents;
    private LinearLayout linearLayoutSitter;
    private SitterApplication app;
    private ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        app = (SitterApplication)getApplication();
        activityManager = app.getInstanceOfActivityManager();

        imageButtonBack = (ImageButton)findViewById(R.id.imagebutton_back);
        linearLayoutParents = (LinearLayout)findViewById(R.id.linearlayout_join_parents);
        linearLayoutSitter = (LinearLayout)findViewById(R.id.linearlayout_join_sitter);

        imageButtonBack.setOnClickListener(this);
        linearLayoutParents.setOnClickListener(this);
        linearLayoutSitter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getApplicationContext(), JoinDetailActivity.class);
        switch (v.getId()){
            case R.id.imagebutton_back:
                activityManager.removeActvity(JoinActivity.this);
                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.linearlayout_join_parents:
                intent.putExtra("is_sitter","0");
                startActivity(intent);
                break;
            case R.id.linearlayout_join_sitter:
                intent.putExtra("is_sitter","1");
                startActivity(intent);
                break;


        }
    }


}
