package com.luo.utils;

import com.alibaba.fastjson.JSONObject;
import com.luo.config.Constants;
import com.luo.entity.TypeEntity;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DingTalkUtils {

    private static final OkHttpClient client = new OkHttpClient();

    static String type = "application/json; charset=utf-8;text/plain;*/*";

    /**
     * 发送钉钉消息
     * @param jsonString 消息内容
     * @param webhook 钉钉自定义机器人webhook
     * @return /
     */
    public static boolean sendToDingTalk(String jsonString, String webhook) {
        try{
            JSONObject res = getPostJsonObject(jsonString, webhook);
            return res.getIntValue("errcode") == 0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * post请求方式
     * @param jsonStringBody json类型请求体
     * @param url 请求路径
     * @return /
     * @throws IOException IO异常
     */
    public static JSONObject getPostJsonObject(String jsonStringBody, String url) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse(type), jsonStringBody);
        Request.Builder builder = new Request.Builder().url(url);
        builder.addHeader("Content-Type", type).post(body);
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        String string = response.body().string();
        System.out.printf("send message:%s%n", string);
        return JSONObject.parseObject(string);
    }

    public static JSONObject getGetJsonObject(String url) throws IOException {
        Request.Builder builder = new Request.Builder().url(url);
        builder.addHeader("Content-Type", type).get();
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        String string = response.body().string();
        System.out.printf("send message:%s%n", string);
        return JSONObject.parseObject(string);
    }

    public static boolean batchSend(List<? extends TypeEntity> entities) {
        for (TypeEntity entity : entities) {
            Constants.maxMinuteCount.getAndDecrement();
            if (Constants.maxMinuteCount.intValue() > 0) {
                Constants.webhooks.forEach(webhook -> DingTalkUtils.sendToDingTalk(entity.getJSONObjectString(),webhook));
            }else{
                try {
                    TimeUnit.SECONDS.sleep(60);
                    Constants.maxMinuteCount.set(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

}
