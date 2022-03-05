package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demo.data.BaseBean;
import com.example.demo.data.UserBean;
import com.example.demo.data.UserResonseBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.lzy.okgo.model.HttpParams;

public class ForgetActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private LinearLayout accountLayout;
    private EditText accountEt;
    private LinearLayout questionLayout;
    private TextView questionTv;
    private EditText answerEt;
    private LinearLayout passwordLayout;
    private EditText passwordEt;
    private EditText repasswordEt;
    private TextView submitBtn;
    UserBean userBean;
    private String account, answer, password, repassword;

    @Override
    public int initLayout() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initData() {}

    @Override
    protected void initView() {
        title = findViewById(R.id.title);
        title.setText("Find password");
        accountLayout = findViewById(R.id.accountLayout);
        accountEt = findViewById(R.id.accountEt);
        questionLayout = findViewById(R.id.questionLayout);
        questionTv = findViewById(R.id.questionTv);
        answerEt = findViewById(R.id.answerEt);
        passwordLayout = findViewById(R.id.passwordLayout);
        passwordEt = findViewById(R.id.passwordEt);
        repasswordEt = findViewById(R.id.repasswordEt);
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitBtn) {
            if (accountLayout.getVisibility() == View.VISIBLE) {
                account = accountEt.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    CommonTool.showToast("Please enter account");
                    return;
                }
                getUser();
            } else if (questionLayout.getVisibility() == View.VISIBLE) {
                answer = answerEt.getText().toString().trim();
                if (TextUtils.isEmpty(answer)) {
                    CommonTool.showToast("Please enter answer");
                    return;
                }
                if (answer.equals(userBean.getAnswer())) {
                    questionLayout.setVisibility(View.GONE);
                    passwordLayout.setVisibility(View.VISIBLE);
                } else{
                    CommonTool.showToast("Wrong answer");
                }
            }else {
                changePassword();
            }
        }
    }
    /**
     * 获取用户信息
     */
    public void getUser() {
        HttpParams params = new HttpParams();
        params.put("account", account);
        HttpTool.postObject(UrlConfig.getByAccount, params, UserResonseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                UserResonseBean bean = (UserResonseBean) result[0];
                userBean = bean.data;
                if (userBean != null) {
                    questionLayout.setVisibility(View.VISIBLE);
                    accountLayout.setVisibility(View.GONE);
                    questionTv.setText("Security Question："+userBean.getQuestion());
                } else {
                    CommonTool.showToast("User is not exist");
                }
            }
            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }


    public void changePassword() {

        password = passwordEt.getText().toString().trim();
        repassword = repasswordEt.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            CommonTool.showToast("Please enter password");
            return;
        }
        if (TextUtils.isEmpty(repassword)) {
            CommonTool.showToast("Please enter confirm password");
            return;
        }
        if (!password.equals(repassword)) {
            CommonTool.showToast("The password entered twice is inconsistent");
            return;
        }
        submit();
    }


    private void submit() {
        HttpParams params = new HttpParams();
        params.put("id", userBean.getId());
        params.put("password", password);
        HttpTool.postObject(UrlConfig.forgetPassword, params, BaseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                BaseBean bean = (BaseBean) result[0];
                if (bean.code != 0) {
                    CommonTool.showToast(bean.msg);
                } else {
                    CommonTool.showToast("Successful");
                    finish();
                }
            }
            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });

    }
}