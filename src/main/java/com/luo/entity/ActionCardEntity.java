package com.luo.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p color="red">ActionCard消息</p>
 * <pre>
 *     {
 *     "actionCard": {
 *         "title": "乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身",
 *         "text": "![screenshot](serverapi2/@lADOpwk3K80C0M0FoA)
 *  ### 乔布斯 20 年前想打造的苹果咖啡厅
 *  Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划",
 *         "hideAvatar": "0",
 *         "btnOrientation": "0",
 *         "singleTitle" : "阅读全文",
 *         "singleURL" : "https://www.dingtalk.com/"
 *     },
 *     "msgtype": "actionCard"
 * }
 * </pre>
 */
@Data
@Accessors(chain = true)
public class ActionCardEntity {

    private String msgType;

    // 显示标题
    private String title;

    // 显示内容 markdown格式的消息
    private String content;

    // 0-正常发消息者头像，1-隐藏发消息者头像
    private String hideAvatar;

    // 0-按钮竖直排列，1-按钮横向排列
    private String btnOrientation;

    // 单个按钮的方案。(设置此项和singleURL后btns无效)
    private String singleTitle;

    // 点击singleTitle按钮触发的URL
    private String singleURL;

    private List<ActionCardEntity> btns;

    public String getMsgType() {
        return "actionCard";
    }


    public String getJSONObjectString() {
        // text类型
        JSONObject actionCardContent = new JSONObject();
        actionCardContent.put("title", this.getTitle());
        actionCardContent.put("text", this.getContent());
        actionCardContent.put("hideAvatar", this.getHideAvatar());
        actionCardContent.put("btnOrientation", this.getBtnOrientation());
        if(!StringUtils.isEmpty(this.getSingleTitle()) && !StringUtils.isEmpty(this.getSingleURL())){
            actionCardContent.put("singleTitle", this.getSingleTitle());
            actionCardContent.put("singleURL", this.getSingleURL());
        }else{
            List<ActionCardEntity> btns = new ArrayList<ActionCardEntity>();
            for (int i=0;i<this.getBtns().size();i++){
                btns.add(this.getBtns().get(i));
            }
            if(this.getBtns().size()>0){
                actionCardContent.put("btns", btns);
            }
        }


        JSONObject json = new JSONObject();
        json.put("msgtype", this.getMsgType());
        json.put("actionCard", actionCardContent);

        return json.toJSONString();
    }
}
