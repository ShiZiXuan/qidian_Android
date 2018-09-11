package com.uautogo.qidian.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uautogo.qidian.R;
import com.uautogo.qidian.adapter.InviteLvAdapter;
import com.uautogo.qidian.model.ConfigUrl;
import com.uautogo.qidian.model.InviteBean;
import com.uautogo.qidian.utils.SharedPreferencesUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2018/8/21.
 */

public class ShareFragemnt extends Fragment {
    private View view;
    private ListView lv;
    private InviteLvAdapter adapter;
    private List<InviteBean.DataBean.ListBean> list = new ArrayList<>();
    private TextView toShare,num,money;
    private Handler handler = new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_invite,container,false);
        initView();
        initMethod();
        x.Ext.init(getActivity().getApplication());//Xutils初始化
        return view;
    }
    private void initMethod() {
        toShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
    }

    private void initView() {
        lv = view.findViewById(R.id.invite_lv);
        toShare = view.findViewById(R.id.toShare);
        num = view.findViewById(R.id.invite_num);
        money = view.findViewById(R.id.invite_money);

        loadData();
    }

    private void loadData() {
        String url = ConfigUrl.URL_GETSHAREMSG;
        RequestParams params = new RequestParams(url);
        int userId = Integer.parseInt(SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_ID));
        params.addParameter("userId",userId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("分享信息----->",s);
                try {
                    JSONObject obj = new JSONObject(s);
                    JSONObject data = obj.getJSONObject("data");
                    String rNum = data.getString("rNum");
                    int rSuccessNum = data.getInt("rSuccessNum");
                    num.setText("已邀请"+rNum+"人");
                    money.setText((rSuccessNum*30)+"");
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

        String url1 = ConfigUrl.URL_GETSHARELIST;
        RequestParams params1 = new RequestParams(url1);
        params1.addParameter("userId",userId);
        params1.addParameter("pn",1);
        params1.addParameter("pSize",20);
        x.http().post(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
               // Log.e("分享列表---->",s);
                InviteBean bean = new Gson().fromJson(s, InviteBean.class);
                list = bean.getData().getList();
                adapter = new InviteLvAdapter(getActivity(),list);
                lv.setAdapter(adapter);
//                lv.setSelection(ListView.FOCUS_DOWN);
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter = new InviteLvAdapter(getActivity(),list);
//                        lv.setAdapter(adapter);
//                    }
//                },500);
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

    }

    private void share() {
        SharedPreferencesUtils.putString(getActivity(),"isWxLogin","no");
        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA};
        int rid = Integer.parseInt(SharedPreferencesUtils.getString(getActivity(), SharedPreferencesUtils.Key.KEY_USER_ID));
        final String weixinWeb = "https://www.uautogo.com/dom/index.html?rid="+rid;
        final String sircleWeb = "https://www.uautogo.com/dom/share";

        new ShareAction(getActivity()).setDisplayList(displaylist)
              //  .withMedia(web)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                        if(share_media == SHARE_MEDIA.WEIXIN){
                           new ShareAction(getActivity()).setPlatform(share_media).setCallback(shareListener)
                                   .withMedia(getWeb(weixinWeb))
                                   .share();
                        }else if(share_media == SHARE_MEDIA.WEIXIN_CIRCLE || share_media == SHARE_MEDIA.SINA){
                            new ShareAction(getActivity()).setPlatform(share_media).setCallback(shareListener)
                                    .withMedia(getWeb(sircleWeb))
                                    .share();
                        }
                    }
                })
                .open();


    }
    private UMWeb getWeb(String url){
        UMWeb web = new UMWeb(url);
        web.setTitle("智慧ETC 快速办理");//标题
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iconb);
        web.setThumb(new UMImage(getActivity(), bitmap));  //缩略图
        web.setDescription("先通行,后付费,免安装,免预存,免绑定银行卡");//描述
        return web;
    }
    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.e("!!!!!!!!!!!!","开始回调");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Log.e("!!!!!!!!!!!!","成功回调");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "分享成功!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.e("!!!!!!!!!!!!","失败回调");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.e("!!!!!!!!!!!!","取消回调");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "分享取消", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

}
