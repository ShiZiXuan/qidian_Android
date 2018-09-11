package com.uautogo.qidian.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy on 2017/5/14.
 */

public class WeatherModel {

    @SerializedName("resultcode") public String resultCode;
    @SerializedName("reason") public String reason;
    @SerializedName("result") public WeatherResult result;
    @SerializedName("error_code") public String error_code;

    public class WeatherResult{
        @SerializedName("resultcode") public String resultCode;
    }

    public class WeatherDataToday{
//        "today":{
//            "temperature":"18℃~31℃",
//                    "weather":"多云转晴",
//                    "weather_id":{
//                "fa":"01",
//                        "fb":"00"
//            },
//            "wind":"东风微风",
//                    "week":"星期六",
//                    "city":"无锡",
//                    "date_y":"2017年05月13日",
//                    "dressing_index":"热",
//                    "dressing_advice":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。",
//                    "uv_index":"中等",
//                    "comfort_index":"",
//                    "wash_index":"较适宜",
//                    "travel_index":"较适宜",
//                    "exercise_index":"较适宜",
//                    "drying_index":""
//        },
        @SerializedName("temperature") public String temperature;
        @SerializedName("weather") public String weather;
        @SerializedName("resultcode") public String resultCode;
    }

    public class WeatherDataFuture {


//    "future":{
//        "day_20170513":{
//            "temperature":"18℃~31℃",
//                    "weather":"多云转晴",
//                    "weather_id":{
//                "fa":"01",
//                        "fb":"00"
//            },
//            "wind":"东风微风",
//                    "week":"星期六",
//                    "date":"20170513"
//        },
//        "day_20170514":{
//            "temperature":"17℃~29℃",
//                    "weather":"多云转阴",
//                    "weather_id":{
//                "fa":"01",
//                        "fb":"02"
//            },
//            "wind":"东风微风",
//                    "week":"星期日",
//                    "date":"20170514"
//        },
//        "day_20170515":{
//            "temperature":"16℃~23℃",
//                    "weather":"小雨转阴",
//                    "weather_id":{
//                "fa":"07",
//                        "fb":"02"
//            },
//            "wind":"东风微风",
//                    "week":"星期一",
//                    "date":"20170515"
//        },
//        "day_20170516":{
//            "temperature":"15℃~24℃",
//                    "weather":"阴转多云",
//                    "weather_id":{
//                "fa":"02",
//                        "fb":"01"
//            },
//            "wind":"东南风微风",
//                    "week":"星期二",
//                    "date":"20170516"
//        },
//        "day_20170517":{
//            "temperature":"16℃~28℃",
//                    "weather":"多云",
//                    "weather_id":{
//                "fa":"01",
//                        "fb":"01"
//            },
//            "wind":"东南风微风",
//                    "week":"星期三",
//                    "date":"20170517"
//        },
//        "day_20170518":{
//            "temperature":"16℃~23℃",
//                    "weather":"小雨转阴",
//                    "weather_id":{
//                "fa":"07",
//                        "fb":"02"
//            },
//            "wind":"东风微风",
//                    "week":"星期四",
//                    "date":"20170518"
//        },
//        "day_20170519":{
//            "temperature":"17℃~29℃",
//                    "weather":"多云转阴",
//                    "weather_id":{
//                "fa":"01",
//                        "fb":"02"
//            },
//            "wind":"东风微风",
//                    "week":"星期五",
//                    "date":"20170519"
//        }
//    }
//},
    }
}
