package com.example.demo;

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
    //The title of the forget password page
    private TextView title;
    //The liner layout and edit text of account
    private LinearLayout accountLayout;
    private EditText accountEt;
    //The liner layout and edit text of answer
    private LinearLayout questionLayout;
    private TextView questionTv;
    private EditText answerEt;
    //The liner layout and edit text of password
    private LinearLayout passwordLayout;
    private EditText passwordEt;
    //The edit text of repeat password
    private EditText repasswordEt;
    //Submit button
    private TextView submitBtn;
    //User entity
    UserBean userBean;
    //The string variables of account, answer, password and repeat password
    private String account, answer, password, repassword;
    //Override the initLayout to initial the layout of the interface
    @Override
    public int initLayout() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initData() {}

    @Override
    protected void initView() {
        //Find the title in view_common_title
        title = findViewById(R.id.title);
        //Set the title text to "Find password"
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
        //Button monitoring
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Get the ID of the currently clicked view,if it is submitBtn
        if (v.getId() == R.id.submitBtn) {
            //If the currently visible layout is accountLayout
            if (accountLayout.getVisibility() == View.VISIBLE) {
                //Convert the input to string, and trim() is the method
                //of removing spaces on both sides.
                account = accountEt.getText().toString().trim();
                //If the account entered is empty, the user will be prompted to enter the account
                if (TextUtils.isEmpty(account)) {
                    CommonTool.showToast("Please enter account");
                    return;
                }
                //Calling the getUser() function to get the user
                getUser();
            }
            // If the currently visible layout is questionLayout
            else if (questionLayout.getVisibility() == View.VISIBLE) {
                //Convert the input to string, and trim() is the method
                //of removing spaces on both sides.
                answer = answerEt.getText().toString().trim();
                //If the answer entered is empty, the user will be prompted to enter the answer
                if (TextUtils.isEmpty(answer)) {
                    CommonTool.showToast("Please enter answer");
                    return;
                }
                //If the answer entered is equal to the user's answer,set the questionLayout gone
                //and set the passwordLayout is visible
                if (answer.equals(userBean.getAnswer())) {
                    questionLayout.setVisibility(View.GONE);
                    passwordLayout.setVisibility(View.VISIBLE);
                } else{
                    CommonTool.showToast("Wrong answer");
                }
            }else {
                //Calling changePassword() function to change password
                changePassword();
            }
        }
    }
    /**
     * Get the user information
     */
    public void getUser() {
        //Incoming parameters:account
        HttpParams params = new HttpParams();
        params.put("account", account);
        //Request get user by account url
        HttpTool.postObject(UrlConfig.getByAccount, params, UserResonseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                UserResonseBean bean = (UserResonseBean) result[0];
                userBean = bean.data;
                //If the user is not null, set the questionLayout visible and accountLayout gone
                if (userBean != null) {
                    questionLayout.setVisibility(View.VISIBLE);
                    accountLayout.setVisibility(View.GONE);
                    //Get the user's question and set it as the question text
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
    /**
     * Change the user's password
     */
    public void changePassword() {
        //Convert the inputs to string, and trim() is the method
        //of removing spaces on both sides.
        password = passwordEt.getText().toString().trim();
        repassword = repasswordEt.getText().toString().trim();
        //If the password entered is empty, the user will be prompted to enter the password
        if (TextUtils.isEmpty(password)) {
            CommonTool.showToast("Please enter password");
            return;
        }
        //If the repeat password entered is empty, the user will be prompted to enter the
        //repeat password
        if (TextUtils.isEmpty(repassword)) {
            CommonTool.showToast("Please enter confirm password");
            return;
        }
        //If the password is not equal to repeat password, the user will be prompted to
        //enter the same password
        if (!password.equals(repassword)) {
            CommonTool.showToast("The password entered twice is inconsistent");
            return;
        }
        //calling the submit() function to submit the data
        submit();
    }

    /**
     * Submit the user's password
     */
    private void submit() {
        //Incoming parameters:id, password
        //Because the id is the only, use id to change the password
        HttpParams params = new HttpParams();
        params.put("id", userBean.getId());
        params.put("password", password);
        //Request forget password url
        HttpTool.postObject(UrlConfig.forgetPassword, params, BaseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                BaseBean bean = (BaseBean) result[0];
                //If the code is not 0, show the message
                if (bean.code != 0) {
                    CommonTool.showToast(bean.msg);
                } else {
                    //If get the data successfully, prompt the user successful
                    CommonTool.showToast("Successful");
                    //Finish forget password activity
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