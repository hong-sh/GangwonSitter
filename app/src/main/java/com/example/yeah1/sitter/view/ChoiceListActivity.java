package com.example.yeah1.sitter.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.adapter.ChoiceListRecyclerviewAdapter;
import com.example.yeah1.sitter.dto.SitterCurrentItem;
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

public class ChoiceListActivity extends AppCompatActivity implements ReviewDialogFragment.OnFragmentInteractionListener , View.OnClickListener {

    private RecyclerView recyclerViewChoiceList;
    private ChoiceListRecyclerviewAdapter choiceRecyclerViewAdapter;
    private ArrayList<SitterCurrentItem> sitterCurrentItems;
    private RecyclerView.LayoutManager layoutManager;

    private ProgressBar progressBar;
    private ImageButton imageButtonBackButton;

    private SitterApplication app;
    private UserInfo userInfo;
    private ActivityManager activityManager;
    private String sitterEmail, reviewContent;
    private float reviewRate;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_list);

        app = (SitterApplication)getApplication();
        userInfo = app.getInstanceOfUserInfo();
        activityManager = app.getInstanceOfActivityManager();
        activityManager.addActivity(this);

        imageButtonBackButton = (ImageButton)findViewById(R.id.imagebutton_back);
        imageButtonBackButton.setOnClickListener(this);


        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        recyclerViewChoiceList = (RecyclerView)findViewById(R.id.recyclerview_choice_list);
        recyclerViewChoiceList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewChoiceList.setLayoutManager(layoutManager);

        sitterCurrentItems = new ArrayList<>();

        new ChoiceListAsyncTask().execute();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imagebutton_back:
                activityManager.removeActvity(this);
                break;
        }
    }


    public class ChoiceListAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = null;
                if(userInfo.isSitter()) {
                    jsonObject = HttpConnector.callGETMethod(URLDefine.SITTER_CHOICE_LIST_URL +"user_email="+userInfo.getUserEmail());
                } else {
                    jsonObject = HttpConnector.callGETMethod(URLDefine.CHOICE_LIST_URL +"user_email="+userInfo.getUserEmail());
                }

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {
                    Log.d("MyTag", "choice list : 2" +jsonObject.toString());
                    JSONArray choiceListArray = jsonObject.getJSONArray("choice_list");
                    for (int i = 0; i < choiceListArray.length(); i++) {
                        JSONObject choiceObject = choiceListArray.getJSONObject(i);
                        SitterCurrentItem choiceListItem = new SitterCurrentItem();

                        SitterCurrentItem sitterCurrentItem = new SitterCurrentItem();
                        sitterCurrentItem.setName(choiceObject.getString("user_name"));
                        sitterCurrentItem.setEmail(choiceObject.getString("user_email"));
                        String day = "";
                        if (choiceObject.getString("mon_hope").equals("1")) {
                            day += "월";
                        }
                        if (choiceObject.getString("tue_hope").equals("1")) {
                            day += "화";
                        }
                        if (choiceObject.getString("wed_hope").equals("1")) {
                            day += "수";
                        }
                        if (choiceObject.getString("thu_hope").equals("1")) {
                            day += "목";
                        }
                        if (choiceObject.getString("fri_hope").equals("1")) {
                            day += "금";
                        }
                        if (choiceObject.getString("sat_hope").equals("1")) {
                            day += "토";
                        }
                        if (choiceObject.getString("sun_hope").equals("1")) {
                            day += "일";
                        }
                        sitterCurrentItem.setDay(day);
                        String startTime = choiceObject.getString("start_time");
                        startTime = startTime.substring(0, 5);
                        String endTime = choiceObject.getString("end_time");
                        endTime = endTime.substring(0, 5);
                        sitterCurrentItem.setTime(startTime + " ~ " + endTime);
                        sitterCurrentItem.setHourlywage(Integer.parseInt(choiceObject.getString("money_for_hour")));
                        sitterCurrentItem.setHousework(Integer.parseInt(choiceObject.getString("household_cap")));
                        sitterCurrentItem.setPlay(Integer.parseInt(choiceObject.getString("play_cap")));
                        sitterCurrentItem.setStudy(Integer.parseInt(choiceObject.getString("edu_cap")));
                        sitterCurrentItem.setHeart(Integer.parseInt(choiceObject.getString("help_cap")));
                        sitterCurrentItem.setLanguage(Integer.parseInt(choiceObject.getString("language_cap")));
                        sitterCurrentItem.setWish(choiceObject.getString("etc_hope"));
                        sitterCurrentItem.setTerm(Integer.parseInt(choiceObject.getString("period")));
                        sitterCurrentItem.setAge(Integer.parseInt(choiceObject.getString("baby_age")));
                        sitterCurrentItems.add(sitterCurrentItem);
                    }

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
                choiceRecyclerViewAdapter = new ChoiceListRecyclerviewAdapter(ChoiceListActivity.this, sitterCurrentItems, R.layout.recyclerview_sitter_current_item, userInfo.isSitter());
                recyclerViewChoiceList.setAdapter(choiceRecyclerViewAdapter);

            } else {
                Toast.makeText(getApplicationContext(), "채택 리스트를 가져오는데 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }



    public class InsertReviewAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            HashMap<String, String> insertReviewEntry = new HashMap<>();
            insertReviewEntry.put("user_email", userInfo.getUserEmail());
            insertReviewEntry.put("sitter_email", sitterEmail);
            insertReviewEntry.put("review_contents", reviewContent);
            insertReviewEntry.put("review_rate", String.valueOf(reviewRate));

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.WRITE_REVIEW_URL, insertReviewEntry);
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
                Toast.makeText(getApplicationContext(), "리뷰를 등록 하였습니다!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "리뷰 등록에 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onFragmentInteraction(String sitterEmail, String contents, float rate) {
        this.sitterEmail = sitterEmail;
        this.reviewContent = contents;
        this.reviewRate = rate;
        new InsertReviewAsyncTask().execute();
    }
}
