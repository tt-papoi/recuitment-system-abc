package com.learnthepath.recruitmentsystemabc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication()
public class RecruitmentSystemABCApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecruitmentSystemABCApplication.class, args);
    }
}
