package com.blogpress;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 * @author JY
 */
@EnableAsync
@EnableTransactionManagement
@MapperScan(basePackages = "com.blogpress.*.dao")
@SpringBootApplication(scanBasePackages = "com.blogpress")
public class BlogPressApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogPressApplication.class, args);
    }

}
