package com.example.ptn0411.rxjava2practice.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ptn0411.rxjava2practice.R;
import com.example.ptn0411.rxjava2practice.adapter.MainRecyclerViewAdapter;
import com.example.ptn0411.rxjava2practice.listener.OnRecyclerListener;

import java.util.ArrayList;


/**
 * Created by ptn0411 on 2017/03/07.
 */

public class MainFragment extends Fragment implements OnRecyclerListener{

    private Context mContext;
    private RecyclerView mRecyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //RecyclerViewの参照を取得
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        //レイアウトマネージャを設定（ここで縦方向の標準リストであることを指定）
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> items = new ArrayList<>();
        //リストのデータを作成する
        for (int i = 0; i < 10; i++){
            items.add("Item"+(i+1));
        }

        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(items, mContext, this);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onRecyclerClicked(View view, int position) {
        Log.d("MainFragment", "clicked:"+ (position+1));
    }
}
