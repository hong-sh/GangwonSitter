package com.example.yeah1.sitter.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.UserInfo;
import com.example.yeah1.sitter.util.ActivityManager;
import com.example.yeah1.sitter.util.SitterApplication;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonChange;
    private ImageButton imageButtonBack;
    private EditText editTextEmail, editTextPass, editTextName, editTextPhone, editTextAddr;
    private String email, pass, name, phone, addr;

    private SitterApplication app;
    private UserInfo userInfo;
    private ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        app = (SitterApplication)getApplication();
        userInfo = app.getInstanceOfUserInfo();
        activityManager = app.getInstanceOfActivityManager();
        activityManager.addActivity(this);

        buttonChange = (Button)findViewById(R.id.button_change);
        buttonChange.setOnClickListener(this);
        imageButtonBack = (ImageButton)findViewById(R.id.imagebutton_back);
        imageButtonBack.setOnClickListener(this);

        editTextEmail = (EditText)findViewById(R.id.edittext_email);
        editTextEmail.setText(userInfo.getUserEmail());
        editTextEmail.setFocusable(false);

        editTextPass = (EditText)findViewById(R.id.edittext_pass);

        editTextName = (EditText)findViewById(R.id.edittext_name);
        editTextName.setText(userInfo.getUserName());

        editTextPhone = (EditText)findViewById(R.id.edittext_phone);
        editTextPhone.setText(userInfo.getUserPhone());

        editTextAddr = (EditText)findViewById(R.id.edittext_addr);
        editTextAddr.setText(userInfo.getUserAddr());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_change:
                break;
            case R.id.imagebutton_back:
                activityManager.removeActvity(this);
                break;
        }


    }
}
