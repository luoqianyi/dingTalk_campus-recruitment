package com.luo.logic;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luo.entity.LinkEntity;
import com.luo.entity.MarkdownEntity;
import com.luo.entity.TextEntity;
import com.luo.logic.impl.FreshGraduatesImpl;
import com.luo.logic.impl.OfferImpl;
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

    @Scheduled(cron = "${dingtalk.cron}")
    public void startSendMessage(){
        RecruitmentContext context = new RecruitmentContext(new FreshGraduatesImpl(),new OfferImpl());
        context.executeOrder();
    }

}
