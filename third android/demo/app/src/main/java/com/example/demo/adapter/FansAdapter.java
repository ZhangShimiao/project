package com.example.demo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.data.UserBean;
import com.example.demo.tools.GlideLoadUtils;
import com.example.demo.tools.UrlConfig;

import java.util.ArrayList;

public class FansAdapter extends BaseAdapter {
    private ArrayList<UserBean> arrayList;
    private LayoutInflater inflater;
    private Activity context;

    public FansAdapter(Activity context, ArrayList<UserBean> list) {
        inflater = LayoutInflater.from(context);
        this.arrayList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        //A ？ B ：C (If A is ture, execute B, otherwise execute C)
        //If the list is null, return 0, otherwise return the size of the list.
        return arrayList == null ? 0 : arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        //Returns the element at the specified position in this list.
        //Params:
        //position – index of the element to return
        //Returns:
        //the element at the specified position in this list
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Get the element index.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            //Create a new view.
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_follow_list, null);
            holder.headImg = convertView.findViewById(R.id.headImg);
            holder.text = convertView.findViewById(R.id.text);
            holder.statusTv = convertView.findViewById(R.id.statusTv);
            //Set the view's tag.
            convertView.setTag(holder);
        } else {
            //If the converView is not null
            //Get the Object stored in this view as a tag
            holder = (ViewHolder) convertView.getTag();
        }
        UserBean bean = arrayList.get(position);
        //Get the user's nickname.
        holder.text.setText(bean.getNickname());
        //Load the user's head image.
        GlideLoadUtils imageLoader = new GlideLoadUtils(context);
        imageLoader.loadCircularImage(UrlConfig.BaseURL + bean.getHeadimg(), R.mipmap.default_head, R.mipmap.default_head, holder.headImg);
        //Do not need "Cancel follow" button.
        holder.statusTv.setVisibility(View.GONE);
        return convertView;
    }
    private class ViewHolder {
        //The image view of user's head image.
        private ImageView headImg;
        //The text view of the user's nickname and the follow status.
        private TextView text;
        private TextView statusTv;
    }
}
