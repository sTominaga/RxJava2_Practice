package com.example.ptn0411.rxjava2practice.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ptn0411.rxjava2practice.R;
import com.example.ptn0411.rxjava2practice.databinding.ActivityMainBinding;
import com.example.ptn0411.rxjava2practice.viewmodel.MainViewModel;

/**
 * Created by ptn0411 on 2017/03/07.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        MainViewModel viewModel = new MainViewModel();
        viewModel.startGetApi(viewModel::setModel);

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView company = (TextView)findViewById(R.id.company);
                company.setText("aaaa");
            }
        });
    }
}
