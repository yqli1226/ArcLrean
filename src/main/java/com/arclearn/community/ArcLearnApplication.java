package com.arclearn.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.arclearn.community.mapper.auth")
public class ArcLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArcLearnApplication.class, args);
    }

}
