package com.luo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Configuration
@Data
public class Constants {
    public static final String webhookUrl = "https://oapi.dingtalk.com/robot/send?access_token=";

    public static String keyword;

    public static List<String> webhooks;

    /** 钉钉限流 每分钟每个机器人最多20条消息 */
    public static volatile AtomicInteger maxMinuteCount = new AtomicInteger(20);




}
