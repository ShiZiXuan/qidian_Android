package com.uautogo.qidian.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uautogo.qidian.R;

/**
 * Created by baienda on 2017/9/28.
 */

public class UpdateDialog {
    private TextView title_tv, content_tv;
    private Button yes_btn, no_btn;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mDialog;

    public UpdateDialog(Context context) {
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.update_dialog_content, null);
        view.measure(0, 0);
        title_tv = (TextView) view.findViewById(R.id.title_tv);
        content_tv = (TextView) view.findViewById(R.id.content_tv);
        yes_btn = (Button) view.findViewById(R.id.yes_btn);
        no_btn = (Button) view.findViewById(R.id.no_btn);
        mBuilder = new AlertDialog.Builder(context);
        mBuilder.setCancelable(false)
                .setView(view);
    }

    public UpdateDialog setTitle(String title) {
        title_tv.setText(title);
        return this;
    }

    public UpdateDialog setContent(String content) {
        content_tv.setText(content);
        return this;
    }

    public UpdateDialog setYesClickListener(View.OnClickListener listener) {
        yes_btn.setOnClickListener(listener);
        return this;
    }

    public UpdateDialog setNoClickListener(View.OnClickListener listener) {
        no_btn.setOnClickListener(listener);
        return this;
    }

    public void show() {
        mDialog = mBuilder.create();
        mDialog.show();
    }

    public void dismiss(){
        if (mDialog!=null)
            mDialog.dismiss();
    }
}
