package com.example.yeah1.sitter.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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

import java.util.Arrays;
import java.util.HashMap;

public class SitterInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextAge, editTextCareer, editTextIntro;
    private Button buttonHousework;
    private Button buttonPlay;
    private Button buttonStudy;
    private Button buttonHeart;
    private Button buttonLanguage;
    private Button buttonSitterInfo;
    private ImageButton imageButtonBack;

    private SitterApplication app;
    private ProgressBar progressBar;
    private UserInfo userInfo;
    private SitterInfo sitterInfo;
    private ActivityManager activityManager;

    private String[] arrayAbility = new String[5];

    private String userCareer, userAge, userIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_info);

        app = (SitterApplication) getApplication();
        userInfo = app.getInstanceOfUserInfo();
        sitterInfo = app.getInstanceOfSitterInfo();
        activityManager = app.getInstanceOfActivityManager();
        activityManager.addActivity(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        editTextAge = (EditText) findViewById(R.id.edittext_age);
        editTextCareer = (EditText) findViewById(R.id.edittext_carrer);
        editTextIntro = (EditText) findViewById(R.id.edittext_intro);

        Arrays.fill(arrayAbility, "0");
        buttonHousework = (Button) findViewById(R.id.button_housework);
        buttonPlay = (Button) findViewById(R.id.button_play);
        buttonStudy = (Button) findViewById(R.id.button_study);
        buttonHeart = (Button) findViewById(R.id.button_heart);
        buttonLanguage = (Button) findViewById(R.id.button_language);
        buttonSitterInfo = (Button) findViewById(R.id.button_sitter_Info);
        imageButtonBack = (ImageButton)findViewById(R.id.imagebutton_back);

        buttonHousework.setOnClickListener(this);
        buttonPlay.setOnClickListener(this);
        buttonStudy.setOnClickListener(this);
        buttonHeart.setOnClickListener(this);
        buttonLanguage.setOnClickListener(this);
        buttonSitterInfo.setOnClickListener(this);
        imageButtonBack.setOnClickListener(this);

    }

    public class InsertSitterInfoAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            HashMap<String, String> sitterInfoEntry = new HashMap<>();
            sitterInfoEntry.put("user_email", userInfo.getUserEmail());
            sitterInfoEntry.put("career", userCareer);
            sitterInfoEntry.put("age", userAge);
            sitterInfoEntry.put("household_cap", arrayAbility[0]);
            sitterInfoEntry.put("play_cap", arrayAbility[1]);
            sitterInfoEntry.put("edu_cap", arrayAbility[2]);
            sitterInfoEntry.put("help_cap", arrayAbility[3]);
            sitterInfoEntry.put("language_cap", arrayAbility[4]);
            sitterInfoEntry.put("introduce", userIntro);

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.INSERT_SITTER_INFO_URL, sitterInfoEntry);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {
                    sitterInfo.setUserEmail(userInfo.getUserEmail());
                    sitterInfo.setCareer(userCareer);
                    sitterInfo.setAge(userAge);
                    sitterInfo.setHousework(Integer.parseInt(arrayAbility[0]));
                    sitterInfo.setPlay(Integer.parseInt(arrayAbility[1]));
                    sitterInfo.setStudy(Integer.parseInt(arrayAbility[2]));
                    sitterInfo.setHeart(Integer.parseInt(arrayAbility[3]));
                    sitterInfo.setLanguage(Integer.parseInt(arrayAbility[4]));
                    sitterInfo.setIntroduce(userIntro);

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
                Toast.makeText(getApplicationContext(), userInfo.getUserName() + "님 시터 정보 저장 완료! 유사도 확인을 위한 육아 성향도 설문조사로 이동합니다.", Toast.LENGTH_SHORT).show();
                activityManager.removeActvity(SitterInfoActivity.this);
                Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "시터 정보 저장이 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_housework:
                if (arrayAbility[0] == "0") {
                    arrayAbility[0] = "1";
                    buttonHousework.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayAbility[0] = "0";
                    buttonHousework.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;
            case R.id.button_play:
                if (arrayAbility[1] == "0") {
                    arrayAbility[1] = "1";
                    buttonPlay.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayAbility[1] = "0";
                    buttonPlay.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;
            case R.id.button_study:
                if (arrayAbility[2] == "0") {
                    arrayAbility[2] = "1";
                    buttonStudy.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayAbility[2] = "0";
                    buttonStudy.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;
            case R.id.button_heart:
                if (arrayAbility[3] == "0") {
                    arrayAbility[3] = "1";
                    buttonHeart.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayAbility[3] = "0";
                    buttonHeart.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;
            case R.id.button_language:
                if (arrayAbility[4] == "0") {
                    arrayAbility[4] = "1";
                    buttonLanguage.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayAbility[4] = "0";
                    buttonLanguage.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;
            case R.id.button_sitter_Info:
                userCareer = editTextCareer.getText().toString();
                if(userCareer.length() == 0) {
                    Toast.makeText(getApplicationContext(), "경력을 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                userAge = editTextAge.getText().toString();
                if(userAge.length() == 0) {
                    Toast.makeText(getApplicationContext(), "나이를 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                userIntro = editTextIntro.getText().toString();
                if(userIntro.length() == 0) {
                    Toast.makeText(getApplicationContext(), "자기소개를 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                new InsertSitterInfoAsyncTask().execute();
                break;
            case R.id.imagebutton_back:
                activityManager.removeActvity(SitterInfoActivity.this);
                break;

        }
    }
}
