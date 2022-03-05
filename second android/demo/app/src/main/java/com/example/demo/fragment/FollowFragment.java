package com.example.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.R;
import com.example.demo.RecipeDetailActivity;
import com.example.demo.adapter.RecipeAdapter;
import com.example.demo.data.RecipeBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.Constants;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class FollowFragment extends Fragment {
    View view;
//    GridView更是实现九宫图的首选! GridView的用法很多,主要凸显的是那种网格式布局，既有横向也有纵向的数据显示
    //动态加载数据,上拉下拉刷新
    private GridView gridview;
    private ArrayList<RecipeBean> beans = new ArrayList<>();
//    Adapter是连接后端数据和前端显示的适配器接口，是数据和UI（View）之间一个重要的纽带。
//    在常见的View(ListView,GridView)等地方都需要用到Adapter
    private RecipeAdapter adapter;
//    LayoutInflater 所谓“扩展”，
//    作用类似于findViewById()，
//    不同的是LayoutInflater是用来获得View的，
//    即返回值就是View ，
//    而findViewById()是用来获得具体控件的

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_follow, container, false);
        initView();
        getData();
        return view;
    }


    protected void initView() {
        gridview = view.findViewById(R.id.gridview);
        adapter=new RecipeAdapter(getActivity(),beans);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), RecipeDetailActivity.class);
                intent.putExtra("id",beans.get(position).getId());
                intent.putExtra("uid",beans.get(position).getUid());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){getData();}
    }

    public void getData() {
        CommonTool.showLog("hahhh===");
        HttpParams params = new HttpParams();
        params.put("uid", Constants.userBean.getId());
        HttpTool.postList(UrlConfig.listByFollow, params, new TypeToken<List<RecipeBean>>() {
        }.getType(), new HttpTool.HttpListener() {

            @Override
            public void onComplected(Object... result) {
                List<RecipeBean> temp = (List<RecipeBean>) result[0];
                beans.clear();
                beans.addAll(temp);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }
}
