package com.example.yeah1.sitter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yeah1.sitter.R;
import com.example.yeah1.sitter.dto.HelperItem;

import java.util.List;

/**
 * Created by YEAH1 on 2017. 9. 15..
 */

public class HelperRecyclerViewAdapter extends RecyclerView.Adapter<HelperRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<HelperItem> items;
    private int item_layout;

    public HelperRecyclerViewAdapter(Context context, List<HelperItem> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_helper_item, null);
        view.setMinimumWidth(parent.getMinimumWidth());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HelperItem item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.addr.setText(item.getAddr());
        holder.number.setText(item.getNumber());
        holder.relativeLayouthelper.setTag(holder.number.getText().toString());
        holder.relativeLayouthelper.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_helper:
                if(context instanceof Activity) {
                    Activity activity = (Activity)context;
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + String.valueOf(v.getTag())));
                    activity.startActivity(intent);
                }
                break;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout relativeLayouthelper;
        public TextView title;
        public TextView addr;
        public TextView number;

        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayouthelper = (RelativeLayout) itemView.findViewById(R.id.layout_helper);
            title = (TextView) itemView.findViewById(R.id.textView_helper_Title);
            addr = (TextView) itemView.findViewById(R.id.textView_helper_addr);
            number = (TextView) itemView.findViewById(R.id.textView_helper_number);
        }
    }
}
