package com.example.yeah1.sitter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.ChattingMessage;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 11. 19..
 */

public class ChattingRecyclerViewAdapter extends RecyclerView.Adapter<ChattingRecyclerViewAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<ChattingMessage> items;
    private String userEmail;

    public ChattingRecyclerViewAdapter(Context context, ArrayList<ChattingMessage> items, String userEmail) {
        this.context = context;
        this.items = items;
        this.userEmail = userEmail;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chat_item, null);
        view.setMinimumWidth(parent.getMinimumWidth());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChattingRecyclerViewAdapter.ViewHolder holder, int position) {

        ChattingMessage chattingMessage = items.get(position);
        holder.setIsRecyclable(false);
        if(chattingMessage.getSender().equals(userEmail)) {
            holder.textViewChatSend.setVisibility(View.VISIBLE);
            holder.textViewSenderDate.setVisibility(View.VISIBLE);
            holder.textViewChatSend.setText(chattingMessage.getContent());
            holder.textViewSenderDate.setText(chattingMessage.getDate());
        } else if(chattingMessage.getReceiver().equals(userEmail)){
            holder.textViewChatReceive.setVisibility(View.VISIBLE);
            holder.textViewReceiverDate.setVisibility(View.VISIBLE);
            holder.textViewChatReceive.setText(chattingMessage.getContent());
            holder.textViewReceiverDate.setText(chattingMessage.getDate());
        }



    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View layoutChat;
        public TextView textViewChatSend;
        public TextView textViewSenderDate;
        public TextView textViewChatReceive;
        public TextView textViewReceiverDate;


        public ViewHolder(View itemView) {
            super(itemView);
            layoutChat = (View)itemView.findViewById(R.id.layout_chat);
            textViewChatSend = (TextView) itemView.findViewById(R.id.textView_chat_send);
            textViewSenderDate = (TextView) itemView.findViewById(R.id.textView_send_date);
            textViewChatReceive = (TextView) itemView.findViewById(R.id.textView_chat_receive);
            textViewReceiverDate = (TextView) itemView.findViewById(R.id.textView_receive_date);
        }
    }
}