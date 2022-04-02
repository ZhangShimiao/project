package com.example.demo.tools;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.demo.R;

/**
 * Loading dialog encapsulation class
 *
 */
public class DialogLoading extends Dialog {
    TextView contentTv;
    public DialogLoading(Context context) {
        super(context, R.style.LoadingDialog);
        //Sets this dialog is not cancelable with the BACK key.
        this.setCancelable(false);
        //Sets this dialog can not be canceled when touched outside the window's bounds.
        setCanceledOnTouchOutside(false);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_loading, null);
        contentTv =  view.findViewById(R.id.text_view);
        setContentView(view);

    }
}
