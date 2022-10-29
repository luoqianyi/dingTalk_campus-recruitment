package com.luo;

import com.luo.config.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableScheduling
public class DingTalkCampusRecruitmentApplication {

    @Value("${dingtalk.keyword}")
    public String key;

    @Value("${dingtalk.access_tokens}")
    public String access_tokens;

    public static void main(String[] args) {
        SpringApplication.run(DingTalkCampusRecruitmentApplication.class, args);
    }

    @PostConstruct
    public void initValue(){
        Constants.keyword = key;
        Constants.webhooks = Arrays.asList(access_tokens.split(",")).parallelStream().map(key->Constants.webhookUrl+key).collect(Collectors.toList());
    }

}
