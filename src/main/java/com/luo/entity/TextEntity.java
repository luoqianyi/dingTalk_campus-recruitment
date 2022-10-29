package com.luo.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p color="red">TEXT类型消息</p>
 * <pre>
 * {
 *     "msgtype": "text",
 *     "text": {
 *         "content": "我就是我, 是不一样的烟火@156xxxx8827"
 *     },
 *     "at": {
 *         "atMobiles": [
 *             "156xxxx8827",
 *             "189xxxx8325"
 *         ],
 *         "isAtAll": false
 *     }
 * }
 * </pre>
 *
 */
@Data
@Accessors(chain = true)
public class TextEntity extends TypeEntity{

    private String msgType;

    // 显示内容
    private String content;

    // 是否at所有人
    private Boolean isAtAll;

    // 被@人的手机号(在content里添加@人的手机号)
    private List<String> atMobiles;


    public String getMsgType() {
        return "text";
    }


    public String getJSONObjectString() {
        // text类型
        JSONObject content = new JSONObject();
        content.put("content", this.getContent());
        
        // at some body
        JSONObject atMobile = new JSONObject();
        if(this.getAtMobiles().size() > 0){
            List<String> mobiles = new ArrayList<String>();
            for (int i=0;i<this.getAtMobiles().size();i++){
                mobiles.add(this.getAtMobiles().get(i));
            }
            if(mobiles.size()>0){
                atMobile.put("atMobiles", mobiles);
            }
            atMobile.put("isAtAll", this.getIsAtAll());
        }

        JSONObject json = new JSONObject();
        json.put("msgtype", this.getMsgType());
        json.put("text", content);
        json.put("at", atMobile);
        return json.toJSONString();
    }

}
