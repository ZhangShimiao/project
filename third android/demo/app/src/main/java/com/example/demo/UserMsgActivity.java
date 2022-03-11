package com.example.demo;


import android.content.Intent;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo.adapter.SearchAdapter;
import com.example.demo.data.BaseBean;
import com.example.demo.data.RecipeBean;
import com.example.demo.data.UserBean;
import com.example.demo.data.UserResonseBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.Constants;
import com.example.demo.tools.GlideLoadUtils;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class UserMsgActivity extends BaseActivity implements View.OnClickListener {
    private TextView name;
    private ImageView headImg;
    private TextView butOk;
    private TextView followNum;
    private TextView fansNum;
    private TextView scanNum;
    //A list stores recipe entity
    private ArrayList<RecipeBean> datas = new ArrayList<>();
    private SearchAdapter adapter;
    private int id;
    private UserBean userBean;

    @Override
    public int initLayout() {
        return R.layout.activity_user_msg;
    }

    @Override
    protected void initData() {
        getData();
        //Incoming parameters:id,uid
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("myUid", Constants.userBean.getId());
        //Request get user message url
        HttpTool.postObject(UrlConfig.getUserMsg, params, UserResonseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                UserResonseBean bean = (UserResonseBean) result[0];
                userBean = bean.data;
                bindData();
            }
            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }

    protected void getData() {
        //Incoming parameters:uid
        HttpParams params = new HttpParams();
        params.put("uid", id);
        //Request my recipes url
        HttpTool.postList(UrlConfig.myRecipes, params, new TypeToken<List<RecipeBean>>() {
        }.getType(), new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                List<RecipeBean> temp = (List<RecipeBean>) result[0];
                //If the recipe entity is null or the list size is 0, prompt the user there is no data
                if (temp == null || temp.size() == 0) CommonTool.showToast("No data");
                //Remove all the elements in the recipe bean list
                datas.clear();
                //Append the all the elements to the recipe bean list
                datas.addAll(temp);
                //Notifies the attached observers that the underlying data has been changed
                //and any View reflecting the data set should refresh itself.
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }

    private void bindData() {
        //Load the head image
        GlideLoadUtils imageLoader = new GlideLoadUtils(this);
        imageLoader.loadCircularImage(UrlConfig.BaseURL + userBean.getHeadimg(), R.mipmap.default_head, R.mipmap.default_head, headImg);
        //Get the nickname
        name.setText(userBean.getNickname());
        //Get the follow number
        followNum.setText(userBean.getFollowNum() + "");
        //Get the fan number
        fansNum.setText(userBean.getFansNum() + "");
        //Get the scan number
        scanNum.setText(userBean.getScanNum() + "");
        //If it is follow, set the button text to "Cancel Follow"
        //If it is not follow, set the button text to "Follow"
        if(userBean.getIsFollow()>0){
            butOk.setText("Cancel Follow");
        }else{
            butOk.setText("Follow");
        }
    }

    @Override
    protected void initView() {
        id = getIntent().getIntExtra("id", -1);
        //Find the title in view_common_title.xml
        TextView titleTv = findViewById(R.id.title);
        //Set the title text
        titleTv.setText("Personal Center");
        //The view of name
        name = findViewById(R.id.name);
        //The view of head image
        headImg = findViewById(R.id.headImg);
        ListView listView = findViewById(R.id.listView);
        butOk = findViewById(R.id.but_ok);
        //The view of follow number, fans number and scan number
        followNum = findViewById(R.id.followNum);
        fansNum = findViewById(R.id.fansNum);
        scanNum = findViewById(R.id.scanNum);

        adapter = new SearchAdapter(this, datas);
        listView.setAdapter(adapter);
        //Set the button text to "Follow"
        butOk.setText("Follow");
        //Button monitoring
        butOk.setOnClickListener(this);
        findViewById(R.id.fansNumLayout).setOnClickListener(this);
        findViewById(R.id.followNumLayout).setOnClickListener(this);
        findViewById(R.id.scanNumLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Get the ID of the currently clicked view,if it is but_ok
        if (v.getId() == R.id.but_ok) {//关注
            butOk.setClickable(false);
            //Incoming parameters:befollowid,followid
            HttpParams params = new HttpParams();
            params.put("befollowid", id);
            params.put("followid", Constants.userBean.getId());
            //Request follow url
            HttpTool.postObject(UrlConfig.follow, params, BaseBean.class, new HttpTool.HttpListener() {
                @Override
                public void onComplected(Object... result) {
                    butOk.setClickable(true);
                    BaseBean bean = (BaseBean) result[0];
                    if (bean.code == 0) {
                        initData();
                    } else {
                        CommonTool.showToast(bean.msg);
                    }
                }
                @Override
                public void onFailed(String msg) {
                    butOk.setClickable(true);
                    CommonTool.showToast(msg);
                }
            });
        }
        //Get the ID of the currently clicked view,if it is followNumLayout
        else if (v.getId() == R.id.followNumLayout) {
            if(userBean==null)return;
            //Connect to the FollowListActivity and start FollowListActivity
            Intent intent=new Intent(this,FollowListActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("isSelf",false);
            intent.putExtra("name",userBean.getNickname());
            startActivity(intent);
        }else if (v.getId() == R.id.fansNumLayout) {
            if(userBean==null)return;
            //Connect to the FansListActivity and start FansListActivity
            Intent intent=new Intent(this,FansListActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("name",userBean.getNickname());
            startActivity(intent);
        }else if (v.getId() == R.id.scanNumLayout) {
            if(userBean==null)return;
            //Connect to the ScanListActivity and start ScanListActivity
            Intent intent=new Intent(this, ScanListActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("name",userBean.getNickname());
            startActivity(intent);
        }
    }
}