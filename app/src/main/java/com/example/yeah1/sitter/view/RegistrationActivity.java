package com.example.yeah1.sitter.view;

import android.app.TimePickerDialog;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.UserInfo;
import com.example.yeah1.sitter.util.ActivityManager;
import com.example.yeah1.sitter.util.HttpConnector;
import com.example.yeah1.sitter.util.SitterApplication;
import com.example.yeah1.sitter.util.URLDefine;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonApply;

    private EditText editTextAge;
    private EditText editTextHourlywage;
    private EditText editTextTerm;
    private EditText editTextWish;

    private Button buttonMon;
    private Button buttonTue;
    private Button buttonWed;
    private Button buttonThu;
    private Button buttonFri;
    private Button buttonSat;
    private Button buttonSun;

    private Button buttonHousework;
    private Button buttonPlay;
    private Button buttonStudy;
    private Button buttonHeart;
    private Button buttonLanguage;

    private String[] arrayday = new String[7];
    private String[] arrayAbility = new String[5];

    private TextView textviewTime1;
    private TextView textViewTime2;
    private TimePickerDialog startTimePickerDialog;
    private TimePickerDialog endTimePickerDialog;

    private ImageButton imageButtonBack;

    private int hour = 0;
    private int min = 0;

    private ProgressBar progressBar;
    private SitterApplication app;
    private UserInfo userInfo;
    private ActivityManager activityManager;

    private String babyAge, startTime, endTime, hourlyWage, period, wish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        app = (SitterApplication) getApplication();
        userInfo = app.getInstanceOfUserInfo();
        activityManager = app.getInstanceOfActivityManager();
        activityManager.addActivity(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        editTextAge = (EditText) findViewById(R.id.edittext_age);
        editTextHourlywage = (EditText) findViewById(R.id.edittext_hourlywage);
        editTextTerm = (EditText) findViewById(R.id.edittext_term);
        editTextWish = (EditText) findViewById(R.id.edittext_wish);

        Arrays.fill(arrayday, "0");
        buttonMon = (Button) findViewById(R.id.button_mon);
        buttonTue = (Button) findViewById(R.id.button_tue);
        buttonWed = (Button) findViewById(R.id.button_wed);
        buttonThu = (Button) findViewById(R.id.button_thu);
        buttonFri = (Button) findViewById(R.id.button_fri);
        buttonSat = (Button) findViewById(R.id.button_sat);
        buttonSun = (Button) findViewById(R.id.button_sun);

        imageButtonBack = (ImageButton)findViewById(R.id.imagebutton_back);
        imageButtonBack.setOnClickListener(this);

        buttonMon.setOnClickListener(this);
        buttonTue.setOnClickListener(this);
        buttonWed.setOnClickListener(this);
        buttonThu.setOnClickListener(this);
        buttonFri.setOnClickListener(this);
        buttonSat.setOnClickListener(this);
        buttonSun.setOnClickListener(this);


        textviewTime1 = (TextView) findViewById(R.id.textview_time1);
        textviewTime1.setOnClickListener(this);
        textViewTime2 = (TextView) findViewById(R.id.textview_time2);
        textViewTime2.setOnClickListener(this);

        startTimePickerDialog = new TimePickerDialog(this, startTimeListener, hour, min, true);
        endTimePickerDialog = new TimePickerDialog(this, endTimeListener, hour, min, true);

        Arrays.fill(arrayAbility, "0");
        buttonHousework = (Button) findViewById(R.id.button_housework);
        buttonPlay = (Button) findViewById(R.id.button_play);
        buttonStudy = (Button) findViewById(R.id.button_study);
        buttonHeart = (Button) findViewById(R.id.button_heart);
        buttonLanguage = (Button) findViewById(R.id.button_language);

        buttonHousework.setOnClickListener(this);
        buttonPlay.setOnClickListener(this);
        buttonStudy.setOnClickListener(this);
        buttonHeart.setOnClickListener(this);
        buttonLanguage.setOnClickListener(this);

        buttonApply = (Button) findViewById(R.id.button_apply);
        buttonApply.setOnClickListener(this);


    }

    private TimePickerDialog.OnTimeSetListener endTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String hourStr = String.valueOf(hourOfDay);
            String minuteStr = String.valueOf(minute);
            if(hourOfDay < 10 )
               hourStr = "0" + hourStr;
            if(minute < 10)
               minuteStr = "0" +minuteStr;

            endTime = hourStr + ":" + minuteStr;
            textViewTime2.setText(endTime);
        }
    };

    private TimePickerDialog.OnTimeSetListener startTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String hourStr = String.valueOf(hourOfDay);
            String minuteStr = String.valueOf(minute);
            if(hourOfDay < 10 )
                hourStr = "0" + hourStr;
            if(minute < 10)
                minuteStr = "0" +minuteStr;

            startTime = hourStr + ":" + minuteStr;
            textviewTime1.setText(startTime);
        }
    };

    public class RegistJobOfferAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            HashMap<String, String> registJobOfferEntry = new HashMap<>();
            registJobOfferEntry.put("user_email", userInfo.getUserEmail());
            registJobOfferEntry.put("mon_hope", arrayday[0]);
            registJobOfferEntry.put("tue_hope", arrayday[1]);
            registJobOfferEntry.put("wed_hope", arrayday[2]);
            registJobOfferEntry.put("thu_hope", arrayday[3]);
            registJobOfferEntry.put("fri_hope", arrayday[4]);
            registJobOfferEntry.put("sat_hope", arrayday[5]);
            registJobOfferEntry.put("sun_hope", arrayday[6]);
            registJobOfferEntry.put("baby_age", babyAge);
            registJobOfferEntry.put("start_time", startTime);
            registJobOfferEntry.put("end_time", endTime);
            registJobOfferEntry.put("money_for_hour", hourlyWage);
            registJobOfferEntry.put("household_cap", arrayAbility[0]);
            registJobOfferEntry.put("play_cap", arrayAbility[1]);
            registJobOfferEntry.put("edu_cap", arrayAbility[2]);
            registJobOfferEntry.put("help_cap", arrayAbility[3]);
            registJobOfferEntry.put("language_cap", arrayAbility[4]);
            registJobOfferEntry.put("etc_hope", wish);
            registJobOfferEntry.put("period", period);

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.REGIST_JOB_OFFER_URL, registJobOfferEntry);

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
                Toast.makeText(getApplicationContext(), "구인 등록을 완료하였습니다!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "구인 등록에 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_mon:
                if (arrayday[0] == "0") {
                    arrayday[0] = "1";
                    buttonMon.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayday[0] = "0";
                    buttonMon.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;
            case R.id.button_tue:
                if (arrayday[1] == "0") {
                    arrayday[1] = "1";
                    buttonTue.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayday[1] = "0";
                    buttonTue.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;
            case R.id.button_wed:
                if (arrayday[2] == "0") {
                    arrayday[2] = "1";
                    buttonWed.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayday[2] = "0";
                    buttonWed.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;
            case R.id.button_thu:
                if (arrayday[3] == "0") {
                    arrayday[3] = "1";
                    buttonThu.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayday[3] = "0";
                    buttonThu.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;
            case R.id.button_fri:
                if (arrayday[4] == "0") {
                    arrayday[4] = "1";
                    buttonFri.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayday[4] = "0";
                    buttonFri.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;
            case R.id.button_sat:
                if (arrayday[5] == "0") {
                    arrayday[5] = "1";
                    buttonSat.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayday[5] = "0";
                    buttonSat.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;
            case R.id.button_sun:
                if (arrayday[6] == "0") {
                    arrayday[6] = "1";
                    buttonSun.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    arrayday[6] = "0";
                    buttonSun.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_stroke_edittext));
                }
                break;

            case R.id.textview_time1:
                startTimePickerDialog.show();
                break;

            case R.id.textview_time2:
                endTimePickerDialog.show();
                break;

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
            case R.id.button_apply:
                babyAge = editTextAge.getText().toString();
                if(babyAge.length() == 0) {
                    Toast.makeText(getApplicationContext(), "아이 나이를 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                hourlyWage = editTextHourlywage.getText().toString();
                if(hourlyWage.length() == 0) {
                    Toast.makeText(getApplicationContext(), "희망 시급을 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                period = editTextTerm.getText().toString();
                if(period.length() == 0) {
                    Toast.makeText(getApplicationContext(), "희망 기간 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                wish = editTextWish.getText().toString();
                if(wish.length() == 0) {
                    Toast.makeText(getApplicationContext(), "기타 희망 사항을 입력해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(startTime.length() == 0) {
                    Toast.makeText(getApplicationContext(), "근무 시작 시간을 설정해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(endTime.length() == 0) {
                    Toast.makeText(getApplicationContext(), "근무 종료 시간을 설정해 주세요. ", Toast.LENGTH_SHORT).show();
                    break;
                }
                new RegistJobOfferAsyncTask().execute();
                break;

            case R.id.imagebutton_back:
                activityManager.removeActvity(RegistrationActivity.this);
                break;

        }

    }
}
