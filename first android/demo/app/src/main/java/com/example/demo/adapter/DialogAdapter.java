package com.example.demo.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.demo.R;

import java.util.ArrayList;

public class DialogAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private LayoutInflater inflater;
    private Context context;

    public DialogAdapter(Context context, ArrayList<String> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    private class ViewHolder {
        private TextView text;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //If the converView is not null
        if (convertView != null) {
            //Get the Object stored in this view as a tag
            holder = (ViewHolder) convertView.getTag();
        } else {
            //Create a new view.
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_dialog_listitem, null);
            holder.text = convertView.findViewById(R.id.text);
            //Set the view's tag.
            convertView.setTag(holder);
        }
        String bean = list.get(position);
        holder.text.setText(bean);
        return convertView;
    }
}
