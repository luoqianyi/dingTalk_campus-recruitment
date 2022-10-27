package com.luo.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <p color="red">FeedCard类型</p>
 * <pre>
 *     {
 *     "feedCard": {
 *         "links": [
 *             {
 *                 "title": "时代的火车向前开",
 *                 "messageURL": "https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI",
 *                 "picURL": "https://www.dingtalk.com/"
 *             },
 *             {
 *                 "title": "时代的火车向前开2",
 *                 "messageURL": "https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI",
 *                 "picURL": "https://www.dingtalk.com/"
 *             }
 *         ]
 *     },
 *     "msgtype": "feedCard"
 * }
 * </pre>
 */
@Data
@Accessors(chain = true)
public class FeedCardEntity {

    private String msgType;

    // links
    private List<LinkEntity> links;

    public String getMsgType() {
        return "feedCard";
    }

    public String getJSONObjectString() {

        // text类型
        JSONObject feedCardContent = new JSONObject();
        List<LinkEntity> links = new ArrayList<LinkEntity>();
        for (int i=0;i<this.getLinks().size();i++){
            links.add(this.getLinks().get(i));
        }
        if(this.getLinks().size()>0){
            feedCardContent.put("links", links);
        }

        JSONObject json = new JSONObject();
        json.put("msgtype", this.getMsgType());
        json.put("feedCard", feedCardContent);

        return json.toJSONString();
    }
}
