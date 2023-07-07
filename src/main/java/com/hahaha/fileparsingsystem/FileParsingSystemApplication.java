package com.hahaha.fileparsingsystem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class FileParsingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileParsingSystemApplication.class, args);
        log.info("项目启动");
    }

}
