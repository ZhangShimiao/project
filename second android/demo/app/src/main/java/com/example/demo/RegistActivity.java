package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demo.data.BaseBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.lzy.okgo.model.HttpParams;

public class RegistActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;
    private EditText accountEt;
    private EditText nickNameEt;
    private EditText questionEt;
    private EditText answerEt;
    private EditText passwordEt;
    private EditText repasswordEt;
    private TextView registBtn;

    private String account, nickname, question, answer, password, repassword;

    @Override
    public int initLayout() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initView() {

        title = findViewById(R.id.title);
        accountEt = findViewById(R.id.accountEt);
        nickNameEt = findViewById(R.id.nickNameEt);
        questionEt = findViewById(R.id.questionEt);
        answerEt = findViewById(R.id.answerEt);
        passwordEt = findViewById(R.id.passwordEt);
        repasswordEt = findViewById(R.id.repasswordEt);
        registBtn = findViewById(R.id.registBtn);
        title.setText("Register");
        registBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.registBtn) {
            //hideKeyboard();
            account = accountEt.getText().toString().trim();
            nickname = nickNameEt.getText().toString().trim();
            question = questionEt.getText().toString().trim();
            //应该是answerEt
            answer = answerEt.getText().toString().trim();
            password = passwordEt.getText().toString().trim();
            repassword = repasswordEt.getText().toString().trim();

            if (TextUtils.isEmpty(account)) {
                CommonTool.showToast("Please enter your account");
                return;
            }
            if (TextUtils.isEmpty(nickname)) {
                CommonTool.showToast("Please enter your nickname");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                CommonTool.showToast("Please enter your password");
                return;
            }
            if (TextUtils.isEmpty(repassword)) {
                CommonTool.showToast("Please enter your confirm password");
                return;
            }
            if (!password.equals(repassword)) {
                CommonTool.showToast("The password entered twice is inconsistent");
                return;
            }
            HttpParams params = new HttpParams();
            params.put("type", 0);
            params.put("account", account);
            params.put("nickname", nickname);
            params.put("password", password);
            params.put("question", question);
            params.put("answer", answer);
            HttpTool.postObject(UrlConfig.REGIST_URL, params, BaseBean.class, new HttpTool.HttpListener() {
                @Override
                public void onComplected(Object... result) {
                    BaseBean bean = (BaseBean) result[0];
                    if (bean.code == 0) {
                        CommonTool.showToast("Register successful！");
                        finish();
                    } else {
                        CommonTool.showToast(bean.msg);
                    }
                }

                @Override
                public void onFailed(String msg) {
                    CommonTool.showToast(msg);
                }
            });
        }
    }
    @Override
    protected void initData() {}
}