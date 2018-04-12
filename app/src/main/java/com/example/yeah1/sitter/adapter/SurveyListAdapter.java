package com.example.yeah1.sitter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.SurveyListData;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 9. 27..
 */

public class SurveyListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SurveyListData> chatListDataArrayList;

    public SurveyListAdapter(Context context, ArrayList<SurveyListData> chatListDataArrayList) {
        this.context = context;
        this.chatListDataArrayList = chatListDataArrayList;
    }

    @Override
    public int getCount() {
        return chatListDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = parent.inflate(context, R.layout.listview_survey_item, null);

        SurveyListData chatListData = chatListDataArrayList.get(position);

        if(chatListData.isSend()) {
            TextView textViewSender = (TextView)convertView.findViewById(R.id.textView_chat_send);
            textViewSender.setVisibility(View.VISIBLE);
            textViewSender.setText(chatListData.getMessage());
        } else {
            TextView textViewReceiver = (TextView)convertView.findViewById(R.id.textView_chat_receive);
            textViewReceiver.setVisibility(View.VISIBLE);
            textViewReceiver.setText(chatListData.getMessage());
        }

        return convertView;
    }

}