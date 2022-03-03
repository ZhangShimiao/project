package com.example.demo.common;

import android.app.Activity;
import android.app.Application;

import java.util.Stack;

//配置改变不会导致应用重启
//参考：https://cloud.tencent.com/developer/article/1009329?from=15425
//Configuration changes will not cause the application to restart
public class MyApplication extends Application {
    private static MyApplication context;
    private static Stack<Activity> activityStack;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    public static MyApplication getContext() {
        return context;
    }
    /**
     * add Activity to stack
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

}
