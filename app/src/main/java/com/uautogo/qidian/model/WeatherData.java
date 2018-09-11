package com.uautogo.qidian.model;

/**
 * Created by Jeremy on 2017/5/13.
 */

public class WeatherData {
   public String resultcode;
   public Result result;



    public class Result{
        public Today today;
        public Intime sk;

    }
//    "temperature":"22℃~36℃",
//            "weather":"多云转阴",
//            "weather_id":{
//        "fa":"01",
//                "fb":"02"
//    },
//            "wind":"微风",
//            "week":"星期日",
//            "city":"北京",
//            "date_y":"2017年05月28日",
//            "dressing_index":"炎热",
//            "dressing_advice":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。",
//            "uv_index":"弱",
//            "comfort_index":"",
//            "wash_index":"较适宜",
//            "travel_index":"较不宜",
//            "exercise_index":"较不宜",
//            "drying_index":"
//    "sk":{
//        "temp":"26",
//                "wind_direction":"西南风",
//                "wind_strength":"4级",
//                "humidity":"28%",
//                "time":"18:46"
//    },

    public class Today{
        public String temperature;
        public String weather;
        public String wind;
        public String week;
        public String city;
        public String date_y;
    }

    public class Intime{
        public String temp;
    }
}
