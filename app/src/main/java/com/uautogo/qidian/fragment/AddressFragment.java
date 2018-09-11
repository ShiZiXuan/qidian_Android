package com.uautogo.qidian.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.uautogo.qidian.R;
import com.uautogo.qidian.activity.AllMsgActivity;

/**
 * Created by linjing on 2018/4/23.
 */

public class AddressFragment extends Fragment {
    private InputMethodManager imm;
    CityPickerView cityPicker;
    LinearLayout choose_city_ll;
    EditText adress_tv;
    public EditText name_et, number_et, xiangxiadress_et;
    public String provinceName, cityName, districtName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        cityPicker = new CityPickerView.Builder(getActivity())
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
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                String info = province.getName() + " " + city.getName() + " " + district.getName();
                adress_tv.setText(info);
                //返回结果
                //ProvinceBean 省份信息
                //CityBean     城市信息
                //DistrictBean 区县信息
                Log.e("===" + province.getName() + "===" + city.getName() + "===", "===" + district.getName() + "===");
                provinceName = province.getName();
                cityName = city.getName();
                districtName = district.getName();
            }

            @Override
            public void onCancel() {

            }
        });
        View view = inflater.inflate(R.layout.address_msg, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        adress_tv = view.findViewById(R.id.adress_tv);
        choose_city_ll = (LinearLayout) view.findViewById(R.id.choose_city_ll);
        name_et = (EditText) view.findViewById(R.id.name_et);
        number_et = (EditText) view.findViewById(R.id.number_et);
        xiangxiadress_et = (EditText) view.findViewById(R.id.xiangxiadress_et);

        choose_city_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityPicker.show();
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
    }

    public void saveAddress() {
        String address = xiangxiadress_et.getText().toString().trim();
        String name = name_et.getText().toString().trim();
        String phone = number_et.getText().toString().trim();
        if (TextUtils.isEmpty(provinceName) ||
                TextUtils.isEmpty(cityName) ||
                TextUtils.isEmpty(districtName) ||
                TextUtils.isEmpty(address) ||
                TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(phone)) {
            Toast.makeText(getActivity(), "请填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        AllMsgActivity.receiverName = name;
        AllMsgActivity.receiverMobile = phone;
        AllMsgActivity.receiverAddress = address;
        AllMsgActivity.receiverProvince = provinceName;
        AllMsgActivity.receiverCity = cityName;
        AllMsgActivity.receiverDistrict = districtName;
        ((AllMsgActivity) getActivity()).goNext();
    }
}
