package com.example.demo.tools;

import android.app.Dialog;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.demo.R;
import com.example.demo.adapter.DialogAdapter;

import java.util.ArrayList;

public class CustomDialog extends Dialog {
    protected Context context;
    protected View layout;
    public CustomDialog(Context context) {
        super(context, R.style.custom_dialog);
        //Sets this dialog can not be canceled when touched outside the window's bounds.
        //Sets this dialog is not cancelable with the BACK key.
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        this.context = context;
    }
    public interface DialogCommonFinishIF {
        void onDialogFinished(Object... objects);
    }
    public void bindListLayout(ArrayList<String> texts, final DialogCommonFinishIF dialogListItemSelectIF) {

        layout = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
        Button popupwindows_cancel = layout.findViewById(R.id.popupwindows_cancel);
        ListView listView = layout.findViewById(R.id.listView);
        DialogAdapter adapter = new DialogAdapter(context, texts);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                dialogListItemSelectIF.onDialogFinished(position);
            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
        popupwindows_cancel.setOnClickListener(listener);
        layout.findViewById(R.id.parent).setOnClickListener(listener);
        addContentView(layout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
