package com.example.demo.tools;


import android.app.Activity;
import android.app.Dialog;

import android.content.Context;

import android.content.SharedPreferences;

import android.text.TextUtils;

import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.demo.common.MyApplication;


public class CommonTool {
    public static SharedPreferences sp;
    /**
     * Show log
     */
    public static void showLog(String msg) {
        if (UrlConfig.LOG_FLAG && !TextUtils.isEmpty(msg)) {
            Log.e("test", msg);
        }
    }
    //Show toast.
    public static void showToast(String text){
        if(!TextUtils.isEmpty(text)){
            Toast.makeText(MyApplication.getContext(),text, Toast.LENGTH_SHORT).show();
        }
    }

    private static SharedPreferences getSp(){
        if (sp == null) {
            sp = MyApplication.getContext().getSharedPreferences("sports", Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static void spPutString(String key, String value) {
        getSp().edit().putString(key,value).commit();
    }

    public static String spGetString(String key) {
        return getSp().getString(key,null);
    }
    /**
     * Set dialog
     *
     * @param activity
     * @param dialog
     * @param position  Position: Gravity.CENTER and so on
     * @param animId    -1 represents no animation
     * @param scaleW   {0~1}对话框的宽度=屏幕宽度*scaleW -1表示包裹内容
     * @param scaleH   {0~1}对话框的高度=屏幕高度*scaleH -1表示包裹内容
     */
    public static void setDialog(Activity activity, Dialog dialog, int position, int animId, double scaleW, double scaleH) {

        Window window = dialog.getWindow();
        window.setGravity(position);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        layoutParams.dimAmount = 0.2f;
        window.setAttributes(layoutParams);

        // 此处可以设置dialog显示的位置
        //Set the display position of dialog
        if (animId != -1) {
            window.setWindowAnimations(animId);
            //Add animation.
        }
        dialog.show();
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * scaleW);
        //Set width.
        if (scaleH > 0) {
            //Set height.
            lp.height = (int) (display.getHeight() * scaleH);
        }
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(true);
    }
}
