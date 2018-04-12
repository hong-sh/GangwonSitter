package com.example.yeah1.sitter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.ChattingRoom;
import com.example.yeah1.sitter.view.ChattingActivity;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 11. 19..
 */

public class ChattingRoomRecyclerViewAdapter extends RecyclerView.Adapter<ChattingRoomRecyclerViewAdapter.ViewHolder> implements View.OnClickListener  {

    private Context context;
    private ArrayList<ChattingRoom> items;
    private boolean isSitter;

    public ChattingRoomRecyclerViewAdapter(Context context, ArrayList<ChattingRoom> items, boolean isSitter) {
        this.context = context;
        this.items = items;
        this.isSitter = isSitter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chattingroom_item, null);
        view.setMinimumWidth(parent.getMinimumWidth());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChattingRoomRecyclerViewAdapter.ViewHolder holder, int position) {

        ChattingRoom chattingRoom = items.get(position);
        if(isSitter) {
            holder.textViewName.setText(chattingRoom.getMomName());
            holder.textViewEmail.setText(chattingRoom.getMomID());
        } else {
            holder.textViewName.setText(chattingRoom.getSitterName());
            holder.textViewEmail.setText(chattingRoom.getSitterID());
        }

        holder.layoutChattingRoom.setTag(position);
        holder.layoutChattingRoom.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View v) {
        int idx = (int)v.getTag();
        Activity activity = null;
        if(context instanceof Activity) {
            activity = (Activity)context;
        }
        if (activity != null) {
            Intent intent = new Intent(activity, ChattingActivity.class);
            intent.putExtra("chattingRoomName", items.get(idx).getChattingRoomName());
            if(isSitter)
                intent.putExtra("receiverEmail", items.get(idx).getMomID());
            else
                intent.putExtra("receiverEmail", items.get(idx).getSitterID());

            activity.startActivity(intent);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layoutChattingRoom;
        public TextView textViewName;
        public TextView textViewEmail;

        public ViewHolder(View itemView) {
            super(itemView);

            layoutChattingRoom = (LinearLayout) itemView.findViewById(R.id.layout_chattingRoom);
            textViewName = (TextView) itemView.findViewById(R.id.textView_name);
            textViewEmail = (TextView) itemView.findViewById(R.id.textView_email);
        }
    }
}
