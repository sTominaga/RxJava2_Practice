package com.example.ptn0411.rxjava2practice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ptn0411.rxjava2practice.R;
import com.example.ptn0411.rxjava2practice.adapter.viewholder.MainViewHolder;
import com.example.ptn0411.rxjava2practice.listener.OnRecyclerListener;

import java.util.ArrayList;

/**
 * Created by ptn0411 on 2017/03/07.
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<String> mItems;
    private Context mContext;
    private OnRecyclerListener mListener;

    public MainRecyclerViewAdapter(ArrayList<String> items, Context context, OnRecyclerListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mItems = items;
        this.mContext = context;
        this.mListener = listener;
    }

    /**
     * 表示するレイアウトを設定
     */
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(mInflater.inflate(R.layout.list_item, parent, false));
    }

    /**
     * データの表示、クリックの処理
     */
    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        //データの表示
        if (mItems != null && mItems.size() > position && mItems.get(position) != null) {
            holder.textView.setText(mItems.get(position));
        }

        //クリックの処理
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRecyclerClicked(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        } else {
            return 0;
        }
    }
}
