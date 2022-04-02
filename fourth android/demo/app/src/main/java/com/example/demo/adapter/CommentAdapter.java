package com.example.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.demo.R;
import com.example.demo.data.CommentBean;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    //A list stores the comment entity
    private ArrayList<CommentBean> list;
    private LayoutInflater inflater;
    private Context context;
    private int authorId;

    public CommentAdapter(Context context, ArrayList<CommentBean> list,int authorId) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.authorId=authorId;
    }

    @Override
    public int getCount() {
        //A ？ B ：C (If A is ture, execute B, otherwise execute C)
        //If the list is null, return 0, otherwise return the size of the list.
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        //Returns the element at the specified position in this list.
        //Params:
        //position – index of the element to return
        //Returns:
        //the element at the specified position in this list
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Get the element index.
        return position;
    }

    private class ViewHolder {
        //The image view of the user's head image.
        ImageView headImg;
        //The text view of the user's name and the comment content.
        TextView nameTv;
        TextView contentTv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //convertView主要是为了缓存试图View，用以增加ListView的item view加载效率。
        //在Adapter的getView中，先判断convertView是否为空null，
        //如果非空，则直接再次对convertView复用，否则才创建新的View。
        //参考：https://blog.csdn.net/zhangphil/article/details/78435502
        //If the converView is not null
        if (convertView != null) {
            //View中的setTag（Onbect）表示给View添加一个格外的数据，以后可以用getTag()将这个数据取出来
            //参考：https://www.cnblogs.com/qingblog/archive/2012/05/30/2526239.html
            //Get the Object stored in this view as a tag
            holder = (ViewHolder) convertView.getTag();
        } else {
            //Create a new view.
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_comment_listitem, null);
            holder.headImg = convertView.findViewById(R.id.headImg);
            holder.nameTv = convertView.findViewById(R.id.nameTv);
            holder.contentTv = convertView.findViewById(R.id.contentTv);
            //Set the view's tag.
            convertView.setTag(holder);
        }
        CommentBean bean = list.get(position);
        //Set the nameTv text to the comment username.
        holder.nameTv.setText(bean.getCommentUserName());
        //If the comment entity's becommentuserid equals to the recipe's author id,
        //set the comment content.
        if(bean.getBecommentuserid()==authorId){
            holder.contentTv.setText(bean.getContent());
        }else{
            //If the comment entity's becommentuserid not equal to the recipe's author id,
            //set the comment content.
            holder.contentTv.setText("Reply："+bean.getBeCommentUserName()+" "+ bean.getContent());
        }

        return convertView;
    }
}
