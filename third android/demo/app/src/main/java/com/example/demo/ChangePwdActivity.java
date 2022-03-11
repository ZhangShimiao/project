package com.example.demo;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demo.data.BaseBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.Constants;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.lzy.okgo.model.HttpParams;

public class ChangePwdActivity extends BaseActivity implements View.OnClickListener {
    //The edit text of original password, new password, confirm password
    private EditText originalPwd, newPwd, confirmPwd;


    @Override
    public int initLayout() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initData() {}

    @Override
    protected void initView() {
        //Find the title in view_common_title.xml
        TextView titleTv = findViewById(R.id.title);
        //Set the title text to "Edit Password"
        titleTv.setText("Edit Password");
        //The view of original password
        originalPwd = findViewById(R.id.originalPwd);
        ////The view of new password
        newPwd = findViewById(R.id.newPwd);
        //The view of confirm password
        confirmPwd = findViewById(R.id.confirmPwd);
        //Button monitoring
        findViewById(R.id.but_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Get the ID of the currently clicked view,if it is but_ok, calling editPwd()
        if (v.getId() == R.id.but_ok) {
            editPwd();
        }
    }
    //Edit the password
    public void editPwd() {
        //Convert the inputs to string, and trim() is the method
        //of removing spaces on both sides.
        String old = originalPwd.getText().toString().trim();
        String pwd = newPwd.getText().toString().trim();
        String repwd =confirmPwd.getText().toString().trim();
        //If the old password entered is empty, the user will be prompted to enter the old password
        if (TextUtils.isEmpty(old)) {
            CommonTool.showToast("Please enter the original password");
            return;
        }
        //If the new password entered is empty, the user will be prompted to enter the new password
        if (TextUtils.isEmpty(pwd)) {
            CommonTool.showToast("Please enter the new password");
            return;
        }
        //If the confirm password entered is empty, the user will be prompted to confirm password
        if (TextUtils.isEmpty(repwd)) {
            CommonTool.showToast("Please confirm your password");
            return;
        }
        //If the old password is not equal to the user password, prompt the user the password is wrong
        if (!old.equals(Constants.userBean.getPassword())) {
            CommonTool.showToast("The original password is wrong");
            return;
        }
        //If the new password is not equal to confirm password, prompt the user
        //the two passwords are inconsistent
        if (!pwd.equals(repwd)) {
            CommonTool.showToast("The two passwords are inconsistent");
            return;
        }
        //Incoming parameters:password,id
        HttpParams params = new HttpParams();
        params.put("password", pwd);
        params.put("id", Constants.userBean.getId());
        //Request forget password url
        HttpTool.postObject(UrlConfig.forgetPassword, params, BaseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                BaseBean bean = (BaseBean) result[0];
                if (bean.code == 0) {
                    CommonTool.showToast("Modified successfully!");
                    //Call this to set the result that your activity will return to its caller.
                    setResult(1);
                    //Finish the change password activity
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