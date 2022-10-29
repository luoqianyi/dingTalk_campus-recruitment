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

public class FreshGraduatesImpl implements RecruitmentService {

    @Override
    public boolean orderRecruitment() {
        List<LinkEntity> linkEntities = new ArrayList<>();
        try {
            String yingjieListUrl = "https://app.yingjiesheng.com/weixin/?module=recommedlist&version=200&jobterm=fulltime&page=1&location=0&industrty=11";
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
                                isTop = "";
                            }
                            String linkUrl = getJsonObject.getString("linkurl");
                            LinkEntity linkEntity = new LinkEntity();
                            linkEntity.setMessageUrl(linkUrl).setPicUrl("").setContent("开始日期:"+date+"\n"+isTop).setTitle(title+ Constants.keyword);
                            linkEntities.add(linkEntity);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return DingTalkUtils.batchSend(linkEntities);
    }

}
