package com.luo.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p color="red">链接类型</p>
 * <pre>
 *     {
 *     "msgtype": "link",
 *     "link": {
 *         "text": "这个即将发布的新版本，创始人陈航（花名“无招”）称它为“红树林”。
 * 而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是“红树林”？",
 *         "title": "时代的火车向前开",
 *         "picUrl": "",
 *         "messageUrl": "https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI"
 *     }
 * }
 * </pre>
 */
@Data
@Accessors(chain = true)
public class LinkEntity extends TypeEntity{

    private String msgType;

    // 显示标题
    private String title;

    // 显示内容
    private String content;

    // icon url
    private String picUrl;

    // 内容对链接
    private String messageUrl;

    public String getMsgType() {
        return "link";
    }

    public String getJSONObjectString() {
        // text类型
        JSONObject linkContent = new JSONObject();
        linkContent.put("title", this.getTitle());
        linkContent.put("text", this.getContent());
        linkContent.put("picUrl", this.getPicUrl());
        linkContent.put("messageUrl", this.getMessageUrl());

        JSONObject json = new JSONObject();
        json.put("msgtype", this.getMsgType());
        json.put("link", linkContent);
        return json.toJSONString();
    }

}
