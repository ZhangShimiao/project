package com.example.demo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.MainActivity;
import com.example.demo.R;
import com.example.demo.UserMsgActivity;
import com.example.demo.data.RecipeBean;
import com.example.demo.tools.Constants;
import com.example.demo.tools.GlideLoadUtils;
import com.example.demo.tools.UrlConfig;

import java.util.List;

public class RecipeAdapter extends BaseAdapter {
    //    Adapter是连接后端数据和前端显示的适配器接口，是数据和UI（View）之间一个重要的纽带。
//    在常见的View(ListView,GridView)等地方都需要用到Adapter
    private LayoutInflater inflater;
    private Activity context;
    private List<RecipeBean> beans;

    public RecipeAdapter(Activity context, List<RecipeBean> beans) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
//        Android中的ListView常用Adapter中都会涉及到convertView的使用，
//        使用convertView主要是为了缓存试图View，用以增加ListView的item view加载效率
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_recipes_listitem, null);
            viewHolder.iv = convertView.findViewById(R.id.iv);
            viewHolder.titleTv = convertView.findViewById(R.id.titleTv);
            viewHolder.headImg = convertView.findViewById(R.id.headImg);
            viewHolder.nameTv = convertView.findViewById(R.id.nameTv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RecipeBean bean = beans.get(position);
        viewHolder.titleTv.setText(bean.getTitle());
        viewHolder.nameTv.setText(bean.getUname());
        //加载食谱图片和用户头像
        GlideLoadUtils imageLoader = new GlideLoadUtils(context);
        if (bean.getPic().contains("v.ylapi.cn")) {
            imageLoader.loadImage(bean.getPic(), viewHolder.iv, true);
        } else {
            imageLoader.loadImage(UrlConfig.BaseURL + bean.getPic(), viewHolder.iv, true);
        }
        imageLoader.loadCircularImage(UrlConfig.BaseURL + bean.getHeadimg(), R.mipmap.default_head, R.mipmap.default_head, viewHolder.headImg);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.nameTv || v.getId() == R.id.headImg) {
                    if (!bean.getUid().equals(Constants.userBean.getId())) {
                        Intent intent = new Intent(context, UserMsgActivity.class);
                        intent.putExtra("id", bean.getUid());
                        context.startActivity(intent);
                    } else {
                        //如果点击的是当前登录用户的头像或用户名跳转到mainactivity中的my
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("index", 2);
                        context.startActivity(intent);
                    }

                }
            }
        };
        viewHolder.nameTv.setOnClickListener(listener);
        viewHolder.headImg.setOnClickListener(listener);
        return convertView;
    }
    private class ViewHolder {
        ImageView iv;
        TextView titleTv;
        ImageView headImg;
        TextView nameTv;
    }
}
