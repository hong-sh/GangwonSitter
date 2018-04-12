package com.example.yeah1.sitter.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.SitterInfo;
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


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textviewJoin;
    private Button buttonLogin;
    private EditText editTextEmail, editTextPass;
    private ProgressBar progressBar;
    private String userEmail, userPass;
    private SitterApplication app;
    private UserInfo userInfo;
    private SitterInfo sitterInfo;
    private ActivityManager activityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        app = (SitterApplication) getApplication();
        userInfo = app.getInstanceOfUserInfo();
        sitterInfo = app.getInstanceOfSitterInfo();
        activityManager = app.getInstanceOfActivityManager();
        activityManager.addActivity(this);

        textviewJoin = (TextView) findViewById(R.id.textview_join);
        textviewJoin.setOnClickListener(this);
        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(this);
        editTextEmail = (EditText) findViewById(R.id.edittext_email);
        editTextPass = (EditText) findViewById(R.id.edittext_pass);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

    }


    public class LoginAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            HashMap<String, String> loginEntry = new HashMap<>();
            loginEntry.put("user_email", userEmail);
            loginEntry.put("user_pass", userPass);

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.LOGIN_URL, loginEntry);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {
                    JSONObject userInfoObject = jsonObject.getJSONObject("user_info");
                    userInfo.setUserEmail(userInfoObject.getString("user_email"));
                    userInfo.setUserName(userInfoObject.getString("user_name"));
                    userInfo.setUserPhone(userInfoObject.getString("user_phone"));
                    userInfo.setUserAddr(userInfoObject.getString("user_addr"));
                    userInfo.setmContext(getApplicationContext());
                    userInfo.setUserPass(userPass);
                    if (userInfoObject.getString("is_sitter").equals("1"))
                        userInfo.setSitter(true);
                    else
                        userInfo.setSitter(false);
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
                if (userInfo.isSitter()) {
                    new SitterInfoAsyncTask().execute();
                } else {
                    Toast.makeText(getApplicationContext(), userInfo.getUserName() + "님 환영합니다!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "로그인이 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class SitterInfoAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.SITTER_INFO_URL + "user_email=" + userInfo.getUserEmail());

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONObject sitterInfoObject = jsonObject.getJSONObject("sitter_info");
                    sitterInfo.setUserEmail(userInfo.getUserEmail());
                    sitterInfo.setCareer(sitterInfoObject.getString("career"));
                    sitterInfo.setAge(sitterInfoObject.getString("age"));
                    sitterInfo.setHousework(Integer.parseInt(sitterInfoObject.getString("household_cap")));
                    sitterInfo.setPlay(Integer.parseInt(sitterInfoObject.getString("play_cap")));
                    sitterInfo.setStudy(Integer.parseInt(sitterInfoObject.getString("edu_cap")));
                    sitterInfo.setHeart(Integer.parseInt(sitterInfoObject.getString("help_cap")));
                    sitterInfo.setLanguage(Integer.parseInt(sitterInfoObject.getString("language_cap")));
                    sitterInfo.setIntroduce(sitterInfoObject.getString("introduce"));

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
                Toast.makeText(getApplicationContext(), userInfo.getUserName() + "님 환영합니다!", Toast.LENGTH_SHORT).show();
                activityManager.removeActvity(LoginActivity.this);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_join:
                activityManager.removeActvity(LoginActivity.this);
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);
                break;
            case R.id.button_login:
                userEmail = editTextEmail.getText().toString();
                if (!isValidEmail(userEmail)) {
                    Toast.makeText(getApplicationContext(), "이메일 형식에 맞지 않습니다. ", Toast.LENGTH_SHORT).show();
                      break;
                  }
                userPass = editTextPass.getText().toString();
                new LoginAsyncTask().execute();

                break;
        }
    }

    public boolean isValidEmail(String inputStr) {

        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(inputStr);

        if (!m.matches()) {
            return false;
        }
        return true;
    }
}
