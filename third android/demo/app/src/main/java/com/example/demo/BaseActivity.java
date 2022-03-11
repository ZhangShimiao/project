package com.example.demo;


import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.demo.common.MyApplication;

import com.example.demo.tools.DialogLoading;

public abstract class BaseActivity extends FragmentActivity {
    private DialogLoading loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //两个activity之间的通讯可以通过bundle类来实现
        //Perform initialization of all fragments
        super.onCreate(savedInstanceState);
        //Get the current context and add the activity to the stack
        MyApplication.getContext().addActivity(this);
        //Set the activity content from the initLayout resource
        setContentView(initLayout());
        //Initialize view
        initView();
        //Initialize data
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
    //The back to button in view_common_title.xml
    public void backTo(View view) {
        finish();
    }
    public void hideKeyboard(){
        //参考:https://blog.csdn.net/weixin_42814000/article/details/106418026
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //If the input window is already displayed, it gets hidden.
        //If not the input window will be displayed.
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    //Permission
    public static final int REQUEST_PERMISSON = 1;
    //permission result interface
    private PermissionResultIF permissionResultIF;
    private boolean isShowDialog = true;
    private String[] PERMISSIONS;

    public interface PermissionResultIF {
        public void permissionResult(boolean suc);
    }
    //Verify the storage permission
    public boolean verifyStoragePermission(String[] PERMISSIONS, PermissionResultIF permissionResultIF) {
        isShowDialog = true;
        this.permissionResultIF = permissionResultIF;
        this.PERMISSIONS = PERMISSIONS;
        return verifyStoragePermission();
    }
    private boolean verifyStoragePermission() {
//        静态变量，代表运行该应用的手机系统的SDK 版本
        //The SDK version of the mobile phone system running this application is less than 23
        if (Build.VERSION.SDK_INT < 23) {
            permissionResultIF.permissionResult(true);
            return true;
        }
        int permission = 0;
        for (int i = 0; i < PERMISSIONS.length; i++) {
            //查看是否已经有权限了
            //Check whether you have permission
            permission += ActivityCompat.checkSelfPermission(this, PERMISSIONS[i]);
        }
        //Have permission: PackageManager.PERMISSION_GRANTED
        //No permission: PackageManager.PERMISSION_DENIED
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //没有权限，需要申请权限
            //No permission, you need to apply for permission
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSON);
            return false;
        } else {
            permissionResultIF.permissionResult(true);
            return true;
        }
    }
    //Loading dialog
    public void showLoadingDialog() {
        //If no loading dialog, create a new one
        if (loading == null) {
            loading = new DialogLoading(this);
        }
        //判断dialog是否正在显示
        //Determine whether the dialog is being displayed
        if (!loading.isShowing()) {
            loading.show();
        }
    }

    //Hide loading dialog
    public void hideLoadingDialog() {
        try {
            if (loading != null) {
                //如果dialog正在显示，则让他不显示，从屏幕上消失
                //If the dialog is showing, let it not display and disappear from the screen
                if (loading.isShowing()) {
                    loading.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}