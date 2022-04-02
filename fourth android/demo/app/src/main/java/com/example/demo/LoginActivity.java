package com.example.demo;

import android.content.Intent;
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
    //The title of the login page
    private TextView title;
    //The input user name variable
    private EditText inputUsername;
    //The input password variable
    private EditText inputPassword;
    //The login button variable
    private TextView loginBtn;
    //The register button variable
    private TextView registBtn;
    //The forget password button variable
    private TextView forgetBtn;

    //Override the initLayout to initial the layout of the interface
    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        //在view_common_title里,当控件visibility属性为INVISIBLE时，
        //界面保留了view控件所占有的空间；而控件属性为GONE时，界面则不保留view控件所占有的空间
        //Do not need the back button,so set the visibility is gone
        findViewById(R.id.backIv).setVisibility(View.GONE);
        //Find the title in view_common_title
        title = findViewById(R.id.title);
        //用户名的输入框
        //Input box for user name
        inputUsername = findViewById(R.id.inputUsername);
        //密码输入框
        //Input box for password
        inputPassword = findViewById(R.id.inputPassword);
        //登录按钮
        //Login button
        loginBtn = findViewById(R.id.loginBtn);
        //注册按钮
        //Register button
        registBtn = findViewById(R.id.registBtn);
        //忘记密码按钮
        //Forget password button
        forgetBtn = findViewById(R.id.forgetBtn);
        //Set the title text to "Login"
        title.setText("Login");
        //Button monitoring
        loginBtn.setOnClickListener(this);
        registBtn.setOnClickListener(this);
        forgetBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        //view.getid()就是获取当前点击的view的id，这个id一般是你在xml布局文件设置的id
        //Get the ID of the currently clicked view,if it is loginBtn, calling login() function
        if (view.getId() == R.id.loginBtn) {
            login();
        }
        //if it is registBtn,connect to the RegistActivity and start the RegistActivity
        else  if(view.getId()==R.id.registBtn) {
            //Intent是一种运行时绑定（runtime binding)机制，它能在程序运行的过程中连接两个不同的组件。
            //通过Intent，你的程序可以向Android表达某种请求或者意愿，Android会根据意愿的内容选择适当的组件来响应
            Intent intent=new Intent(this,RegistActivity.class);
            //显示Intent就是直接以“类名称”来指定要启动哪一个Activity：
            //Intent intent = new Intent(this , activity.class);　　
            //其中activity.class就是要指定启动的activity
            startActivity(intent);
        }
        //if it is forgetBtn,connect to the ForgetActivity and start the ForgetActivity
        else  if(view.getId()==R.id.forgetBtn){
            Intent intent=new Intent(this,ForgetActivity.class);
            startActivity(intent);
        }
    }
    //The login function
    public void login(){
        //Convert the input username and password to string, and trim() is the method
        //of removing spaces on both sides.
        String account = inputUsername.getText().toString().trim();
        String loginPwd = inputPassword.getText().toString().trim();
        //Print log
        Log.e("test","用户名输入："+account);
        Log.e("test","密码输入："+loginPwd);
        //If the user name entered is empty, the user will be prompted to enter the account
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(LoginActivity.this,"Please enter account",Toast.LENGTH_SHORT).show();
        }
        //If the password entered is empty, the user will be prompted to enter the password
        if (TextUtils.isEmpty(loginPwd)) {
            Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
        }
        //Incoming parameters: account, password and the user type
        HttpParams params=new HttpParams();
        params.put("account",account);
        params.put("password",loginPwd);
        params.put("type",0);
        //Request login url
        HttpTool.postObject(UrlConfig.LOGIN_URL, params, UserResonseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                UserResonseBean bean= (UserResonseBean) result[0];
                Constants.userBean=bean.data;
                //If get the data successfully, set the status of the user is login
                if(bean.code==0){
                    CommonTool.spPutString("isLogin","1");
                    CommonTool.spPutString("userbean",new Gson().toJson( Constants.userBean));
                    //Prompt the user to login successfully
                    Toast.makeText(LoginActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                    //Connect to the MainActivity and start the MainActivity
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    //Finish the login activity
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
        //If the user is already logged in, get the user entity from SharedPreference sp
        //The display of the input box will be set to the user's username and password
        String userbeanStr=CommonTool.spGetString("userbean");
        if(userbeanStr!=null) {
            UserBean userBean= GsonUtils.GsonToBean(userbeanStr, UserBean.class);
            inputUsername.setText(userBean.getAccount());
            inputPassword.setText(userBean.getPassword());
        }
    }
}