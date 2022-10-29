package com.luo.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <p color="red">Markdown类型</p>
 * <pre>
 *     {
 *      "msgtype": "markdown",
 *      "markdown": {
 *          "title":"杭州天气",
 *          "text": "#### 杭州天气 @156xxxx8827\n" +
 *                  "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
 *                  "> ![screenshot](https://gw.alipayobjects.com/zos/skylark-tools/public/files/84111bbeba74743d2771ed4f062d1f25.png)\n"  +
 *                  "> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n"
 *      },
 *     "at": {
 *         "atMobiles": [
 *             "156xxxx8827",
 *             "189xxxx8325"
 *         ],
 *         "isAtAll": false
 *     }
 *  }
 * </pre>
 */
@Data
@Accessors(chain = true)
public class MarkdownEntity extends TypeEntity{
    private String msgType;

    // 显示标题
    private String title;

    // 显示内容
    private String content;

    // 是否at所有人
    private Boolean isAtAll;

    // 被@人的手机号(在content里添加@人的手机号)
    private List<String> atMobiles;


    public String getMsgType() {
        return "markdown";
    }

    
    public String getJSONObjectString() {
        // markdown类型
        JSONObject markdownContent = new JSONObject();
        markdownContent.put("title", this.getTitle());
        markdownContent.put("text", this.getContent());

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
        json.put("markdown", markdownContent);
        json.put("at", atMobile);
        return json.toJSONString();
    }
}
