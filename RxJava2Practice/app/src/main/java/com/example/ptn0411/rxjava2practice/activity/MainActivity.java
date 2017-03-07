package com.example.ptn0411.rxjava2practice.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.ptn0411.rxjava2practice.R;
import com.example.ptn0411.rxjava2practice.fragment.MainFragment;

/**
 * Created by ptn0411 on 2017/03/07.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment fragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.container, fragment);
        transaction.commit();
    }
}
