package com.example.demo.tools;

import android.app.Activity;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.demo.R;

//import com.bumptech.glide.load.resource.bitmap.CircleCrop;


/**
 * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
 * Created by Li_Xavier on 2017/6/20 0020.
 */
public class GlideLoadUtils {
    private Activity activity;
    private Fragment fragment;
    private RequestManager manager;

    public GlideLoadUtils(Activity activity) {
        this.activity = activity;
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) return;
        manager = Glide.with(activity);
    }

    /**
     * 获取RequestManager对象
     *
     * @return
     */
    public RequestManager getManager() {
        return manager;
    }

    /**
     * 加载普通图片
     *
     * @param object
     * @param view
     */
    public void loadImage(Object object, ImageView view, boolean isCenterCrop) {
        loadImage(object, R.mipmap.default_image, R.mipmap.default_image, view, isCenterCrop);

    }

    /**
     * 加载普通图片
     *
     * @param object
     * @param error
     * @param placeHolder
     * @param view
     */
    public void loadImage(Object object, int error, int placeHolder, ImageView view, boolean isCenterCrop) {
        if (manager == null) return;
        if (isCenterCrop) {
            manager.load(object)
                    .error(error)
                    .placeholder(placeHolder).centerCrop()
                    .into(view);
        } else {
            manager.load(object)
                    .error(error)
                    .placeholder(placeHolder).fitCenter()
                    .into(view);
        }

    }

//    /**
//     * 加载圆形图片
//     *
//     * @param object
//     * @param view
//     */
//    public void loadCircularImage(Object object, ImageView view) {
//        loadCircularImage(object,R.mipmap.default_headimg,R.mipmap.default_headimg,view);
//    }
    /**
     * 加载圆形图片
     *
     * @param object
     * @param error
     * @param placeHolder
     * @param view
     */
    public void loadCircularImage(Object object, int error, int placeHolder, ImageView view) {
        if (manager == null) return;
        manager.load(object)
                .error(error)
                .placeholder(placeHolder)
                .transform(new CircleCrop())
                .into(view);
    }
}