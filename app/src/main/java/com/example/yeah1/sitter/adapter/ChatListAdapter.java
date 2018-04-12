package com.example.yeah1.sitter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.ChattingMessage;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 11. 19..
 */

public class ChatListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ChattingMessage> chatListDataArrayList;
    private String userEmail;

    public ChatListAdapter(Context context, ArrayList<ChattingMessage> chatListDataArrayList, String userEmail) {
        this.context = context;
        this.chatListDataArrayList = chatListDataArrayList;
        this.userEmail = userEmail;
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

        ChattingMessage chatListData = chatListDataArrayList.get(position);

        if(chatListData.getSender().equals(userEmail)) {
            TextView textViewSender = (TextView)convertView.findViewById(R.id.textView_chat_send);
            textViewSender.setVisibility(View.VISIBLE);
            textViewSender.setText(chatListData.getContent());
            TextView textViewSendDate = (TextView)convertView.findViewById(R.id.textView_send_date);
            textViewSendDate.setVisibility(View.VISIBLE);
            textViewSendDate.setText(chatListData.getDate());
        } else {
            TextView textViewReceiver = (TextView)convertView.findViewById(R.id.textView_chat_receive);
            textViewReceiver.setVisibility(View.VISIBLE);
            textViewReceiver.setText(chatListData.getContent());
            TextView textViewReceiveDate = (TextView)convertView.findViewById(R.id.textView_receive_date);
            textViewReceiveDate.setVisibility(View.VISIBLE);
            textViewReceiveDate.setText(chatListData.getDate());
        }

        return convertView;
    }

}
