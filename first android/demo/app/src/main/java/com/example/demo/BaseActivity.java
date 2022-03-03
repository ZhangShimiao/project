package com.example.demo;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;

import com.example.demo.common.MyApplication;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getContext().addActivity(this);
        setContentView(initLayout());
        initView();
        initData();
    }
    public abstract int initLayout();
    protected abstract void initData();
    protected abstract void initView();
    //fragment的onPause方法重写
    @Override
    public void onPause() {
        super.onPause();
    }
    //返回的按钮在xml中配置的onclick
    public void backTo(View view) {
        finish();
    }
}