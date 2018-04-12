package com.example.yeah1.sitter.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.UserInfo;
import com.example.yeah1.sitter.util.ActivityManager;
import com.example.yeah1.sitter.util.HttpConnector;
import com.example.yeah1.sitter.util.SitterApplication;
import com.example.yeah1.sitter.util.URLDefine;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton imageButtonBack;
    private Button buttonJoin;

    private EditText editTextEmail, editTextPass, editTextName, editTextPhone, editTextAddr;
    private ProgressBar progressBar;
    private String userEmail, userPass, userName, userPhone, userAddr, isSitter;

    private SitterApplication app;
    private UserInfo userInfo;
    private ActivityManager activityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_detail);

        app = (SitterApplication)getApplication();
        userInfo = app.getInstanceOfUserInfo();
        activityManager = app.getInstanceOfActivityManager();
        activityManager.addActivity(JoinDetailActivity.this);

        imageButtonBack = (ImageButton)findViewById(R.id.imagebutton_back);
        imageButtonBack.setOnClickListener(this);
        buttonJoin = (Button)findViewById(R.id.button_join);
        buttonJoin.setOnClickListener(this);

        editTextEmail = (EditText)findViewById(R.id.edittext_email);
        editTextPass = (EditText)findViewById(R.id.edittext_pass);
        editTextName = (EditText)findViewById(R.id.edittext_name);
        editTextPhone = (EditText)findViewById(R.id.edittext_phone);
        editTextAddr = (EditText)findViewById(R.id.edittext_addr);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Intent intent = getIntent();
        isSitter = intent.getStringExtra("is_sitter");
    }


    public class JoinAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            HashMap<String, String> joinEntry = new HashMap<>();
            joinEntry.put("user_email", userEmail);
            joinEntry.put("user_pass", userPass);
            joinEntry.put("user_name", userName);
            joinEntry.put("user_phone", userPhone);
            joinEntry.put("user_addr", userAddr);
            joinEntry.put("is_sitter", isSitter);

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.JOIN_URL, joinEntry);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {
                    userInfo.setUserEmail(userEmail);
                    userInfo.setUserName(userName);
                    userInfo.setUserPhone(userPhone);
                    userInfo.setUserAddr(userAddr);
                    userInfo.setUserPass(userPass);
                    if(isSitter.equals("0"))
                        userInfo.setSitter(false);
                    else if(isSitter.equals("1"))
                        userInfo.setSitter(true);
                    userInfo.saveLogin();

                    result = "true";
                }


            } catch (JSONException e) {
                Log.d("MyTag", e.toString());
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {
                Toast.makeText(getApplicationContext(), userInfo.getUserName() +"님 회원가입 완료!", Toast.LENGTH_SHORT).show();

                if (isSitter.equals("0")){
                    activityManager.removeActvity(JoinDetailActivity.this);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else if(isSitter.equals("1")){
                    activityManager.removeActvity(JoinDetailActivity.this);
                    Intent intent = new Intent(getApplicationContext(), SitterInfoActivity.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(getApplicationContext(), "로그인이 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_back:
                activityManager.removeActvity(JoinDetailActivity.this);
                break;
            case R.id.button_join:
                userEmail = editTextEmail.getText().toString();
                if (!isValidEmail(userEmail)) {
                    Toast.makeText(getApplicationContext(), "이메일 형식에 맞지 않습니다. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                userPass = editTextPass.getText().toString();
                if(userPass.length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                userName = editTextName.getText().toString();
                if(userName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                userPhone = editTextPhone.getText().toString();
                if(userPhone.length() == 0) {
                    Toast.makeText(getApplicationContext(), "전화번호를 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                userAddr = editTextAddr.getText().toString();
                if(userAddr.length() == 0) {
                    Toast.makeText(getApplicationContext(), "주소를 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                new JoinAsyncTask().execute();
                break;
        }
    }

    public boolean isValidEmail(String inputStr) {

        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(inputStr);

        if( !m.matches() ) {
            return false;
        }
        return true;
    }
}
