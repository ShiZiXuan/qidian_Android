package com.uautogo.qidian.model;

/**
 * Created by Jeremy on 2017/5/13.
 */

//{
//        "resultcode": "200",
//        "reason": "查询成功##",
//        "result": {
//        "province": "NX",
//        "city": "NX_YINCHUAN",
//        "hphm": "宁A79A29",
//        "hpzl": "02",
//        "lists": [
//        {
//        "date": "2016-02-27 14:12:00",
//        "area": "永宁许黄路与李银路路口",
//        "act": "机动车违反禁止标线指示的",
//        "code": "",
//        "fen": "3",
//        "money": "100",
//        "handled": "0"
//        }
//        ]
//        },
//        "error_code": 0
//        }

public class CarQueryData {

    public String date;
    public String area;
    public String act;
    public String fen;
    public String money;
    public String handled;
}
