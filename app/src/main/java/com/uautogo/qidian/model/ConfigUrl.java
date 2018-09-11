package com.uautogo.qidian.model;

/**
 * Created by uuun on 2018/8/2.
 */

public class ConfigUrl {
    //服务器地址
    public final static String URL_BASE = "https://etc.uautogo.com/api/ckeInfo";

    //查询CKE余额
    public final static String URL_GETCARMONEY = URL_BASE+"/queryCkeInfoById";

    //设置支付密码
    public final static String URL_SETPASSWORD = URL_BASE+"/updateCkePayPasswrd ";

    //提现请求
    public final static String URL_MAKEMONEY = URL_BASE+"/goStartPay ";


    //账单详情
    public final static String URL_COSTDETAIL = URL_BASE+"/queryCKERecordList ";

    //获取验证码
    public final static String URL_PHONECODE = URL_BASE+"/getPhoneVerificationCode";

    //重置密码
    public final static String URL_RESETPW = URL_BASE+"/resetCkePayPasswrd";

    //获取ETC账单列表
    public final static String URL_GETETCCOST = "https://etc.uautogo.com/api/record/queryOrderRecordByUserId";

    //获取用户填写etc步骤
    public final static String URL_GETETCDETAIL = "https://etc.uautogo.com/api/etcInfo/isHandleSuccess";

    //用户支付ETC账单
    public final static String URL_ETCPAY = "https://etc.uautogo.com/api/etcbuy/pay";

    //获取用户所有车辆
    public final static String URL_GETALLCAR = "https://etc.uautogo.com/api/etcInfo/queryCarListByUserId";

    //获取分享数据
    public final static String URL_GETSHAREMSG = "https://etc.uautogo.com/api/user/queryShareInfo";

    //获取分享用户列表
    public final static String URL_GETSHARELIST = "https://etc.uautogo.com/api/user/queryShareUserList";

    //获取二维码
    public final static String URL_GETCODE= "https://etc.uautogo.com/api/user/vcode";
}
