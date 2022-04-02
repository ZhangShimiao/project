package com.example.demo;


import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo.adapter.FansAdapter;
import com.example.demo.data.UserBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class FansListActivity extends BaseActivity {
    //A list stores user entity
    private ArrayList<UserBean> datas = new ArrayList<>();
    //Adapter参考：https://www.runoob.com/w3cnote/android-tutorial-adapter.html
    private FansAdapter adapter;
    private int id;

    @Override
    public int initLayout() {
        return R.layout.activity_fans_list;
    }

    @Override
    protected void initData() {
        //Incoming parameters:uid
        HttpParams params = new HttpParams();
        params.put("uid",id);
        //Request get fans url
        HttpTool.postList(UrlConfig.getFansByUser, params, new TypeToken<List<UserBean>>() {
        }.getType(), new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                List<UserBean> temp = (List<UserBean>) result[0];
                //If the user entity is null or the list size is 0, prompt the user there is no data
                if (temp == null || temp.size() == 0) CommonTool.showToast("No data");
                //Remove all the elements in the user bean list
                datas.clear();
                //Append the all the elements to the user bean list
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

    @Override
    protected void initView() {
        //Get id
        id = getIntent().getIntExtra("id", -1);
        //Get user name
        String name = getIntent().getStringExtra("name");
        //Find the title in view_common_title.xml
        TextView titleTv = findViewById(R.id.title);
        //Set the title text to "name's Fans"
        titleTv.setText(name + "'s Fans");
        ListView listView = findViewById(R.id.listView);
        adapter = new FansAdapter(this, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Connect to the UserMsgActivity and start UserMsgActivity
                Intent intent=new Intent(FansListActivity.this,UserMsgActivity.class);
                intent.putExtra("id", datas.get(position).getId());
                startActivity(intent);
            }
        });
    }
}