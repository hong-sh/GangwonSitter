package com.example.yeah1.sitter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.SitterCurrentItem;
import com.example.yeah1.sitter.util.CircularProgressBar;
import com.example.yeah1.sitter.view.SitterRegistrationActivity;

import java.util.List;

/**
 * Created by YEAH1 on 2017. 9. 17..
 */

public class SitterCurrentRecyclerViewAdapter extends RecyclerView.Adapter<SitterCurrentRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private AppCompatActivity activity;
    private List<SitterCurrentItem> items;
    private int item_layout;

    public SitterCurrentRecyclerViewAdapter(Context context, List<SitterCurrentItem> items, int item_layout){
        this.context = context;
        if(context instanceof AppCompatActivity)
            activity = (AppCompatActivity)context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public SitterCurrentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_sitter_current_item, null);
        view.setMinimumWidth(parent.getMinimumWidth());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SitterCurrentRecyclerViewAdapter.ViewHolder holder, int position) {

        SitterCurrentItem item = items.get(position);

        holder.circularProgressBarSitter.setProgress(item.getSimilar());
        holder.circularProgressBarSitter.setProgressColor(Color.parseColor("#be465d"));
        holder.textViewName.setText(item.getName()+ "님");

        if(item.getHousework() == 1){
            holder.imageViewHousework.setColorFilter(Color.parseColor("#f4b183"));
            holder.textViewHousework.setTextColor(Color.BLACK);
        }
        if(item.getPlay() == 1){
            holder.imageViewPlay.setColorFilter(Color.parseColor("#a9d18e"));
            holder.textViewPlay.setTextColor(Color.BLACK);
        }
        if(item.getStudy() == 1){
            holder.imageViewStudy.setColorFilter(Color.parseColor("#9dc3e6"));
            holder.textViewStudy.setTextColor(Color.BLACK);
        }
        if(item.getHeart() == 1){
            holder.imageViewHeart.setColorFilter(Color.parseColor("#ffd966"));
            holder.textViewHeart.setTextColor(Color.BLACK);
        }
        if(item.getLanguage() == 1){
            holder.imageViewLanguage.setColorFilter(Color.parseColor("#c00000"));
            holder.textViewLanguage.setTextColor(Color.BLACK);
        }


        holder.textViewAge.setText(String.valueOf(item.getAge()) + " 세");
        holder.textViewHourlywage.setText(item.getHourlywage()+ " 원");
        holder.textViewTerm.setText(item.getTerm()+" 개월");
        holder.textViewTime.setText(item.getTime());
        holder.textViewDay.setText(item.getDay());

        holder.textViewWish.setText(item.getWish());
        holder.buttonApply.setOnClickListener(this);
        holder.buttonApply.setTag(position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View v) {
        Activity activity = null;
        if(context instanceof Activity)
            activity = (Activity)context;
        if(activity != null) {
            int index = (int)v.getTag();
            SitterCurrentItem item = items.get(index);
            Intent intent = new Intent(activity, SitterRegistrationActivity.class);
            intent.putExtra("job_offer_idx", item.getJobOfferIdx());
            intent.putExtra("hourlywage", item.getHourlywage());
            activity.startActivity(intent);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayoutSitterCurrent;
        public CircularProgressBar circularProgressBarSitter;
        public TextView textViewName;

        public TextView textViewAge;
        public TextView textViewTime;
        public TextView textViewHourlywage;
        public TextView textViewDay;
        public TextView textViewTerm;
        public TextView textViewWish;

        public ImageView imageViewHousework;
        public ImageView imageViewPlay;
        public ImageView imageViewStudy;
        public ImageView imageViewHeart;
        public ImageView imageViewLanguage;

        public TextView textViewHousework;
        public TextView textViewPlay;
        public TextView textViewStudy;
        public TextView textViewHeart;
        public TextView textViewLanguage;

        public Button buttonApply;

        public ViewHolder(View itemView) {
            super(itemView);

            linearLayoutSitterCurrent = (LinearLayout)itemView.findViewById(R.id.layout_sitter_current);
            circularProgressBarSitter = (CircularProgressBar)itemView.findViewById(R.id.progressbar_percent);
            textViewName = (TextView)itemView.findViewById(R.id.textview_name);

            textViewAge = (TextView)itemView.findViewById(R.id.textview_age);
            textViewTime = (TextView)itemView.findViewById(R.id.textview_time);
            textViewHourlywage = (TextView)itemView.findViewById(R.id.textview_hourlywage);
            textViewDay = (TextView)itemView.findViewById(R.id.textview_day);
            textViewTerm = (TextView)itemView.findViewById(R.id.textview_term);
            textViewWish = (TextView)itemView.findViewById(R.id.textview_wish);

            imageViewHousework = (ImageView)itemView.findViewById(R.id.imageview_housework);
            imageViewPlay = (ImageView)itemView.findViewById(R.id.imageview_play);
            imageViewStudy = (ImageView)itemView.findViewById(R.id.imageview_study);
            imageViewHeart = (ImageView)itemView.findViewById(R.id.imageview_heart);
            imageViewLanguage = (ImageView)itemView.findViewById(R.id.imageview_language);

            textViewHousework = (TextView)itemView.findViewById(R.id.textview_housework);
            textViewPlay = (TextView)itemView.findViewById(R.id.textview_play);
            textViewStudy = (TextView)itemView.findViewById(R.id.textview_study);
            textViewHeart = (TextView)itemView.findViewById(R.id.textview_heart);
            textViewLanguage = (TextView)itemView.findViewById(R.id.textview_language);

            buttonApply = (Button)itemView.findViewById(R.id.button_apply);
        }
    }
}
