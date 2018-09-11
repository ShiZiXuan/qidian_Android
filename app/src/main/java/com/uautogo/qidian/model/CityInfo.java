package com.uautogo.qidian.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jeremy on 2017/5/13.
 */

public class CityInfo {

    public String city_name;
    public String city_code;
    public String abbr;

    public static List<CityInfo> parseData(String data){
        List<CityInfo> cityInfos = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject result = jsonObject.getJSONObject("result");
            Iterator<String> iterator = result.keys();
            while (iterator.hasNext()) {
                CityInfo cityInfo = new CityInfo();
                String key = iterator.next();
                JSONObject keyJSON = result.getJSONObject(key);
                String city_name = keyJSON.getString("province");
                String city_code = keyJSON.getString("province_code");
                JSONArray cityArray = keyJSON.getJSONArray("citys");
                String abbr = cityArray.getJSONObject(0).getString("abbr");

                cityInfo.city_name = city_name;
                cityInfo.city_code = city_code;
                cityInfo.abbr = abbr;
                cityInfos.add(cityInfo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cityInfos;
    }

}
