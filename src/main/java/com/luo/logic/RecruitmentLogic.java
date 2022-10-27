package com.luo.logic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luo.entity.LinkEntity;
import com.luo.entity.MarkdownEntity;
import com.luo.entity.TextEntity;
import com.luo.utils.DingTalkUtils;
import com.luo.utils.Html2MarkdwonUtil;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RecruitmentLogic {

    private final String url="https://backservice.offerxiansheng.com/api/backend-service/bkd/campus-recruit";

    private final String enterpriseList = url+"/applet/page-list";

    private final String detail = url+"/details?recruitId=";

    private final String yingjieListUrl = "https://app.yingjiesheng.com/weixin/?module=recommedlist&version=200&jobterm=fulltime&page=1&location=0&industrty=11";

    @Value("${dingtalk.webhook}")
    private String webhook;

    @Value("${dingtalk.keyword}")
    private String keyword;

    @Scheduled(cron = "0 0 21 * * ?")
    public void startSendMessage(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page",1);
        jsonObject.put("size",20);
        jsonObject.put("type","1");
        jsonObject.put("posInfoId","7");
        jsonObject.put("cityIdList","");
        String body = jsonObject.toJSONString();
        // 发送offer先生订阅
        try {
            JSONObject postJsonObject = DingTalkUtils.getPostJsonObject(body, enterpriseList);
            int code = postJsonObject.getIntValue("code");
            if (code==1000){
                JSONObject data = (JSONObject) postJsonObject.get("data");
                if (data!=null || !data.isEmpty()){
                    JSONArray jsonArray = data.getJSONObject("data").getJSONArray("records");
                    if (!jsonArray.isEmpty()){
                        for (int i = jsonArray.size() - 1; i >= 0; i--) {
                            Date updateTime = jsonArray.getJSONObject(i).getSqlDate("updateTime");
                            Duration duration = Duration.between(updateTime.toLocalDate().atStartOfDay(), LocalDateTime.now());
                            if (duration.toDays()==0){
                                int recruitId = jsonArray.getJSONObject(i).getIntValue("recruitId");
                                JSONObject getJsonObject = DingTalkUtils.getGetJsonObject(detail + recruitId);
                                System.out.println(getJsonObject);
                                if (getJsonObject.getIntValue("code")==1000){
                                    String richText = getJsonObject.getJSONObject("data").getString("richText");
                                    JSONObject datas = getJsonObject.getJSONObject("data");
                                    String title = datas.getString("companyName");
//                                    String covert = Html2MarkdwonUtil.covert(richText);
//                                    MarkdownEntity markdownEntity = new MarkdownEntity();
                                    // 校招群
//                                                                        String webhook = "https://oapi.dingtalk.com/robot/send?access_token=49764e9bcfcfa736999f85e1e648f39e88f60f59a1ba684b4aa531c89c4b6710";
                                    // 内测群
//                                    String webhook = "https://oapi.dingtalk.com/robot/send?access_token=36de56feeb394a8da3f9c6e9cd0d6e790b920a9c8a80eaa4b72811e36d80bccb";
//                                    DingTalkUtils.sendToDingTalk(markdownEntity.getJSONObjectString(), webhook);
                                    String content = datas.getString("content");
                                    String beginTime = datas.getString("beginTime");
                                    String messageUrl = datas.getString("url");
                                    String logoUrl = datas.getString("logoUrl");
                                    LinkEntity linkEntity = new LinkEntity();
                                    linkEntity.setMessageUrl(messageUrl).setPicUrl(logoUrl).setContent(content+"\n开始日期:"+beginTime).setTitle(title+keyword);
                                    DingTalkUtils.sendToDingTalk(linkEntity.getJSONObjectString(),webhook);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(postJsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 发送应届生订阅
        try {
            JSONObject getJsonObject = DingTalkUtils.getGetJsonObject(yingjieListUrl);
            String result = getJsonObject.getString("result");
            if ("200".equals(result) && getJsonObject.getJSONObject("resultbody")!=null){
                JSONArray jsonArray = getJsonObject.getJSONObject("resultbody").getJSONArray("items");
                if (!jsonArray.isEmpty()){
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Date date = jsonArray.getJSONObject(i).getSqlDate("date");
                        Duration duration = Duration.between(date.toLocalDate().atStartOfDay(), LocalDateTime.now());
                        getJsonObject = jsonArray.getJSONObject(i);
                        System.out.println(getJsonObject);
                        if (duration.toDays()==0){
                            String title = getJsonObject.getString("title");
                            String isTop = getJsonObject.getString("istop");
                            if (isTop!=null&&isTop.equals("1")){
                                isTop = "推荐投递";
                            }else{
                                continue;
                            }
                            String linkUrl = getJsonObject.getString("linkurl");
                            LinkEntity linkEntity = new LinkEntity();
                            linkEntity.setMessageUrl(linkUrl).setPicUrl("").setContent("开始日期:"+date+"\n"+isTop).setTitle(title+keyword);
                            DingTalkUtils.sendToDingTalk(linkEntity.getJSONObjectString(),webhook);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
