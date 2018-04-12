package com.example.yeah1.sitter.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.SitterInfo;
import com.example.yeah1.sitter.dto.UserInfo;
import com.example.yeah1.sitter.util.ActivityManager;
import com.example.yeah1.sitter.util.SitterApplication;

public class MyPageActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout relativeLayoutChoice;
    private RelativeLayout relativeLayoutProfile;
    private RelativeLayout relativeLayoutCallCenter;

    private ImageButton imageButtonBack;
    private TextView textViewMyPageName, textViewMyPageEmail;
    private Button buttonLogout;

    private SitterApplication app;
    private UserInfo userInfo;
    private SitterInfo sitterInfo;
    private ActivityManager activityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        app = (SitterApplication)getApplication();
        userInfo = app.getInstanceOfUserInfo();
        sitterInfo = app.getInstanceOfSitterInfo();
        activityManager = app.getInstanceOfActivityManager();
        activityManager.addActivity(this);

        textViewMyPageName = (TextView)findViewById(R.id.textview_mypage_name);
        textViewMyPageEmail = (TextView)findViewById(R.id.textview_mypage_email);

        textViewMyPageName.setText(userInfo.getUserName());
        textViewMyPageEmail.setText(userInfo.getUserEmail());

        relativeLayoutChoice = (RelativeLayout)findViewById(R.id.relativelayout_choice);
        relativeLayoutProfile = (RelativeLayout)findViewById(R.id.relativelayout_profile);
        relativeLayoutCallCenter = (RelativeLayout)findViewById(R.id.relativelayout_center);

        imageButtonBack = (ImageButton)findViewById(R.id.imagebutton_back);
        buttonLogout = (Button)findViewById(R.id.button_logout);

        relativeLayoutChoice.setOnClickListener(this);
        relativeLayoutProfile.setOnClickListener(this);
        relativeLayoutCallCenter.setOnClickListener(this);

        imageButtonBack.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relativelayout_choice:
                Intent intent = new Intent(getApplicationContext(), ChoiceListActivity.class);
                startActivity(intent);
                break;
            case R.id.relativelayout_profile:
                Intent intent1 = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.imagebutton_back:
                activityManager.removeActvity(this);
                break;
            case R.id.button_logout:
                userInfo.removeLogin();
                userInfo = null;
                sitterInfo = null;
                activityManager.removeAllActvity();
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다. 로그인 화면으로 이동 합니다.", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent2);
                break;
            case R.id.relativelayout_center:
                Intent intent3 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-9879-7770"));
                startActivity(intent3);
                break;
        }
    }
}
