package com.luo.config;

import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Constants {
    public static final String webhookUrl = "https://oapi.dingtalk.com/robot/send?access_token=";

    @Value("${dingtalk.keyword}")
    public static String keyword;

    @Value("${dingtalk.access_tokens}")
    public static String access_tokens;

    public static List<String> webhooks = Arrays.asList(access_tokens.split(",")).parallelStream().map(key->webhookUrl+key).collect(Collectors.toList());

    /** 钉钉限流 每分钟每个机器人最多20条消息 */
    public static volatile Integer maxMinuteCount = 20;


}
