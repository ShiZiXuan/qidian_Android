package com.uautogo.qidian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.uautogo.qidian.R;
import com.uautogo.qidian.adapter.AddLvAdapter;
import com.uautogo.qidian.model.AddressBean;

import java.util.ArrayList;
import java.util.List;

public class AddressListActivity extends AppCompatActivity {
    private ListView lv;
    private AddLvAdapter adapter;
    private List<AddressBean> list = new ArrayList<>();
    private Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        initView();
        initMethod();
    }

    private void initMethod() {
        but.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent intent = new Intent(AddressListActivity.this,AdressActivity.class);
                                       startActivity(intent);
                                   }
                               }
        );
    }

    private void initView() {
        lv = findViewById(R.id.add_lv);
        but = findViewById(R.id.addAddress_but);

        for(int i=0;i<20;++i){
            list.add(new AddressBean("王三丰","北京市北京市海淀区","12345678900","魏公村理工科技大厦1505"));
        }
        adapter = new AddLvAdapter(list,this);
        lv.setAdapter(adapter);

    }
}
