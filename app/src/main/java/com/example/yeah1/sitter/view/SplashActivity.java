package com.example.yeah1.sitter.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.SitterInfo;
import com.example.yeah1.sitter.dto.UserInfo;
import com.example.yeah1.sitter.util.FirebaseBackgroundService;
import com.example.yeah1.sitter.util.HttpConnector;
import com.example.yeah1.sitter.util.SitterApplication;
import com.example.yeah1.sitter.util.URLDefine;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class SplashActivity extends AppCompatActivity {

    private SitterApplication app;
    private UserInfo userInfo;
    private SitterInfo sitterInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_splash);

        app = (SitterApplication)getApplication();
        userInfo = app.getInstanceOfUserInfo();
        userInfo.setmContext(this);
        userInfo.getPreferences();
        sitterInfo = app.getInstanceOfSitterInfo();



        Handler handler = new Handler();
        handler.postDelayed(new splashhandler(), 3000);
    }

    private class splashhandler implements Runnable {
        public void run() {

            if(userInfo.getUserEmail() != null && userInfo.getUserPass() != null) {
                new LoginAsyncTask().execute();
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class)); // 로딩이 끝난후 이동할 Activity
                SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
            }


        }
    }

    public class LoginAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            HashMap<String, String> loginEntry = new HashMap<>();
            Log.d("MyTag", "user email = " +userInfo.getUserEmail());
            Log.d("MyTag", "user_pass = " +userInfo.getUserPass());

            loginEntry.put("user_email", userInfo.getUserEmail());
            loginEntry.put("user_pass", userInfo.getUserPass());

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.LOGIN_URL, loginEntry);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {
                    JSONObject userInfoObject = jsonObject.getJSONObject("user_info");
                    userInfo.setUserEmail(userInfoObject.getString("user_email"));
                    userInfo.setUserName(userInfoObject.getString("user_name"));
                    userInfo.setUserPhone(userInfoObject.getString("user_phone"));
                    userInfo.setUserAddr(userInfoObject.getString("user_addr"));
                    userInfo.setmContext(getApplicationContext());
                    if (userInfoObject.getString("is_sitter").equals("1"))
                        userInfo.setSitter(true);
                    else
                        userInfo.setSitter(false);
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
            if (result.equals("true")) {
                if (userInfo.isSitter()) {
                    new SitterInfoAsyncTask().execute();
                } else {
                    Toast.makeText(getApplicationContext(), userInfo.getUserName() + "님 환영합니다!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
                }
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class)); // 로딩이 끝난후 이동할 Activity
                SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
            }
            super.onPostExecute(result);
        }
    }

    public class SitterInfoAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
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

            if (result.equals("true")) {
                Toast.makeText(getApplicationContext(), userInfo.getUserName() + "님 환영합니다!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
            } else {
//                Toast.makeText(getApplicationContext(), "시터 정보 가져오기에 실패하였습니다. 로그인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class)); // 로딩이 끝난후 이동할 Activity
                SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
            }
            super.onPostExecute(result);
        }
    }

}
