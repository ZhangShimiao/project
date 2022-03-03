package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class SearchRecipesActivity extends BaseActivity implements View.OnClickListener {
    //A list stores recipe entity
    private ArrayList<RecipeBean> datas = new ArrayList<>();
    private SearchAdapter adapter;
    private String keyword="";
    private EditText searchEt;

    @Override
    public int initLayout() {
        return R.layout.activity_search_recipes;
    }

    @Override
    protected void initData() {
        //Incoming parameters: keyword
        HttpParams params = new HttpParams();
        params.put("keyword", keyword);
        //Request search recipe url
        HttpTool.postList(UrlConfig.recipesSearch, params, new TypeToken<List<RecipeBean>>() {
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
        //Find the title in view_common_title.xml
        TextView titleTv = findViewById(R.id.title);
        //Set the title text
        titleTv.setText("Search");
        searchEt= findViewById(R.id.searchEt);
        TextView searchTv = findViewById(R.id.searchTv);
        adapter = new SearchAdapter(this, datas);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        searchTv.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Connect to the RecipeDetailActivity and start RecipeDetailActivity
                Intent intent=new Intent(SearchRecipesActivity.this, RecipeDetailActivity.class);
                intent.putExtra("id",datas.get(position).getId());
                intent.putExtra("uid",datas.get(position).getUid());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        //Get the ID of the currently clicked view,if it is searchTv
        if(v.getId()==R.id.searchTv){
            //Convert the input to string
            keyword=searchEt.getText().toString();
            initData();
        }
    }


}