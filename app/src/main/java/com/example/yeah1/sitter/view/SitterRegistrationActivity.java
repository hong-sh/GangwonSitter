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

public class SitterRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonHousework;
    private Button buttonPlay;
    private Button buttonStudy;
    private Button buttonHeart;
    private Button buttonLanguage;
    private Button buttonRegistration;

    private ImageButton imageButtonBack;

    private EditText editTextAge;
    private EditText editTextCareer;
    private EditText editTextHourlywage;
    private EditText editTextIntroduce;
    private EditText editTextAppeal;

    private String applyAge, applyCareer, applyHourlyWage, applyIntroduce, applyAppeal;

    private ProgressBar progressBar;

    private String[] arrayAbility = new String[5];

    private SitterApplication app;
    private UserInfo userInfo;
    private SitterInfo sitterInfo;
    private ActivityManager activityManager;
    private int jobOfferIdx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_registration);

        Intent intent = getIntent();
        jobOfferIdx = intent.getIntExtra("job_offer_idx", 0);
        String hourlyWage = intent.getStringExtra("hourlywage");

        app = (SitterApplication) getApplication();
        userInfo = app.getInstanceOfUserInfo();
        sitterInfo = app.getInstanceOfSitterInfo();
        activityManager = app.getInstanceOfActivityManager();
        activityManager.addActivity(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        imageButtonBack = (ImageButton)findViewById(R.id.imagebutton_back);
        imageButtonBack.setOnClickListener(this);

        Arrays.fill(arrayAbility, "0");
        editTextAge = (EditText) findViewById(R.id.edittext_age);
        editTextAge.setText(sitterInfo.getAge());
        editTextCareer = (EditText) findViewById(R.id.edittext_carrer);
        editTextCareer.setText(sitterInfo.getCareer());
        editTextHourlywage = (EditText) findViewById(R.id.edittext_hourlywage);
        editTextHourlywage.setText(hourlyWage);
        editTextIntroduce = (EditText) findViewById(R.id.edittext_intro);
        editTextIntroduce.setText(sitterInfo.getIntroduce());
        editTextAppeal = (EditText) findViewById(R.id.edittext_appeal);

        buttonHousework = (Button) findViewById(R.id.button_housework);
        buttonPlay = (Button) findViewById(R.id.button_play);
        buttonStudy = (Button) findViewById(R.id.button_study);
        buttonHeart = (Button) findViewById(R.id.button_heart);
        buttonLanguage = (Button) findViewById(R.id.button_language);

        buttonRegistration = (Button)findViewById(R.id.button_sitter_registration);
        buttonRegistration.setOnClickListener(this);

        if (sitterInfo.getHousework() == 1) {
            arrayAbility[0] = "1";
            buttonHousework.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if (sitterInfo.getPlay() == 1) {
            arrayAbility[1] = "1";
            buttonPlay.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if (sitterInfo.getStudy() == 1) {
            arrayAbility[2] = "1";
            buttonStudy.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if (sitterInfo.getHeart() == 1) {
            arrayAbility[3] = "1";
            buttonHeart.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if (sitterInfo.getLanguage() == 1) {
            arrayAbility[4] = "1";
            buttonLanguage.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        buttonHousework.setOnClickListener(this);
        buttonPlay.setOnClickListener(this);
        buttonStudy.setOnClickListener(this);
        buttonHeart.setOnClickListener(this);
        buttonLanguage.setOnClickListener(this);


    }


    public class ApplyAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";
//user_email, job_offer_idx, hope_money_for_hour, appeal
            HashMap<String, String> applyEntry = new HashMap<>();
            applyEntry.put("user_email", userInfo.getUserEmail());
            applyEntry.put("job_offer_idx", String.valueOf(jobOfferIdx));
            applyEntry.put("hope_money_for_hour", applyHourlyWage);
            applyEntry.put("appeal", applyAppeal);

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.APPLY_URL, applyEntry);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {
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
                Toast.makeText(getApplicationContext(), "지원이 완료되었습니다. 좋은 결과 있길 바랍니다!", Toast.LENGTH_SHORT).show();
                activityManager.removeActvity(SitterRegistrationActivity.this);
            } else {
                Toast.makeText(getApplicationContext(), "지원이 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
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
            case R.id.button_sitter_registration:
                applyAge = editTextAge.getText().toString();
                if(applyAge.length() == 0) {
                    Toast.makeText(getApplicationContext(), "나이를 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                applyCareer = editTextCareer.getText().toString();
                if(applyCareer.length() == 0) {
                    Toast.makeText(getApplicationContext(), "경력을 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                applyAppeal = editTextAppeal.getText().toString();
                if(applyAppeal.length() == 0) {
                    Toast.makeText(getApplicationContext(), "어필 내용을 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                applyHourlyWage = editTextHourlywage.getText().toString();
                if(applyHourlyWage.length() == 0) {
                    Toast.makeText(getApplicationContext(), "희망 시급을 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                applyIntroduce = editTextIntroduce.getText().toString();
                if(applyIntroduce.length() == 0) {
                    Toast.makeText(getApplicationContext(), "자기소개를 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                new ApplyAsyncTask().execute();
                break;

            case R.id.imagebutton_back:
                activityManager.removeActvity(SitterRegistrationActivity.this);
                break;
        }
    }
}
