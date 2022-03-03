package com.example.demo.common;

import android.app.Activity;
import android.app.Application;

import java.util.Stack;

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
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

}
