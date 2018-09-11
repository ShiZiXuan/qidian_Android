package com.uautogo.qidian.widget;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uautogo.qidian.R;
import com.uautogo.qidian.ServiceApi.WangZKRequestApi;
import com.uautogo.qidian.activity.AllMsgActivity;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OCRDialog {
    private AlertDialog mDialog;
    private Context mContext;
    private TextView id_card_number_tv, id_card_date_tv,  bank_card_number_tv, bank_card_type_tv, bank_card_name_tv, bank_card_phone_tv,name_et;
    private TextView car_card_number_tv, car_distinguish_code_tv, car_engine_number_tv, car_travel_create_time_tv;
    private Button ok_btn,remake_btn;

    private EditText id_card_address_tv;

    private int number,carId;
    private String vcode, imageUrls,carCardAdd,carType,carOwner,use,model,regist_date;

    public OCRDialog(Context context, int number,Application application) {
        mContext = context;
        this.number = number;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ocr, null, false);
        initView(view);
        mDialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        Window window = mDialog.getWindow();
        /* 设置显示窗口的宽高 */
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        /* 设置窗口显示位置 */
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(R.color.transparent);
        x.Ext.init(application);
    }

    private void initView(View view) {

        name_et =  view.findViewById(R.id.name_et);
        id_card_number_tv = (TextView) view.findViewById(R.id.id_card_number_tv);
        id_card_date_tv = (TextView) view.findViewById(R.id.id_card_date_tv);
        //id_card_address_tv =  view.findViewById(R.id.id_card_address_tv);
        bank_card_number_tv = (TextView) view.findViewById(R.id.bank_card_number_tv);
        bank_card_type_tv = (TextView) view.findViewById(R.id.bank_card_type_tv);
        bank_card_name_tv = (TextView) view.findViewById(R.id.bank_card_name_tv);
        id_card_address_tv =  view.findViewById(R.id.id_card_address_tv);
        bank_card_phone_tv = (TextView) view.findViewById(R.id.bank_card_phone_tv);
        car_card_number_tv = (TextView) view.findViewById(R.id.car_card_number_tv);
        car_distinguish_code_tv = (TextView) view.findViewById(R.id.car_distinguish_code_tv);
        car_engine_number_tv = (TextView) view.findViewById(R.id.car_engine_number_tv);
        car_travel_create_time_tv = (TextView) view.findViewById(R.id.car_travel_create_time_tv);
        remake_btn = view.findViewById(R.id.remake_bt);
        ConstraintLayout first_page_result_cl = (ConstraintLayout) view.findViewById(R.id.first_page_result_cl);
        ConstraintLayout second_page_result_cl = (ConstraintLayout) view.findViewById(R.id.second_page_result_cl);
        if (number == 1) {
            first_page_result_cl.setVisibility(View.VISIBLE);
            second_page_result_cl.setVisibility(View.GONE);
        } else if (number == 2) {
            first_page_result_cl.setVisibility(View.GONE);
            second_page_result_cl.setVisibility(View.VISIBLE);
        }
        remake_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //输入框获取焦点
                id_card_address_tv.setFocusable(true);
                id_card_address_tv.setFocusableInTouchMode(true);
                id_card_address_tv.requestFocus();

                //拉起键盘
                InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(id_card_address_tv,0);
            }
        });

        ok_btn = (Button) view.findViewById(R.id.ok_btn);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();
                map.put("bidImageUrl", imageUrls);
                if (number == 1) {
                    map.put("bidUserid", Integer.parseInt(SharedPreferencesUtils.getString(mContext, SharedPreferencesUtils.Key.KEY_USER_ID)));
                    map.put("bidUserPhone", SharedPreferencesUtils.getString(mContext, SharedPreferencesUtils.Key.KEY_USER_PHONE));
                    /*
                     * 身份证
                     */
                    map.put("bidName", name_et.getText().toString());
                    map.put("bidIdentityNumber", id_card_number_tv.getText().toString());
                    map.put("bidIdentityAddress", id_card_address_tv.getText().toString());
                    map.put("bidExpiryDate", id_card_date_tv.getText().toString());
                    /*
                     * 银行卡
                     */
                    map.put("bidBankPhone", bank_card_phone_tv.getText().toString());
                    map.put("bidBankType", bank_card_type_tv.getText().toString());
                    map.put("bidBankName", bank_card_name_tv.getText().toString());
                    map.put("bidBankcardNumber", bank_card_number_tv.getText().toString());
                    map.put("type", "0");
//                    map.put("vcode", vcode);

                    WangZKRequestApi.getInstance().uploadFirstPage(map, new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String json = response.body().string();
                                JSONObject object = new JSONObject(json);
                                int code = object.optInt("code");
                                if (code == 0) {
                                    object = object.optJSONObject("data");
                                    if (object.optInt("code") == 200) {
                                        ((AllMsgActivity) mContext).first_result_number = object.optString("etcInfoId");
                                        ((AllMsgActivity) mContext).goNext();
                                        dismiss();
                                    } else {
                                        Toast.makeText(mContext, object.optString("msg"), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(mContext, object.optString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                } else if (number == 2) {
                    String url = "https://etc.uautogo.com/api/etcInfo/insertEtctravelInfo";
                    RequestParams params = new RequestParams(url);
                    params.addParameter("userId", Integer.parseInt(SharedPreferencesUtils.getString(mContext, SharedPreferencesUtils.Key.KEY_USER_ID)));
                    params.addParameter("etcId", AllMsgActivity.first_result_number);
                    params.addParameter("bidCarNumber", car_card_number_tv.getText().toString());
                    params.addParameter("bidCarDistinguishCode", car_distinguish_code_tv.getText().toString());
                    params.addParameter("bidEngineNumber", car_engine_number_tv.getText().toString());
                    params.addParameter("bidTravelCreateTime", car_travel_create_time_tv.getText().toString());
                    params.addParameter("etcType", 0);
                    params.addParameter("bidImageUrl",imageUrls);
                    params.addParameter("vehicleType",carType);
                    params.addParameter("owner",carOwner);
                    params.addParameter("useCharacter",use);
                    params.addParameter("addr",carCardAdd);
                    params.addParameter("model",model);
                    params.addParameter("issueDate",regist_date);

                    x.http().post(params, new org.xutils.common.Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Log.e("车辆信息---->",s);
                            String json = s;
                            try {
                                JSONObject js = new JSONObject(json);
                                int code = js.getInt("code");
                                JSONObject data = js.getJSONObject("data");
                                int carId = data.getInt("carId");
                                SharedPreferencesUtils.putInt(mContext,"carId",carId);
                                if(code==0){
                                    ((AllMsgActivity) mContext).goNext();
                                    dismiss();
                                }else{
                                    Toast.makeText(mContext, js.optString("msg"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onError(Throwable throwable, boolean b) {

                        }

                        @Override
                        public void onCancelled(CancelledException e) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
//                    WangZKRequestApi.getInstance().uploadSecondPage(map, new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            Log.e("车辆信息2----->","!!!!!!!!!!!!!!!");
//                            try {
//                                String json = response.body().string();
//
//                                JSONObject object = new JSONObject(json);
//                                int code = object.optInt("code");
//                                if (code == 0) {
//                                    ((AllMsgActivity) mContext).goNext();
//                                    dismiss();
//                                } else {
//                                    Toast.makeText(mContext, object.optString("msg"), Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                        }
//                    });

                }
            }
        });
    }

    public void setCarCardNumber(String number) {
        car_card_number_tv.setText(number);
    }
    public void setCarCardAdd(String add) {
        carCardAdd = add;
    }
    public void setCarType(String type) {
        carType = type;
    }
    public void setOwner(String owner) {
        carOwner = owner;
    }
    public void setUseChara(String useChara) {
        use = useChara;
    }
    public void setModel(String model1) {
        model = model1;
    }
    public void setDate(String date) {
        regist_date = date;
    }

    public void setCarDistinguishCode(String code) {
        car_distinguish_code_tv.setText(code);
    }

    public void setCarEngineNumber(String number) {
        car_engine_number_tv.setText(number);
    }

    public void setCarTravelCreateTime(String time) {
        car_travel_create_time_tv.setText(time);
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public void show() {
        mDialog.show();
    }

    public void setName(String name) {
        name_et.setText(name);
        //name_et.setSelection(name_et.getText().toString().length());
    }

    public void setIdCardNumber(String number) {
        id_card_number_tv.setText(number);
    }

    public void setIdCardDate(String date) {
        id_card_date_tv.setText(date);
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setIdCardAddress(String address) {
        id_card_address_tv.setText(address);
        id_card_address_tv.setSelection(name_et.getText().toString().length());
    }

    public void setBankCardNumber(String number) {
        bank_card_number_tv.setText(number);
    }

    public void setBankCardType(String type) {
        bank_card_type_tv.setText(type);
    }

    public void setBankCardName(String name) {
        bank_card_name_tv.setText(name);
    }

    public void setBankCardPhone(String phone) {
        bank_card_phone_tv.setText(phone);
    }
}
