package com.uautogo.qidian.model;

/**
 * Created by Jeremy on 2017/5/13.
 */

public class XianXingData {
   public String resultcode;
   public Result result;
//    "result":{
//        "date":"2017-06-03",
//                "week":"星期六",
//                "city":"beijing",
//                "cityname":"北京",
//                "des":[
//        {
//            "time":"私家车：限行时间段为周一至五的早7时至晚20时(法定节假日和公休日不限行)",
//                "place":"限行范围为五环路以内（不包括五环路主路）",
//                "info":""
//        },
//        {
//            "time":"公务车：停驶时间为0时至24时",
//                "place":"停驶范围为本市行政区域内所有道路",
//                "info":""
//        }
//        ],
//        "fine":"京牌罚100块，不扣分。非京牌罚100块，扣3分。",
//                "remarks":"临时号牌按号牌尾号数字限行。机动车车尾号为英文字母的按0号管理",
//                "isxianxing":0,
//                "xxweihao":null,
//                "holiday":""
//    },


    public class Result{
        public int isxianxing;
        public String[] xxweihao;

    }

}
