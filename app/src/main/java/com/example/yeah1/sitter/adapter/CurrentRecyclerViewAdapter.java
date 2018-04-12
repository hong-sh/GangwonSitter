package com.example.yeah1.sitter.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yeah1.sitter.util.CircularProgressBar;
import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.CurrentItem;
import com.example.yeah1.sitter.view.SitterDetailActivity;

import java.util.List;


/**
 * Created by YEAH1 on 2017. 9. 17..
 */

public class CurrentRecyclerViewAdapter extends RecyclerView.Adapter<CurrentRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private AppCompatActivity activity;
    private List<CurrentItem> items;
    private int item_layout;

    public CurrentRecyclerViewAdapter(Context context, List<CurrentItem> items, int item_layout){
        this.context = context;
        if(context instanceof AppCompatActivity)
            activity = (AppCompatActivity)context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public CurrentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_current_item,null);
        view.setMinimumWidth(parent.getMinimumWidth());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrentRecyclerViewAdapter.ViewHolder holder, int position) {

        CurrentItem item = items.get(position);

        holder.circularProgressBarCurrent.setProgress(item.getSimilar());
        holder.circularProgressBarCurrent.setProgressColor(Color.parseColor("#de828f"));
        holder.textViewName.setText(item.getName()+"님");

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
        holder.textViewCareer.setText(item.getCareer() + " 년");
        holder.textViewhourlywage.setText(item.getHourlywage() + " 원");
        holder.textViewSimilar.setText(item.getSimilar() + " %");

        holder.linearLayoutCurrent.setOnClickListener(this);
        holder.linearLayoutCurrent.setTag(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View v) {
        int index = (int)v.getTag();
        CurrentItem item = items.get(index);
        Intent intent = new Intent(activity, SitterDetailActivity.class);
        intent.putExtra("job_offer_idx", item.getJobOfferIdx());
        intent.putExtra("user_email", item.getUserEmail());
        intent.putExtra("name", item.getName());
        intent.putExtra("housework", item.getHousework());
        intent.putExtra("play", item.getPlay());
        intent.putExtra("study", item.getStudy());
        intent.putExtra("heart", item.getHeart());
        intent.putExtra("language", item.getLanguage());
        intent.putExtra("age", item.getAge());
        intent.putExtra("career",item.getCareer());
        intent.putExtra("hourlywage", item.getHourlywage());
        intent.putExtra("similar", item.getSimilar());
        activity.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout linearLayoutCurrent;
        public CircularProgressBar circularProgressBarCurrent;
        public TextView textViewName;

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

        public TextView textViewAge;
        public TextView textViewCareer;
        public TextView textViewhourlywage;
        public TextView textViewSimilar;


        public ViewHolder(View itemView) {
            super(itemView);

            linearLayoutCurrent = (LinearLayout)itemView.findViewById(R.id.layout_current);
            circularProgressBarCurrent = (CircularProgressBar)itemView.findViewById(R.id.progressbar_percent);
            textViewName = (TextView)itemView.findViewById(R.id.textview_name);

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

            textViewAge = (TextView)itemView.findViewById(R.id.textview_age);
            textViewCareer = (TextView)itemView.findViewById(R.id.textview_career);
            textViewhourlywage = (TextView)itemView.findViewById(R.id.textview_hourlywage);
            textViewSimilar = (TextView)itemView.findViewById(R.id.textview_similar);
        }
    }
}
