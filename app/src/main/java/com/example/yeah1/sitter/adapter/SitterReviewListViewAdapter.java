package com.example.yeah1.sitter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.SitterReviewItem;

import java.util.ArrayList;


/**
 * Created by YEAH1 on 2017. 9. 17..
 */

public class SitterReviewListViewAdapter extends ArrayAdapter<SitterReviewItem> {

    private Context context;
    private ArrayList<SitterReviewItem> items;
    private int resource;

    private TextView textViewContent, textViewName;
    private RatingBar ratingBar;

    private SitterReviewItem item;


    public SitterReviewListViewAdapter(Context context, ArrayList<SitterReviewItem> items, int resource) {
        super(context, resource, items);

        this.context = context;
        this.items = items;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate( R.layout.listveiw_sitter_review_item,null);
        }

        textViewContent = (TextView)convertView.findViewById(R.id.textview_sitter_review_content);
        textViewName = (TextView)convertView.findViewById(R.id.textview_sitter_review_name);
        ratingBar = (RatingBar)convertView.findViewById(R.id.ratingbar_sitter_review);

        item = items.get(position);

        textViewContent.setText(item.getContent());
        textViewName.setText(item.getName());
        ratingBar.setRating(item.getRate());

        return convertView;
    }
}
