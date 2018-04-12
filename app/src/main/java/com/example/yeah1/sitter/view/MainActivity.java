package com.example.yeah1.sitter.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.adapter.CurrentRecyclerViewAdapter;
import com.example.yeah1.sitter.adapter.HelperRecyclerViewAdapter;
import com.example.yeah1.sitter.adapter.SitterCurrentRecyclerViewAdapter;
import com.example.yeah1.sitter.dto.CurrentItem;
import com.example.yeah1.sitter.dto.HelperItem;
import com.example.yeah1.sitter.dto.SitterCurrentItem;
import com.example.yeah1.sitter.dto.UserInfo;
import com.example.yeah1.sitter.util.ActivityManager;
import com.example.yeah1.sitter.util.FirebaseBackgroundService;
import com.example.yeah1.sitter.util.HttpConnector;
import com.example.yeah1.sitter.util.SitterApplication;
import com.example.yeah1.sitter.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar progressBar;
    private RecyclerView recyclerViewhelper;
    private RecyclerView recyclerViewCurrent;
    private RecyclerView recyclerViewSitter;
    private RecyclerView.LayoutManager layoutManager;
    private HelperRecyclerViewAdapter helperRecyclerViewAdapter;
    private CurrentRecyclerViewAdapter currentRecyclerViewAdapter;
    private SitterCurrentRecyclerViewAdapter sitterCurrentRecyclerViewAdapter;
    private Menu menu;

    private ArrayList<HelperItem> helperItems;
    private ArrayList<CurrentItem> currentItems;
    private ArrayList<SitterCurrentItem> sitterCurrentItems;
    private SitterApplication app;
    private UserInfo userInfo;
    private ActivityManager activityManager;
    private String offerState;

    private RelativeLayout layoutNoApply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (SitterApplication) getApplication();
        userInfo = app.getInstanceOfUserInfo();
        activityManager = app.getInstanceOfActivityManager();
        activityManager.addActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu = navigationView.getMenu();
        View v = navigationView.getHeaderView(0);
        TextView textViewName = (TextView)v.findViewById(R.id.textview_nav_name);
        TextView textViewEmail = (TextView)v.findViewById(R.id.textview_nav_email);
        textViewName.setText(userInfo.getUserName());
        textViewEmail.setText(userInfo.getUserEmail());
        navigationView.setNavigationItemSelectedListener(this);

        recyclerViewCurrent = (RecyclerView) findViewById(R.id.recyclerview_main);
        recyclerViewCurrent.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCurrent.setLayoutManager(layoutManager);

        layoutNoApply = (RelativeLayout)findViewById(R.id.layout_noapply);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (!userInfo.isSitter()) {
            new OfferStateAsyncTask().execute();
        } else {
            MenuItem menuItem = menu.findItem(R.id.nav_register);
            new JobOfferListAsyncTask().execute();
            menuItem.setEnabled(false);
            MenuItem menuItem1 = menu.findItem(R.id.nav_current);
            menuItem1.setTitle("부모 구인 현황");
            MainActivity.this.invalidateOptionsMenu();
        }

        Intent backGroundServiceIntent = new Intent(MainActivity.this, FirebaseBackgroundService.class);
        startService(backGroundServiceIntent);

    }

    public class OfferStateAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.OFFER_STATE_URL + "user_email=" + userInfo.getUserEmail());

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {
                    offerState = jsonObject.getString("offer_state");
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
                MenuItem menuItem = menu.findItem(R.id.nav_register);
                if (offerState.equals("0")) {
                    new OpenDataAsyncTask().execute(URLDefine.OPENDATA_URLS);
                    menuItem.setEnabled(true);
                } else if (offerState.equals("1")) {
                    new ApplyListAsyncTask().execute();
                    menuItem.setEnabled(false);
                }
                MainActivity.this.invalidateOptionsMenu();

            } else {
                Toast.makeText(getApplicationContext(), "정보를 가져오는데 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        MenuItem menuItemRegister =  menu.findItem(R.id.nav_register);
        MenuItem menuItemCurrent =  menu.findItem(R.id.nav_current);
        MenuItem menuItemMypage =  menu.findItem(R.id.nav_mypage);
        MenuItem menuItemHelper =  menu.findItem(R.id.nav_helper);
        MenuItem menuItemChat =  menu.findItem(R.id.nav_chat);

        menuItemRegister.setChecked(false);
        menuItemCurrent.setChecked(false);
        menuItemMypage.setChecked(false);
        menuItemHelper.setChecked(false);
        menuItemChat.setChecked(false);
    }

    public class ApplyListAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";
            currentItems = new ArrayList<>();
            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.APPLY_LIST_URL + "user_email=" + userInfo.getUserEmail());

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONArray applyListArray = jsonObject.getJSONArray("apply_list");
                    for (int i = 0; i < applyListArray.length(); i++) {
                        JSONObject applyObject = applyListArray.getJSONObject(i);
                        CurrentItem currentItem = new CurrentItem();
                        currentItem.setJobOfferIdx(Integer.parseInt(applyObject.getString("job_offer_idx")));
                        currentItem.setUserEmail(applyObject.getString("user_email"));
                        currentItem.setName(applyObject.getString("user_name"));
                        currentItem.setCareer(Integer.parseInt(applyObject.getString("career")));
                        currentItem.setAge(Integer.parseInt(applyObject.getString("age")));
                        currentItem.setHousework(Integer.parseInt(applyObject.getString("household_cap")));
                        currentItem.setPlay(Integer.parseInt(applyObject.getString("play_cap")));
                        currentItem.setStudy(Integer.parseInt(applyObject.getString("edu_cap")));
                        currentItem.setHeart(Integer.parseInt(applyObject.getString("help_cap")));
                        currentItem.setLanguage(Integer.parseInt(applyObject.getString("language_cap")));
                        currentItem.setHourlywage(Integer.parseInt(applyObject.getString("hope_money_for_hour")));
                        currentItem.setSimilar(Integer.parseInt(applyObject.getString("similar")));
                        currentItems.add(currentItem);
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
                if(currentItems.size() == 0) {
                    layoutNoApply.setVisibility(View.VISIBLE);
                }else {
                    for(int i=0; i<currentItems.size()-1; i++) {
                        for(int j=0; j<currentItems.size()-1 - i; j++) {
                            if(currentItems.get(j).getSimilar() < currentItems.get(j+1).getSimilar()) {
                                CurrentItem currentItem = currentItems.get(j);
                                currentItems.set(j, currentItems.get(j+1));
                                currentItems.set(j+1, currentItem);
                            }
                        }
                    }
                    currentRecyclerViewAdapter = new CurrentRecyclerViewAdapter(MainActivity.this, currentItems, R.layout.recyclerview_current_item);
                    recyclerViewCurrent.setAdapter(currentRecyclerViewAdapter);
                }

            } else {
                layoutNoApply.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(), "지원 현황 정보를 가져오는데 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class OpenDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            layoutNoApply.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String result = "false";
            helperItems = new ArrayList<>();

            for (int i = 0; i < params.length; i++) {
                try {
                    JSONObject jsonObject = HttpConnector.callGETMethod(params[i]);
                    if (jsonObject != null) {
                        JSONObject dataObject = jsonObject.getJSONObject(URLDefine.RESULTKEY[i]);
                        if (dataObject.getJSONObject("RESULT").getString("MESSAGE").equals("정상 처리되었습니다.")) {
                            JSONArray rowArray = dataObject.getJSONArray("row");
                            for (int j = 0; j < rowArray.length(); j++) {
                                JSONObject helperObject = rowArray.getJSONObject(j);
                                HelperItem helperItem = new HelperItem();
                                helperItem.setTitle(helperObject.getString("DO_NM"));
                                helperItem.setAddr(helperObject.getString("ROAD_ADDRESS"));
                                helperItem.setNumber(helperObject.getString("PHONE_NO"));
                                helperItems.add(helperItem);
                            }
                        }
                        result = "true";
                    }


                } catch (JSONException e) {
                    Log.d("MyTag", e.toString());
                    e.printStackTrace();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {
                helperRecyclerViewAdapter = new HelperRecyclerViewAdapter(MainActivity.this, helperItems, R.layout.recyclerview_helper_item);
                recyclerViewCurrent.setAdapter(helperRecyclerViewAdapter);
            } else {
                Toast.makeText(getApplicationContext(), "산후조리사 제공 기관 정보를 가져오는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class JobOfferListAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            sitterCurrentItems = new ArrayList<>();

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.JOB_OFFER_LIST_URL+"user_email=" +userInfo.getUserEmail());

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONArray applyListArray = jsonObject.getJSONArray("apply_list");
                    for (int i = 0; i < applyListArray.length(); i++) {
                        JSONObject applyObject = applyListArray.getJSONObject(i);
                        SitterCurrentItem sitterCurrentItem = new SitterCurrentItem();
                        sitterCurrentItem.setName(applyObject.getString("user_name"));
                        sitterCurrentItem.setEmail(applyObject.getString("user_email"));
                        String day = "";
                        if (applyObject.getString("mon_hope").equals("1")) {
                            day += "월";
                        }
                        if (applyObject.getString("tue_hope").equals("1")) {
                            day += "화";
                        }
                        if (applyObject.getString("wed_hope").equals("1")) {
                            day += "수";
                        }
                        if (applyObject.getString("thu_hope").equals("1")) {
                            day += "목";
                        }
                        if (applyObject.getString("fri_hope").equals("1")) {
                            day += "금";
                        }
                        if (applyObject.getString("sat_hope").equals("1")) {
                            day += "토";
                        }
                        if (applyObject.getString("sun_hope").equals("1")) {
                            day += "일";
                        }
                        sitterCurrentItem.setDay(day);
                        sitterCurrentItem.setJobOfferIdx(Integer.parseInt(applyObject.getString("idx")));
                        String startTime = applyObject.getString("start_time");
                        startTime = startTime.substring(0, 5);
                        String endTime = applyObject.getString("end_time");
                        endTime = endTime.substring(0, 5);
                        sitterCurrentItem.setTime(startTime + " ~ " + endTime);
                        sitterCurrentItem.setHourlywage(Integer.parseInt(applyObject.getString("money_for_hour")));
                        sitterCurrentItem.setHousework(Integer.parseInt(applyObject.getString("household_cap")));
                        sitterCurrentItem.setPlay(Integer.parseInt(applyObject.getString("play_cap")));
                        sitterCurrentItem.setStudy(Integer.parseInt(applyObject.getString("edu_cap")));
                        sitterCurrentItem.setHeart(Integer.parseInt(applyObject.getString("help_cap")));
                        sitterCurrentItem.setLanguage(Integer.parseInt(applyObject.getString("language_cap")));
                        sitterCurrentItem.setWish(applyObject.getString("etc_hope"));
                        sitterCurrentItem.setTerm(Integer.parseInt(applyObject.getString("period")));
                        sitterCurrentItem.setSimilar(Integer.parseInt(applyObject.getString("similar")));
                        Log.d("MyTag", "similar = " +applyObject.getString("similar"));
                        sitterCurrentItem.setAge(Integer.parseInt(applyObject.getString("baby_age")));
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

                for(int i=0; i<sitterCurrentItems.size()-1; i++) {
                    for(int j=0; j<sitterCurrentItems.size()-1 - i; j++) {
                        if(sitterCurrentItems.get(j).getSimilar() < sitterCurrentItems.get(j+1).getSimilar()) {
                            SitterCurrentItem sitterCurrentItem = sitterCurrentItems.get(j);
                            sitterCurrentItems.set(j, sitterCurrentItems.get(j+1));
                            sitterCurrentItems.set(j+1, sitterCurrentItem);
                        }
                    }
                }

                sitterCurrentRecyclerViewAdapter = new SitterCurrentRecyclerViewAdapter(MainActivity.this, sitterCurrentItems, R.layout.recyclerview_sitter_current_item);
                recyclerViewCurrent.setAdapter(sitterCurrentRecyclerViewAdapter);
            } else {
                Toast.makeText(getApplicationContext(), "구인 리스트를 가져오는데 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit_count == 0) {
                Toast.makeText(this, "뒤로가기를 한번 더 누르시면 종료됩니다.",
                        Toast.LENGTH_SHORT).show();
                exitHandler.sendEmptyMessageDelayed(0, 2000);
                exit_count++;

            } else if (exit_count == 1) {
                activityManager.removeAllActvity();
                return;
            }
        }
    }

    int exit_count = 0;


    Handler exitHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                exit_count = 0;
            }
            return false;
        }
    });


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_register) {
            Toast.makeText(getApplicationContext(), "시터와 유사도 확인을 위한 육아 성향도 설문조사로 먼저 이동합니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_current) {
            if(!userInfo.isSitter()) {
                layoutNoApply.setVisibility(View.GONE);
                new ApplyListAsyncTask().execute();
            } else {
                layoutNoApply.setVisibility(View.GONE);
                new JobOfferListAsyncTask().execute();
            }

        } else if (id == R.id.nav_helper) {
            new OpenDataAsyncTask().execute(URLDefine.OPENDATA_URLS);

        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
            startActivity(intent);
        } else if(id == R.id.nav_chat) {
            Intent intent = new Intent(getApplicationContext(), ChattingRoomActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
