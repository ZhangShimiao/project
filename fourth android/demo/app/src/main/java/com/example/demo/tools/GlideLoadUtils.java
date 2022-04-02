package com.example.demo.tools;

import android.app.Activity;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.demo.R;


/**
 * Glide 是一个 Android 上的图片加载和缓存库
 * Glide
 * picture loading encapsulation class
 *
 */
public class GlideLoadUtils {
    private Activity activity;
    private Fragment fragment;
    private RequestManager manager;

    public GlideLoadUtils(Activity activity) {
        this.activity = activity;
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) return;
        //Begin a load with Glide that will be tied to the given Activity's lifecycle
        //and that uses the given Activity's default options.
        //A RequestManager for the given activity that can be used to start a load.
        manager = Glide.with(activity);
    }

    /**
     * 加载普通图片
     * Load normal picture
     */
    public void loadImage(Object object, ImageView view, boolean isCenterCrop) {
        loadImage(object, R.mipmap.default_image, R.mipmap.default_image, view, isCenterCrop);
    }

    /**
     * 加载普通图片
     * Load normal picture
     * CropCenter:
     * (1)当图片大于ImageView的宽高：以图片的中心点和ImageView的中心点为基准，按比例缩小图片，
     * 直到图片的宽高有一边等于ImageView的宽高，则对于另一边，图片的长度大于或等于ImageView的长度，
     * 最后用ImageView的大小居中截取该图片。
     * (2)当图片小于ImageView的宽高：以图片的中心点和ImageView的中心点为基准，按比例扩大图片，
     * 直到图片的宽高大于或等于ImageView的宽高，并按ImageView的大小居中截取该图片。
     * 链接：https://www.jianshu.com/p/8b4b352333f8
     */
    public void loadImage(Object object, int error, int placeHolder, ImageView view, boolean isCenterCrop) {
        if (manager == null) return;
        if (isCenterCrop) {
            manager.load(object)
                    .error(error)
                    .placeholder(placeHolder).centerCrop()
                    .into(view);
            //Sets the ImageView the resource will be loaded into,
            //cancels any existing loads into the view,
            //and frees any resources Glide may have previously loaded into the view
            //so they may be reused.
        } else {
            manager.load(object)
                    .error(error)
                    .placeholder(placeHolder).fitCenter()
                    .into(view);
        }

    }

    /**
     * 加载圆形图片
     * Load circular image
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