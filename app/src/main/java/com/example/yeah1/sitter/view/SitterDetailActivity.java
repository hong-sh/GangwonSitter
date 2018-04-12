package com.example.yeah1.sitter.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.adapter.SitterReviewListViewAdapter;
import com.example.yeah1.sitter.dto.ChattingMessage;
import com.example.yeah1.sitter.dto.ChattingRoom;
import com.example.yeah1.sitter.dto.CurrentItem;
import com.example.yeah1.sitter.dto.SitterReviewItem;
import com.example.yeah1.sitter.dto.UserInfo;
import com.example.yeah1.sitter.util.HttpConnector;
import com.example.yeah1.sitter.util.SitterApplication;
import com.example.yeah1.sitter.util.URLDefine;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;



public class SitterDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView listViewSitterReview;
    private View header;
    private View footer;

    private TextView textViewTitle;
    private ImageButton imageButtonBack;

    private ImageView imageViewHousework;
    private ImageView imageViewPlay;
    private ImageView imageViewStudy;
    private ImageView imageViewHeart;
    private ImageView imageViewLanguage;

    private TextView textViewHousework;
    private TextView textViewPlay;
    private TextView textViewStudy;
    private TextView textViewHeart;
    private TextView textViewLanguage;

    private TextView textViewAge;
    private TextView textViewCareer;
    private TextView textViewhourlywage;
    private TextView textViewSimilar;

    private TextView textViewIntroduce;
    private TextView textViewAppeal;

    private ProgressBar progressBar;

    private Button buttonMeeting, buttonChoice, buttonOnlineMeeting;

    private ArrayList<SitterReviewItem> itemsReview;
    private SitterReviewListViewAdapter sitterReviewListViewAdapter;

    private CurrentItem currentItem;
    private String sitterEmail, sitterName;
    private String applyIntroduce, applyAppeal;
    private int jobOfferIdx, hourlywage;

    private SitterApplication app;
    private UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitter_detail);

        app = (SitterApplication)getApplication();
        userInfo = app.getInstanceOfUserInfo();

        Intent intent = getIntent();
        sitterEmail = intent.getStringExtra("user_email");
        sitterName = intent.getStringExtra("name");
        int housework = intent.getIntExtra("housework", 0);
        int play = intent.getIntExtra("play", 0);
        int study = intent.getIntExtra("study", 0);
        int heart = intent.getIntExtra("heart",0);
        int language = intent.getIntExtra("language", 0);
        int age = intent.getIntExtra("age", 0);
        int career = intent.getIntExtra("career", 0);
        hourlywage = intent.getIntExtra("hourlywage", 0);
        int similar = intent.getIntExtra("similar", 0);
        jobOfferIdx = intent.getIntExtra("job_offer_idx", 0);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        textViewTitle = (TextView)findViewById(R.id.textview_appbar_sitter);
        textViewTitle.setText(sitterName + "님");
        textViewTitle.setTextColor(Color.BLACK);

        imageButtonBack = (ImageButton)findViewById(R.id.imagebutton_back);
        imageButtonBack.setOnClickListener(this);

        currentItem = new CurrentItem(similar, sitterEmail, sitterName,age,career,hourlywage, housework, play, study,heart,language);

        listViewSitterReview = (ListView)findViewById(R.id.listview_sitter_review);

        header = getLayoutInflater().inflate(R.layout.listview_sitter_detail_header,null, false);
        listViewSitterReview.addHeaderView(header);
        initHeader();

        footer = getLayoutInflater().inflate(R.layout.listview_sitter_review_footer, null, false);
        listViewSitterReview.addFooterView(footer);

        buttonMeeting = (Button)footer.findViewById(R.id.button_meeting);
        buttonChoice = (Button)footer.findViewById(R.id.button_choice);
        buttonOnlineMeeting = (Button)footer.findViewById(R.id.button_online_meeting);

        buttonMeeting.setOnClickListener(this);
        buttonChoice.setOnClickListener(this);
        buttonOnlineMeeting.setOnClickListener(this);

        itemsReview = new ArrayList<>();

    }

    public void initHeader(){

        imageViewHousework = (ImageView)header.findViewById(R.id.imageview_housework);
        imageViewPlay = (ImageView)header.findViewById(R.id.imageview_play);
        imageViewStudy = (ImageView)header.findViewById(R.id.imageview_study);
        imageViewHeart = (ImageView)header.findViewById(R.id.imageview_heart);
        imageViewLanguage = (ImageView)header.findViewById(R.id.imageview_language);

        textViewHousework = (TextView)header.findViewById(R.id.textview_housework);
        textViewPlay = (TextView)header.findViewById(R.id.textview_play);
        textViewStudy = (TextView)header.findViewById(R.id.textview_study);
        textViewHeart = (TextView)header.findViewById(R.id.textview_heart);
        textViewLanguage = (TextView)header.findViewById(R.id.textview_language);

        textViewAge = (TextView)header.findViewById(R.id.textview_age);
        textViewCareer = (TextView)header.findViewById(R.id.textview_career);
        textViewhourlywage = (TextView)header.findViewById(R.id.textview_hourlywage);
        textViewSimilar = (TextView)header.findViewById(R.id.textview_similar);

        textViewIntroduce = (TextView)header.findViewById(R.id.textview_introduce);
        textViewAppeal = (TextView)header.findViewById(R.id.textview_appeal);

        if(currentItem.getHousework() == 1){
            imageViewHousework.setColorFilter(Color.parseColor("#f4b183"));
            textViewHousework.setTextColor(Color.BLACK);
        }

        if(currentItem.getPlay() == 1){
            imageViewPlay.setColorFilter(Color.parseColor("#a9d18e"));
            textViewPlay.setTextColor(Color.BLACK);
        }

        if(currentItem.getStudy() == 1){
            imageViewStudy.setColorFilter(Color.parseColor("#9dc3e6"));
            textViewStudy.setTextColor(Color.BLACK);
        }

        if(currentItem.getHeart() == 1){
            imageViewHeart.setColorFilter(Color.parseColor("#ffd966"));
            textViewHeart.setTextColor(Color.BLACK);
        }

        if(currentItem.getLanguage() == 1){
            imageViewLanguage.setColorFilter(Color.parseColor("#c00000"));
            textViewLanguage.setTextColor(Color.BLACK);
        }

        textViewAge.setText(currentItem.getAge()+" 세");
        textViewCareer.setText(currentItem.getCareer()+" 년");
        textViewhourlywage.setText(currentItem.getHourlywage() + " 원");
        textViewSimilar.setText(currentItem.getSimilar() + " %");

        new ApplyDetailAsyncTask().execute();

    }

    public class ApplyDetailAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";
            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.APPLY_DETAIL_URL + "user_email=" + sitterEmail);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONObject applyDetailObject = jsonObject.getJSONObject("apply_detail");
                    applyIntroduce = applyDetailObject.getString("introduce");
                    applyAppeal = applyDetailObject.getString("appeal");

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
                textViewIntroduce.setText(applyIntroduce);
                textViewAppeal.setText(applyAppeal);
                new ReviewListAsyncTask().execute();
            } else {
                Toast.makeText(getApplicationContext(), "시터 정보를 불러오는데 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class ReviewListAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.REVIEW_LIST_URL + "user_email=" + sitterEmail);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONArray reviewListArray = jsonObject.getJSONArray("review_list");
                    for(int i=0; i<reviewListArray.length(); i++) {
                        JSONObject reviewObject = reviewListArray.getJSONObject(i);
                        SitterReviewItem sitterReviewItem = new SitterReviewItem();
                        sitterReviewItem.setRate(Float.parseFloat(reviewObject.getString("review_rate")));
                        sitterReviewItem.setName(reviewObject.getString("review_contents"));
                        sitterReviewItem.setContent(reviewObject.getString("user_name"));
                        itemsReview.add(sitterReviewItem);
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
                sitterReviewListViewAdapter = new SitterReviewListViewAdapter(SitterDetailActivity.this, itemsReview, R.layout.listveiw_sitter_review_item);
                listViewSitterReview.setAdapter(sitterReviewListViewAdapter);

            } else {
                Toast.makeText(getApplicationContext(), "등록된 리뷰가 없습니다. ", Toast.LENGTH_SHORT).show();
                sitterReviewListViewAdapter = new SitterReviewListViewAdapter(SitterDetailActivity.this, itemsReview, R.layout.listveiw_sitter_review_item);
                listViewSitterReview.setAdapter(sitterReviewListViewAdapter);
            }
            super.onPostExecute(result);
        }
    }

    public class ChoiceAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            //ser_email(string), sitter_email(string), job_offer_idx(int), money_for_hour(int)

            String result = "false";

            HashMap<String, String> choiceEntry = new HashMap<>();
            choiceEntry.put("user_email", userInfo.getUserEmail());
            choiceEntry.put("sitter_email", sitterEmail);
            choiceEntry.put("job_offer_idx", String.valueOf(jobOfferIdx));
            choiceEntry.put("money_for_hour", String.valueOf(hourlywage));

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.CHOICE_URL, choiceEntry);
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
                Toast.makeText(getApplicationContext(), sitterName + "님을 채택 하였습니다! 저희쪽에서 연락드리겠습니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "채택에 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagebutton_back:
                finish();
                break;
            case R.id.button_online_meeting:
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();
                String chattingRoomName = "chattingRoom:" +System.currentTimeMillis()+System.nanoTime();
                ChattingRoom chattingRoom = new ChattingRoom(userInfo.getUserName(), userInfo.getUserEmail(), sitterName, sitterEmail, chattingRoomName);
                databaseReference.getRoot().child(sitterName).push().setValue(chattingRoom);
                databaseReference.getRoot().child(userInfo.getUserName()).push().setValue(chattingRoom);
                ChattingMessage chattingMessage = new ChattingMessage(userInfo.getUserEmail(), sitterEmail, "직거래의 오해가 있는 연락처 및 개인정보 요청은 제재를 받을 수 있습니다 ", "관리자");
                databaseReference.getRoot().child(chattingRoomName).push().setValue(chattingMessage);
                break;
            case R.id.button_meeting:
                FragmentManager fm = getSupportFragmentManager();
                MeetingFragment meetingFragment = MeetingFragment.newInstance();
                meetingFragment.show(fm, "meeting");
                break;
            case R.id.button_choice:
                new ChoiceAsyncTask().execute();
                break;
        }
    }
}
