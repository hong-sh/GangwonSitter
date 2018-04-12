package com.example.yeah1.sitter.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.adapter.SurveyListAdapter;
import com.example.yeah1.sitter.dto.SurveyData;
import com.example.yeah1.sitter.dto.SurveyListData;
import com.example.yeah1.sitter.dto.UserInfo;
import com.example.yeah1.sitter.util.ActivityManager;
import com.example.yeah1.sitter.util.HttpConnector;
import com.example.yeah1.sitter.util.SitterApplication;
import com.example.yeah1.sitter.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SurveyActivity extends AppCompatActivity implements View.OnClickListener{


    private ListView listViewSurvey;
    private Button buttonAnswer1, buttonAnswer2, buttonAnswer3;
    private ProgressBar progressBar;
    private ImageButton imageButtonBack;

    private SurveyListAdapter surveyListAdapter;
    private ArrayList<SurveyListData> chatListDataArrayList;

    private SurveyData surveyData;
    private HashMap<String, String> surveyEntry;
    private int surveyCount = 0;

    private SitterApplication app;
    private UserInfo userInfo;
    private ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        app = (SitterApplication)getApplication();
        userInfo = app.getInstanceOfUserInfo();
        activityManager = app.getInstanceOfActivityManager();
        activityManager.addActivity(this);

        initLayout();

        surveyData = new SurveyData();
        surveyEntry = new HashMap<>();

        new SurveyListTask().execute();
    }

    private void initLayout() {
        listViewSurvey = (ListView) findViewById(R.id.listView_survey);
        chatListDataArrayList = new ArrayList<>();
        surveyListAdapter = new SurveyListAdapter(SurveyActivity.this, chatListDataArrayList);
        listViewSurvey.setAdapter(surveyListAdapter);

        buttonAnswer1 = (Button) findViewById(R.id.button_answer1);
        buttonAnswer2 = (Button) findViewById(R.id.button_answer2);
        buttonAnswer3 = (Button) findViewById(R.id.button_answer3);
        imageButtonBack = (ImageButton)findViewById(R.id.imagebutton_back);
        imageButtonBack.setOnClickListener(this);
        buttonAnswer1.setOnClickListener(this);
        buttonAnswer2.setOnClickListener(this);
        buttonAnswer3.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public class SurveyListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.SURVEY_LIST_URL);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONArray surveyList = jsonObject.getJSONArray("survey_list");

                    for (int i = 0; i < surveyList.length(); i++) {

                        JSONObject surveyObject = surveyList.getJSONObject(i);
                        int surveyNum = Integer.parseInt(surveyObject.getString("survey_num"));
                        surveyData.addSurveyQuestion(surveyNum, surveyObject.getString("survey_txt"));

                        ArrayList<String> answerList = new ArrayList<>();
                        JSONArray answerArray = surveyObject.getJSONArray("answer");
                        for (int j = 0; j < answerArray.length(); j++) {
                            JSONObject answerObject = answerArray.getJSONObject(j);
                            answerList.add(answerObject.getString("answer_txt"));
                        }
                        surveyData.addSurveyAnswer(surveyNum, answerList);
                    }

                    result = "true";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {
                surveyCount = 1;
                showSurveyQuestion();
            } else {
                Toast.makeText(getApplicationContext(), "설문 조사 문항을 가져오는데 실패하였습니다.", Toast.LENGTH_SHORT).show();

            }
            super.onPostExecute(result);
        }
    }

    private void showSurveyQuestion() {
        SurveyListData chatListData = new SurveyListData(surveyData.getSurveyItem(surveyCount), false);
        updateListView(chatListData);
    }

    private void updateListView(SurveyListData chatListData) {
        chatListDataArrayList.add(chatListData);
        surveyListAdapter.notifyDataSetChanged();
        listViewSurvey.invalidate();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.imagebutton_back) {
            activityManager.removeActvity(this);
            return;
        }

        String buttonStr = ((Button) v).getText().toString();
        surveyEntry.put("user_answer" + surveyCount, buttonStr);
        SurveyListData chatListData = new SurveyListData(buttonStr, true);
        if (surveyCount == 19) {
            new InsertSurveyAsyncTask().execute();
            return;
        } else if (surveyCount > 19){
            return;
        }
        surveyCount++;
        updateListView(chatListData);
        showSurveyQuestion();
    }

    public class InsertSurveyAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            surveyEntry.put("user_email", userInfo.getUserEmail());

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.INSERT_USER_SURVEY_URL, surveyEntry);

                if (jsonObject != null && jsonObject.getString("result").equals("true"))
                    result = "true";


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
                Toast.makeText(getApplicationContext(), "설문 조사 내용을 저장하였습니다", Toast.LENGTH_SHORT).show();
                if(userInfo.isSitter()) {
                    activityManager.removeActvity(SurveyActivity.this);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    activityManager.removeActvity(SurveyActivity.this);
                    Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "설문 조사 저장에 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }


}
