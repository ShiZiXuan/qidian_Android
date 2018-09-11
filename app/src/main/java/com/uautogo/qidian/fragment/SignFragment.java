package com.uautogo.qidian.fragment;

import android.app.Fragment;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uautogo.qidian.R;
import com.uautogo.qidian.activity.ProblemActivity;

/**
 * Created by linjing on 2018/4/23.
 * etc签约界面
 */

public class SignFragment extends Fragment {
    Button copy_bt;
    TextView toWeb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_msg, container, false);
       initView(view);
        return view;
    }
        public void show(){

        }
    public void initView(View view) {
        copy_bt = view.findViewById(R.id.sign_copy_bt);
        toWeb = view.findViewById(R.id.sign_toWeb);
        copy_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText("1192842554");
                Toast.makeText(getActivity(), "复制成功!", Toast.LENGTH_LONG).show();
            }
        });
        toWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProblemActivity.class);
                intent.putExtra("web","sign");
                startActivity(intent);
            }
        });
    }
}
