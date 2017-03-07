package com.example.ptn0411.rxjava2practice.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ptn0411.rxjava2practice.R;

/**
 * Created by ptn0411 on 2017/03/07.
 */

public class MainViewHolder extends RecyclerView.ViewHolder{

    public final TextView textView;

    public MainViewHolder(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.list_item_text);
    }
}
