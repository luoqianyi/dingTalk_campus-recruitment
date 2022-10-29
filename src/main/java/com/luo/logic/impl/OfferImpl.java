package com.luo.logic.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luo.config.Constants;
import com.luo.entity.LinkEntity;
import com.luo.logic.RecruitmentService;
import com.luo.utils.DingTalkUtils;

import java.io.IOException;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OfferImpl implements RecruitmentService {

    @Override
    public boolean orderRecruitment() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page",1);
        jsonObject.put("size",20);
        jsonObject.put("type","1");
        jsonObject.put("posInfoId","7");
        jsonObject.put("cityIdList","");
        String body = jsonObject.toJSONString();
        List<LinkEntity> linkEntities = new ArrayList<>();
        try {
            String url = "https://backservice.offerxiansheng.com/api/backend-service/bkd/campus-recruit";
            String enterpriseList = url + "/applet/page-list";
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
                                String detail = url + "/details?recruitId=";
                                JSONObject getJsonObject = DingTalkUtils.getGetJsonObject(detail + recruitId);
                                System.out.println(getJsonObject);
                                if (getJsonObject.getIntValue("code")==1000){
                                    String richText = getJsonObject.getJSONObject("data").getString("richText");
                                    JSONObject datas = getJsonObject.getJSONObject("data");
                                    String title = datas.getString("companyName");
                                    String content = datas.getString("content");
                                    String beginTime = datas.getString("beginTime");
                                    String messageUrl = datas.getString("url");
                                    String logoUrl = datas.getString("logoUrl");
                                    LinkEntity linkEntity = new LinkEntity();
                                    linkEntity.setMessageUrl(messageUrl).setPicUrl(logoUrl).setContent(content+"\n开始日期:"+beginTime).setTitle(title+ Constants.keyword);
                                    linkEntities.add(linkEntity);
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(postJsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return DingTalkUtils.batchSend(linkEntities);
    }
}
