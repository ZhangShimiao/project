package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.data.UserResonseBean;
import com.example.demo.fragment.CollectionFragment;
import com.example.demo.fragment.MainFragment;
import com.example.demo.fragment.SelfFragment;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.Constants;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

public class MainActivity extends  BaseActivity implements View.OnClickListener {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    //存放fragments数组
    private ArrayList<Fragment> MainFragments = new ArrayList<>();
    //底部按钮，home,收藏，我的
    private View home_button, collect_button, self_button;
    //字体样式数组
    private TextView[] texts = new TextView[3];
    //图标数组
    private ImageView[] images = new ImageView[3];
    private int index = 0;
    //红色的底部导航栏点击状态
    private int[] clickStatus={R.mipmap.home_click,R.mipmap.collect_click,R.mipmap.my_click};
    //白色的底部导航栏未点击状态
    private int[] unclickStatus={R.mipmap.home_unclick,R.mipmap.collect_unclick,R.mipmap.my_unclick};

    //重写basicActivitty里的方法,activity_main只包含了空的framelayout和底部导航栏
    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }
    @Override
    protected void initData() {
        setCurrentFragment();
    }

    @Override
    protected void initView() {
        //getIntent()方法在Activity中使用，获得启动当前活动时的Intent内容
        //了解一下四种启动模式
        index=getIntent().getIntExtra("index",0);
        //获得底部导航栏的三个按钮样式
        home_button = findViewById(R.id.home_button);
        collect_button = findViewById(R.id.collect_button);
        self_button = findViewById(R.id.self_button);
        //获得textview，“首页”文字,home键的文本
        texts[0] = findViewById(R.id.tv1);
        //获得textview,"收藏"文字
        texts[1] = findViewById(R.id.tv2);
        //获得textview,"我的"文字
        texts[2] = findViewById(R.id.tv3);
        //获得imageview,"首页"的房子图标
        images[0] = findViewById(R.id.iv1);
        //获得imageview,"收藏"的星星图标
        images[1] = findViewById(R.id.iv2);
        //获得imageview,"我的"的小人图标
        images[2] = findViewById(R.id.iv3);
        //主显示页面的fragment
        MainFragment fragment1 = new MainFragment();
        MainFragments.add(fragment1);
        //收藏页面的fragment
        CollectionFragment fragment2 = new CollectionFragment();
        MainFragments.add(fragment2);
        //我的个人页面的fragment
        SelfFragment fragment3 = new SelfFragment();
        MainFragments.add(fragment3);
        //设置三个按钮的点击的监听器,可以不独立写成函数
        setListener();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        index=intent.getIntExtra("index",0);
        setCurrentFragment();
    }

    protected void setListener() {
        //因为implement了View.OnClickListener,所以可以直接this
        home_button.setOnClickListener(this);
        collect_button.setOnClickListener(this);
        self_button.setOnClickListener(this);

    }

    private void setCurrentFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < MainFragments.size(); i++) {
            if (MainFragments.get(i) != null) {
                //先把fragment都隐藏
                transaction.hide(MainFragments.get(i));
            }
            //获取当前点击的是哪个按钮,执行该按钮对应的fragment
            if (i == index) {
                //index为0时在home,1时在收藏,2时在我的
                Log.e("index", String.valueOf(index));
                //点击状态字体为红色
                texts[i].setTextColor(getResources().getColor(R.color.mainText));
                texts[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                TextPaint tp = texts[i].getPaint();
                tp.setFakeBoldText(true);
                //点击状态的红色图标
                images[i].setImageResource(clickStatus[i]);
            } else {
                //未点击状态下的图标和字体设置
                texts[i].setTextColor(getResources().getColor(R.color.bar_grey));
                texts[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                TextPaint tp = texts[i].getPaint();
                tp.setFakeBoldText(false);
                images[i].setImageResource(unclickStatus[i]);
            }
        }
        //如果该Fragment对象被添加到了它的Activity中，那么它返回true，否则返回false。
        //如果该index fragment没有添加到里面,则调用让他添加进来
        if (!MainFragments.get(index).isAdded()) {
            //向Activity加入一个片段，这个片段在activity容器中有他自己的视图
            transaction.add(R.id.container, MainFragments.get(index));
        }
        //显示之前被隐藏的fragment
        transaction.show(MainFragments.get(index));
        //当你把这个碎片add进fragmentmanager（碎片栈），
        //或者replace（是remove和add的结合体）进去的时候，你才会进入碎片的生命周期。
        //既然commit就可以进入碎片的生命周期
        transaction.commit();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.home_button) {
            index = 0;
            setCurrentFragment();
        } else if (v.getId() == R.id.collect_button) {
            index = 1;
            setCurrentFragment();
        } else if (v.getId() == R.id.self_button) {
            index = 2;
            setCurrentFragment();
        }
    }
}