package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.demo.tools.CommonTool;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否登录
        String isLogin= CommonTool.spGetString("isLogin");
        if(TextUtils.isEmpty(isLogin)||"0".equals(isLogin)){
            //跳转到login活动
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);

        }
        else {
            Log.e("test","登录状态中");
            Intent intent=new Intent(this, RegistActivity.class);
            startActivity(intent);
        }
        finish();
    }
}