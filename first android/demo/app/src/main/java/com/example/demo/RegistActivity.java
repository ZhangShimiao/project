package com.example.demo;

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
    //The title of the register page
    private TextView title;
    //The edit text of the account variable
    private EditText accountEt;
    //The edit text of the nick name variable
    private EditText nickNameEt;
    //The edit text of the question variable
    private EditText questionEt;
    //The edit text of the answer variable
    private EditText answerEt;
    //The edit text of the password variable
    private EditText passwordEt;
    //The edit text of the repeat password variable
    private EditText repasswordEt;
    //The register button
    private TextView registBtn;
    //The string variables of account, nickname, question, answer, password and repeat password
    private String account, nickname, question, answer, password, repassword;
    //Override the initLayout to initial the layout of the interface
    @Override
    public int initLayout() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initView() {
        //Find the title in view_common_title
        title = findViewById(R.id.title);
        //Input box for account
        accountEt = findViewById(R.id.accountEt);
        //Input box for nickname
        nickNameEt = findViewById(R.id.nickNameEt);
        //Input box for question
        questionEt = findViewById(R.id.questionEt);
        //Input box for answer
        answerEt = findViewById(R.id.answerEt);
        //Input box for password
        passwordEt = findViewById(R.id.passwordEt);
        //Input box for repeat password
        repasswordEt = findViewById(R.id.repasswordEt);
        //Register Button
        registBtn = findViewById(R.id.registBtn);
        //Set the title text to "Register"
        title.setText("Register");
        //Button monitoring
        registBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //Get the ID of the currently clicked view,if it is registBtn
        if (view.getId() == R.id.registBtn) {
            //hideKeyboard();
            //Convert the inputs to string, and trim() is the method
            //of removing spaces on both sides.
            account = accountEt.getText().toString().trim();
            nickname = nickNameEt.getText().toString().trim();
            question = questionEt.getText().toString().trim();
            answer = answerEt.getText().toString().trim();
            password = passwordEt.getText().toString().trim();
            repassword = repasswordEt.getText().toString().trim();
            //If the account entered is empty, the user will be prompted to enter the account
            if (TextUtils.isEmpty(account)) {
                CommonTool.showToast("Please enter your account");
                return;
            }
            //If the nickname entered is empty, the user will be prompted to enter the nickname
            if (TextUtils.isEmpty(nickname)) {
                CommonTool.showToast("Please enter your nickname");
                return;
            }
            //If the password entered is empty, the user will be prompted to enter the password
            if (TextUtils.isEmpty(password)) {
                CommonTool.showToast("Please enter your password");
                return;
            }
            //If the repeat password entered is empty, the user will be prompted to enter the
            //repeat password
            if (TextUtils.isEmpty(repassword)) {
                CommonTool.showToast("Please enter your confirm password");
                return;
            }
            //If the password is not equal to repeat password, the user will be prompted to
            //enter the same password
            if (!password.equals(repassword)) {
                CommonTool.showToast("The password entered twice is inconsistent");
                return;
            }
            //Incoming parameters: user type, account, nickname, password, question and answer
            HttpParams params = new HttpParams();
            params.put("type", 0);
            params.put("account", account);
            params.put("nickname", nickname);
            params.put("password", password);
            params.put("question", question);
            params.put("answer", answer);
            //Request register url
            HttpTool.postObject(UrlConfig.REGIST_URL, params, BaseBean.class, new HttpTool.HttpListener() {
                @Override
                public void onComplected(Object... result) {
                    BaseBean bean = (BaseBean) result[0];
                    //If get the data successfully
                    if (bean.code == 0) {
                        //Prompt the user to register successfully
                        CommonTool.showToast("Register successful！");
                        //Finish the register activity
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