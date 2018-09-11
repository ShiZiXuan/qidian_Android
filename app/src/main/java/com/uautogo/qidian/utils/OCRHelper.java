package com.uautogo.qidian.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OCRHelper {
    private String appcode = "e0ab083a148f437694c649bc30456274";
    private String idUrl = "http://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json";
    private String driveUrl = "http://dm-53.data.aliyun.com/rest/160601/ocr/ocr_vehicle.json";
    private String bankUrl = "http://api06.aliyun.venuscn.com/ocr/bank-card";
    private String carNumberUrl = "http://jisucpsb.market.alicloudapi.com/licenseplaterecognition/recognize";

    private static final MediaType JSON = MediaType.parse("application/json; charset=UTF-8");
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.w("================", "" + message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);
    private OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

    public void getIDCardInfo(Bitmap bitmap, boolean isZhengMian, ResultCallback callback) {
        getInfo(idUrl, compressImage(bitmap), isZhengMian, callback);
    }

    public void getDriveCardInfo(Bitmap bitmap, boolean isZhengMian, ResultCallback callback) {
        getInfo(driveUrl, compressImage(bitmap), isZhengMian, callback);
    }

    public void getBankCardInfo(Bitmap bitmap, boolean isZhengMian, final ResultCallback callback) {
        byte[] bytes = getBitmapByte(compressImage(bitmap));
        try {
            String s = new String(Base64.encode(bytes, Base64.NO_WRAP), "utf-8");
            FormBody formBody = new FormBody
                    .Builder()
                    .add("pic", s)//设置参数名称和参数值
                    .build();
            final Request request = new Request.Builder()
                    .url(bankUrl)
                    .addHeader("Authorization", "APPCODE " + appcode)
                    .post(formBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    callback.onFailure();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (request != null && response.body() != null) {
                        try {
                            String result = response.body().string();
                            Log.e("=============", "==" + result);
                            JSONObject object = new JSONObject(result);
                            if (object.optInt("ret") == 200) {
                                callback.onSuccess(object.getString("data"));
                            } else {
                                callback.onFailure();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onFailure();
                        }
                    } else {
                        callback.onFailure();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCarNumber(Bitmap bitmap, final ResultCallback callback) {
        byte[] bytes = getBitmapByte(compressImage(bitmap));
        try {
            String s = new String(Base64.encode(bytes, Base64.NO_WRAP), "utf-8");
            FormBody formBody = new FormBody
                    .Builder()
                    .add("pic", s)//设置参数名称和参数值
                    .build();
            final Request request = new Request.Builder()
                    .url(carNumberUrl)
                    .addHeader("Authorization", "APPCODE " + appcode)
                    .post(formBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    callback.onFailure();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (request != null && response.body() != null) {
                        try {
                            String result = response.body().string();
                            Log.e("=============", "==" + result);
                            JSONObject object = new JSONObject(result);
                            object = object.optJSONObject("result");
                            callback.onSuccess(object.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onFailure();
                        }
                    } else {
                        callback.onFailure();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getInfo(String url, Bitmap bitmap, boolean isZhengMian, ResultCallback callback) {
        byte[] bytes = getBitmapByte(bitmap);
        try {
            String s = new String(Base64.encode(bytes, Base64.NO_WRAP), "utf-8");
            String requestBody = getRequestBody(s, isZhengMian);
            post(url, requestBody, callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRequestBody(String bitmapBase64, boolean isZhengMian) {
        String faceOrBack = isZhengMian ? "face" : "back";
        return "{\"inputs\":[{\"image\":{\"dataType\":50,\"dataValue\":\"" + bitmapBase64 + "\"},\"configure\":{\"dataType\":50,\"dataValue\":\"{\\\"side\\\":\\\"" + faceOrBack + "\\\"}\"}}]}";
    }

    private byte[] getBitmapByte(Bitmap bitmap) {   //将bitmap转化为byte[]类型也就是转化为二进制
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
            bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    private void post(final String url, String json, final ResultCallback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "APPCODE " + appcode)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                callback.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (request != null && response.body() != null) {
                    try {
                        String result = response.body().string();
                        Log.e("=============", "==" + result);
                        JSONObject object = new JSONObject(result);
                        JSONArray array = object.optJSONArray("outputs");
                        object = array.getJSONObject(0);
                        object = object.optJSONObject("outputValue");
                        result = object.optString("dataValue");
                        callback.onSuccess(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }
        });
    }

    /**
     * 图片质量压缩
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 1024 * 1.8) {    // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        opts.inSampleSize = 2;
        opts.inJustDecodeBounds = false;
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, opts);// 把ByteArrayInputStream数据生成图片
//        try {
//            FileOutputStream out = new FileOutputStream(srcPath);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return bitmap;
    }

    public interface ResultCallback {
        void onSuccess(String result);

        void onFailure();
    }
}