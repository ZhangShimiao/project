package com.example.demo;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.fragment.CollectionFragment;
import com.example.demo.fragment.MainFragment;
import com.example.demo.fragment.SelfFragment;


import java.util.ArrayList;
//主界面活动Main activity contains main interface fragment,
// collection interface fragemnt and myself interface fragment
public class MainActivity extends  BaseActivity implements View.OnClickListener {
    //对应用的Fragment执行一些操作，如添加、移除或替换它们，以及将它们添加到返回堆栈
    //create a fragmentManager through getSupportFragmentManager()
    private FragmentManager fragmentManager = getSupportFragmentManager();
    //存放fragments数组
    //the array list which contains fragments
    private ArrayList<Fragment> MainFragments = new ArrayList<>();
    //底部按钮，home,收藏，我的
    //Bottom button
    private View home_button, collect_button, self_button;
    //Text style array
    private TextView[] texts = new TextView[3];
    //image array
    private ImageView[] images = new ImageView[3];
    //Used to identify which fragment is currently in
    private int index = 0;
    //红色的底部导航栏点击状态Red bottom navigation bar click status
    private int[] clickStatus={R.mipmap.home_click,R.mipmap.collect_click,R.mipmap.my_click};
    //白色的底部导航栏未点击状态The white bottom navigation bar is not clicked
    private int[] unclickStatus={R.mipmap.home_unclick,R.mipmap.collect_unclick,R.mipmap.my_unclick};

    //重写BaseActivity里的方法Rewrite the method in BaseActivity
    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }
    @Override
    protected void initData() {
        //Calling setCurrentFragment() function to set the current fragment
        setCurrentFragment();
    }
    @Override
    protected void initView() {
        //getIntent()方法在Activity中使用，获得启动当前活动时的Intent内容
        //了解一下四种启动模式
        //Set the index number to 0
        index=getIntent().getIntExtra("index",0);
        //获得底部导航栏的三个按钮样式
        //Get the style of the three buttons of the navigation bar
        home_button = findViewById(R.id.home_button);
        collect_button = findViewById(R.id.collect_button);
        self_button = findViewById(R.id.self_button);
        //Get the textview，“Home” text
        texts[0] = findViewById(R.id.tv1);
        //Get the textview,"Collection" text
        texts[1] = findViewById(R.id.tv2);
        //Get the textview,"My" text
        texts[2] = findViewById(R.id.tv3);
        //Get the imageview,"Home" image view
        images[0] = findViewById(R.id.iv1);
        //"Collection" image view
        images[1] = findViewById(R.id.iv2);
        //"My" image view
        images[2] = findViewById(R.id.iv3);
        //主显示页面的fragment
        //Main page's fragment
        MainFragment fragment1 = new MainFragment();
        MainFragments.add(fragment1);
        //收藏页面的fragment
        //Collection page's fragment
        CollectionFragment fragment2 = new CollectionFragment();
        MainFragments.add(fragment2);
        //我的个人页面的fragment
        //My page's fragment
        SelfFragment fragment3 = new SelfFragment();
        MainFragments.add(fragment3);
        //设置三个按钮的点击的监听器,可以不独立写成函数
        //calling setListener() function to set the listener
        setListener();
    }
//参考：https://blog.csdn.net/i_nclude/article/details/105472751
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
            //If the fragment is not null, then hide the fragment
            if (MainFragments.get(i) != null) {
                //先把fragment都隐藏
                transaction.hide(MainFragments.get(i));
            }
            //Get which button is currently clicked and execute the fragment corresponding to the button
            if (i == index) {
                //index为0时在home,1时在收藏,2时在我的
                Log.e("index", String.valueOf(index));
                //The font color of click status is red
                texts[i].setTextColor(getResources().getColor(R.color.mainText));
                //Set the text size
                texts[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                TextPaint tp = texts[i].getPaint();
                //Bold text
                tp.setFakeBoldText(true);
                //点击状态的红色图标
                //Click status
                images[i].setImageResource(clickStatus[i]);
            } else {
                //Unclick status
                texts[i].setTextColor(getResources().getColor(R.color.bar_grey));
                texts[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                TextPaint tp = texts[i].getPaint();
                tp.setFakeBoldText(false);
                images[i].setImageResource(unclickStatus[i]);
            }
        }
        //如果该Fragment对象被添加到了它的Activity中，那么它返回true，否则返回false。
        //如果该index fragment没有添加到里面,则调用让他添加进来
        //If the index fragment is not added to it, call to add it
        if (!MainFragments.get(index).isAdded()) {
            //向Activity加入一个片段，这个片段在activity容器中有他自己的视图
            transaction.add(R.id.container, MainFragments.get(index));
        }
        //显示之前被隐藏的fragment
        //Show previously hidden fragments
        transaction.show(MainFragments.get(index));
        //当你把这个碎片add进fragmentmanager（碎片栈），
        //或者replace（是remove和add的结合体）进去的时候，你才会进入碎片的生命周期。
        //既然commit就可以进入碎片的生命周期
        transaction.commit();
    }
    @Override
    public void onClick(View v) {
        //Get the ID of the currently clicked view,if it is home_button,
        //set the index value as 0, calling setCurrentFragment() function
        if (v.getId() == R.id.home_button) {
            index = 0;
            setCurrentFragment();
        }
        //Get the ID of the currently clicked view,if it is collect_button,
        //set the index value as 1, calling setCurrentFragment() function
        else if (v.getId() == R.id.collect_button) {
            index = 1;
            setCurrentFragment();
        }
        //Get the ID of the currently clicked view,if it is self_button,
        //set the index value as 2, calling setCurrentFragment() function
        else if (v.getId() == R.id.self_button) {
            index = 2;
            setCurrentFragment();
        }
    }
}