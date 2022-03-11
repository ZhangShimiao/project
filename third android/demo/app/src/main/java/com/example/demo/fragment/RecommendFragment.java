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

public class RecommendFragment extends Fragment {
    View view;
    private ArrayList<RecipeBean> recipeBeans = new ArrayList<>();
    private RecipeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_follow, container, false);
        initView();
        getData();
        return view;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //Called when the hidden state of the fragment has changed. Fragments start out not hidden;
        //this will be called whenever the fragment changes state from that.
        if(!hidden){getData();}
    }


    protected void initView() {
        //Initialise the view.
        GridView gv = view.findViewById(R.id.gridview);
        adapter=new RecipeAdapter(getActivity(),recipeBeans);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Connect to the RecipeDetailActivity and start the RecipeDetailActivity
                Intent intent=new Intent(getActivity(), RecipeDetailActivity.class);
                intent.putExtra("id",recipeBeans.get(position).getId());
                intent.putExtra("uid",recipeBeans.get(position).getUid());
                startActivity(intent);
            }
        });
    }
    protected void getData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constants.userBean.getId());
        //Request the show all recipes url.
        HttpTool.postList(UrlConfig.showRecommendRecipes, params, new TypeToken<List<RecipeBean>>() {
        }.getType(), new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                List<RecipeBean> temp = (List<RecipeBean>) result[0];
                recipeBeans.clear();
                recipeBeans.addAll(temp);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }
}
