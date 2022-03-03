package com.example.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.AddRecipeActivity;
import com.example.demo.ChangePwdActivity;
import com.example.demo.ChangeSelfActivity;
import com.example.demo.FansListActivity;
import com.example.demo.FollowListActivity;
import com.example.demo.LoginActivity;
import com.example.demo.R;
import com.example.demo.ScanListActivity;
import com.example.demo.adapter.SearchAdapter;
import com.example.demo.data.RecipeBean;
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

public class SelfFragment extends Fragment implements View.OnClickListener{
    View view;
    private TextView addTv, changePswTv;
    private TextView nameTv;
    private ImageView headImg;
    private TextView butOk;
    private TextView followNumTv;
    private TextView fansNumTv;
    private TextView scanNumTv;
    //食谱的实体，列表形式，里面有多个食谱
    private ArrayList<RecipeBean> datas = new ArrayList<>();
    private SearchAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_self, container, false);
        initView();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        //Get the recipe information of the user
        getData();
        //获取该用户信息
        HttpParams params = new HttpParams();
        params.put("id", Constants.userBean.getId());
        //Request the get user url to get the user information.
        HttpTool.postObject(UrlConfig.GetUser, params, UserResonseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                UserResonseBean bean = (UserResonseBean) result[0];
                Constants.userBean = bean.data;
                bindData();
            }
            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });

    }

    protected void getData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constants.userBean.getId());
        //Request the my recipes url to get the user's recipes.
        HttpTool.postList(UrlConfig.myRecipes, params, new TypeToken<List<RecipeBean>>() {
        }.getType(), new HttpTool.HttpListener() {

            @Override
            public void onComplected(Object... result) {
                List<RecipeBean> temp = (List<RecipeBean>) result[0];
                datas.clear();
                datas.addAll(temp);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }

    protected void initView() {
        //添加菜谱的加号键
        addTv = view.findViewById(R.id.addTv);
        //修改密码文字按键
        changePswTv = view.findViewById(R.id.changePsw);
        //用户名text
        nameTv = view.findViewById(R.id.nameTv);
        //头像 Head image
        headImg = view.findViewById(R.id.headImg);
        //菜单的view
        ListView listView = view.findViewById(R.id.listView);
        //注销登录按键
        butOk = view.findViewById(R.id.but_ok);
        //关注人数
        followNumTv = view.findViewById(R.id.followNumTv);
        //粉丝人数
        fansNumTv = view.findViewById(R.id.fansNumTv);
        //浏览记录数
        scanNumTv = view.findViewById(R.id.scanNumTv);

        adapter = new SearchAdapter(getActivity(), datas);
        listView.setAdapter(adapter);
        //Set listener
        //都是可点击的
        addTv.setOnClickListener(this);
        butOk.setOnClickListener(this);
        changePswTv.setOnClickListener(this);
        view.findViewById(R.id.UserMsg).setOnClickListener(this);
        view.findViewById(R.id.followNum).setOnClickListener(this);
        view.findViewById(R.id.fansNum).setOnClickListener(this);
        view.findViewById(R.id.scanNum).setOnClickListener(this);
    }

    private void bindData() {
        GlideLoadUtils imageLoader = new GlideLoadUtils(getActivity());
        //头像加载 Load the head image.
        imageLoader.loadCircularImage(UrlConfig.BaseURL + Constants.userBean.getHeadimg(), R.mipmap.default_head, R.mipmap.default_head, headImg);
        nameTv.setText(Constants.userBean.getNickname());
        //设置关注，粉丝，浏览数量
        //Set the text of follow number, fans number and scan number.
        followNumTv.setText(Constants.userBean.getFollowNum() + "");
        fansNumTv.setText(Constants.userBean.getFansNum() + "");
        scanNumTv.setText(Constants.userBean.getScanNum() + "");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addTv) {
            //跳转到添加食谱活动
            Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.changePsw) {
            //跳转到修改密码活动
            Intent intent = new Intent(getActivity(), ChangePwdActivity.class);
            //Set the requestCode to 2.
            startActivityForResult(intent, 2);
        } else if (view.getId() == R.id.but_ok) {
            //自定义的方法
            exit();
        } else if (view.getId() == R.id.UserMsg) {
            //跳转到修改个人信息活动
            Intent intent = new Intent(getActivity(), ChangeSelfActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.followNum) {
            //跳转到关注列表活动
            Intent intent=new Intent(getActivity(), FollowListActivity.class);
            intent.putExtra("id",Constants.userBean.getId());
            intent.putExtra("name",Constants.userBean.getNickname());
            intent.putExtra("isSelf",true);
            startActivity(intent);
        }else if (view.getId() == R.id.fansNum) {
            //跳转到粉丝列表活动
            Intent intent=new Intent(getActivity(), FansListActivity.class);
            intent.putExtra("id",Constants.userBean.getId());
            intent.putExtra("name",Constants.userBean.getNickname());
            startActivity(intent);
        }else if (view.getId() == R.id.scanNum) {
            //跳转到浏览历史列表活动
            Intent intent=new Intent(getActivity(), ScanListActivity.class);
            intent.putExtra("id",Constants.userBean.getId());
            intent.putExtra("name",Constants.userBean.getNickname());
            startActivity(intent);
        }
    }

    private void exit() {
        //将login状态设为0，设为未登录状态
        CommonTool.spPutString("isLogin", "0");
        //跳转到登录活动
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //requestCode和startActivityForResult有关，上面设置了修改密码后，设为2，
        if (requestCode == 2 && resultCode == 1) {
            exit();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
