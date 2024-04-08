package com.wsy.tizuobackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.wsy.tizuobackend.mapper")
@EnableScheduling
public class TizuoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TizuoBackendApplication.class, args);
    }

}
