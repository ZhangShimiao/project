package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.data.UserBean;
import com.example.demo.data.UserResonseBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.Constants;
import com.example.demo.tools.GsonUtils;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private EditText inputUsername;
    private EditText inputPassword;
    private TextView loginBtn;
    private TextView registBtn;
    private TextView forgetBtn;

    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        //在view_common_title里,当控件visibility属性为INVISIBLE时，
        //界面保留了view控件所占有的空间；而控件属性为GONE时，界面则不保留view控件所占有的空间
        //不要返回键
        findViewById(R.id.backIv).setVisibility(View.GONE);
        title = findViewById(R.id.title);
        //用户名的输入框
        inputUsername = findViewById(R.id.inputUsername);
        //密码输入框
        inputPassword = findViewById(R.id.inputPassword);
        //登录按钮
        loginBtn = findViewById(R.id.loginBtn);
        //注册按钮
        registBtn = findViewById(R.id.registBtn);
        //忘记密码按钮
        forgetBtn = findViewById(R.id.forgetBtn);
        title.setText("Login");

        loginBtn.setOnClickListener(this);
        registBtn.setOnClickListener(this);
        forgetBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        //view.getid()就是获取当前点击的view的id，这个id一般是你在xml布局文件设置的id
        if (view.getId() == R.id.loginBtn) {
            login();
        }
        else  if(view.getId()==R.id.registBtn) {
            //Intent是一种运行时绑定（runtime binding)机制，它能在程序运行的过程中连接两个不同的组件。
            //通过Intent，你的程序可以向Android表达某种请求或者意愿，Android会根据意愿的内容选择适当的组件来响应
            Intent intent=new Intent(this,RegistActivity.class);
            //显示Intent就是直接以“类名称”来指定要启动哪一个Activity：
            //Intent intent = new Intent(this , activity.class);　　
            //其中activity.class就是要指定启动的activity
            startActivity(intent);
        }
        else  if(view.getId()==R.id.forgetBtn){
            Intent intent=new Intent(this,ForgetActivity.class);
            startActivity(intent);
        }
    }
    public void login(){
        String account = inputUsername.getText().toString().trim();
        String loginPwd = inputPassword.getText().toString().trim();
        Log.e("test","用户名输入："+account);
        Log.e("test","密码输入："+loginPwd);
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(LoginActivity.this,"Please enter account",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(loginPwd)) {
            Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
        }
        HttpParams params=new HttpParams();
        params.put("account",account);
        params.put("password",loginPwd);
        params.put("type",0);
        HttpTool.postObject(UrlConfig.LOGIN_URL, params, UserResonseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                UserResonseBean bean= (UserResonseBean) result[0];
                Constants.userBean=bean.data;
                if(bean.code==0){
                    CommonTool.spPutString("isLogin","1");
                    CommonTool.spPutString("userbean",new Gson().toJson( Constants.userBean));
                    Toast.makeText(LoginActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    CommonTool.showToast(bean.msg);
                }
            }
            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }

    @Override
    protected void initData() {
        //如果用户已经处于登录状态，从sp中可以get到该用户实体
        //那么会将输入框的显示设置为该用户的用户名和密码
        String userbeanStr=CommonTool.spGetString("userbean");
        if(userbeanStr!=null) {
            UserBean userBean= GsonUtils.GsonToBean(userbeanStr, UserBean.class);
            inputUsername.setText(userBean.getNickname());
            inputPassword.setText(userBean.getPassword());
        }
    }
}