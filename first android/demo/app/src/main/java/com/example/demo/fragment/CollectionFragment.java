package com.example.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.R;
import com.example.demo.RecipeDetailActivity;
import com.example.demo.adapter.SearchAdapter;
import com.example.demo.data.RecipeBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.Constants;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class CollectionFragment extends Fragment {
    View view;
    //A list contains recipe entity.
    private ArrayList<RecipeBean> beans = new ArrayList<>();
    private SearchAdapter adapter;
    //用于消除空指针异常
    //Used to eliminate null pointer exceptions
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Convert the xml to the view
        //The root parameter will determine the layoutparam of the view.
        //attachToRoot indicates whether one click addview() is required.
        view = inflater.inflate(R.layout.fragment_collect, container, false);
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
        //Find the title in view_common_title.xml
        TextView title = view.findViewById(R.id.title);
        //Set the title text to "My Collection".
        title.setText("My Collection");
        ImageView backIv = view.findViewById(R.id.backIv);
        ListView listView = view.findViewById(R.id.listView);
        adapter=new SearchAdapter(getActivity(),beans);
        listView.setAdapter(adapter);
        //Do not need the back button.
        backIv.setVisibility(View.GONE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //getActivity() returns the FragmentActivity this fragment is currently associated
                //with. May return null if the fragment is associated with a Context instead.
                //Connect to the RecipeDetailActivity and start the RecipeDetailActivity
                Intent intent=new Intent(getActivity(), RecipeDetailActivity.class);
                intent.putExtra("id",beans.get(position).getId());
                intent.putExtra("uid",beans.get(position).getUid());
                startActivity(intent);
            }
        });
    }

    protected void getData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constants.userBean.getId());
        //Request my collection url to get the user's collection.
        HttpTool.postList(UrlConfig.myCollection, params, new TypeToken<List<RecipeBean>>() {
        }.getType(), new HttpTool.HttpListener() {

            @Override
            public void onComplected(Object... result) {
                List<RecipeBean> temp = (List<RecipeBean>) result[0];
                //If the recipe bean is null or the list size is 0, prompt the user there is no data
                if (temp == null || temp.size() == 0) CommonTool.showToast("No data");
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
