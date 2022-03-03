package com.example.demo;


import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo.adapter.SearchAdapter;
import com.example.demo.data.RecipeBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class ScanListActivity extends BaseActivity {
    //A list stores recipe entity
    private ArrayList<RecipeBean> datas = new ArrayList<>();
    private SearchAdapter adapter;
    private int id;

    @Override
    public int initLayout() {
        return R.layout.activity_fans_list;
    }

    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        //Incoming parameters:uid
        params.put("uid",id);
        //Request scan list url
        HttpTool.postList(UrlConfig.listByScan, params, new TypeToken<List<RecipeBean>>() {
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

    @Override
    protected void initView() {
        id = getIntent().getIntExtra("id", -1);
        String name = getIntent().getStringExtra("name");
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText(name + "'s Scan List");
        ListView listView = findViewById(R.id.listView);
        adapter = new SearchAdapter(this, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Connect to the RecipeDetailActivity and start RecipeDetailActivity
                Intent intent=new Intent(ScanListActivity.this,RecipeDetailActivity.class);
                intent.putExtra("id",datas.get(position).getId());
                intent.putExtra("uid",datas.get(position).getUid());
                startActivity(intent);
            }
        });
    }
}