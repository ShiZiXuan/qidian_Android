package com.uautogo.qidian.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.uautogo.qidian.R;
import com.uautogo.qidian.model.AddressBean;


/**
 * 收货地址
 */

public class AdressActivity extends BaseActivity implements View.OnClickListener {
    private EditText name_et, phone_et, address_et;
    private LinearLayout area_ll;
    private TextView area_et;
    private ImageView illegal_top_bar_left_img;
    private Button save_tv;
    private InputMethodManager imm;//虚拟键盘

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);

        final CityPickerView cityPicker = new CityPickerView.Builder(AdressActivity.this)
                .textSize(20)
//                .title("地址选择")
                .title(null)
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#0000FF")
                .backgroundPop(0xa0000000)
                .confirTextColor("#0000FF")
                .cancelTextColor("#0000FF")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        name_et = (EditText) findViewById(R.id.name_et);
        illegal_top_bar_left_img = (ImageView) findViewById(R.id.illegal_top_bar_left_img);
        phone_et = (EditText) findViewById(R.id.phone_et);
        address_et = (EditText) findViewById(R.id.address_et);
        area_et =  findViewById(R.id.area_et);
        area_ll = findViewById(R.id.area_ll);
        save_tv = findViewById(R.id.save_tv);
        save_tv.setOnClickListener(this);
        illegal_top_bar_left_img.setOnClickListener(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        area_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityPicker.show();
                imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
            }
        });
        AddressBean address = (AddressBean) getIntent().getSerializableExtra("address");
            if(address!=null){
                name_et.setText(address.getName());
                phone_et.setText(address.getPhone());
                area_et.setText(address.getAddress());
                address_et.setText(address.getAddressDetail());
            }
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {

            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                String info = province.getName() + " " + city.getName() + " " + district.getName();
                area_et.setText(info);
                //返回结果
                //ProvinceBean 省份信息
                //CityBean     城市信息
                //DistrictBean 区县信息
                Log.e("===" + province.getName() + "===" + city.getName() + "===", "===" + district.getName() + "===");
            }

            @Override
            public void onCancel() {

            }
        });
        //load();
    }

//    public void save() {
//
//        SharedPreferencesUtils.putString(AdressActivity.this, "name", name_et.getText().toString());
//        SharedPreferencesUtils.putString(AdressActivity.this, "phone", phone_et.getText().toString());
//        SharedPreferencesUtils.putString(AdressActivity.this, "area", area_et.getText().toString());
//        SharedPreferencesUtils.putString(AdressActivity.this, "address", address_et.getText().toString());
//        String s = area_et.getText().toString();
//        if (TextUtils.isEmpty(s)) {
//            SharedPreferencesUtils.putString(AdressActivity.this, "province", "");
//            SharedPreferencesUtils.putString(AdressActivity.this, "city", "");
//            SharedPreferencesUtils.putString(AdressActivity.this, "district", "");
//        } else {
//            String[] split = s.split(" ");
//            SharedPreferencesUtils.putString(AdressActivity.this, "province", split[0]);
//            SharedPreferencesUtils.putString(AdressActivity.this, "city", split[1]);
//            SharedPreferencesUtils.putString(AdressActivity.this, "district", split[2]);
//        }
//    }
//
//    public void load() {
//        String name = SharedPreferencesUtils.getString(AdressActivity.this, "name", "");
//        name_et.setText(name);
//        name_et.setSelection(name.length());
//        name_et.setFocusable(true);
//        name_et.setFocusableInTouchMode(true);
//        name_et.requestFocus();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        phone_et.setText(SharedPreferencesUtils.getString(AdressActivity.this, "phone", ""));
//        area_et.setText(SharedPreferencesUtils.getString(AdressActivity.this, "area", ""));
//        address_et.setText(SharedPreferencesUtils.getString(AdressActivity.this, "address", ""));
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_tv:
                //save();
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.illegal_top_bar_left_img:
                finish();
                break;
        }
    }


}