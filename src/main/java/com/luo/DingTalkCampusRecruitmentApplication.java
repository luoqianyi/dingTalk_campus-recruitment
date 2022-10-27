package com.luo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DingTalkCampusRecruitmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingTalkCampusRecruitmentApplication.class, args);
    }

}
