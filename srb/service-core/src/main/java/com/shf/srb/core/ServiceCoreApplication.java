package com.shf.srb.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Slf4j
@ComponentScan({"com.shf.srb","com.shf.common"})
public class ServiceCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCoreApplication.class, args);

//        try {
//            SpringApplication.run(ServiceCoreApplication.class, args);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // （推荐）如果项目中存在日志框架，可以通过日志框架打印
//            log.debug("the exception is {}", e.getMessage(), e);
//        }

    }
}
