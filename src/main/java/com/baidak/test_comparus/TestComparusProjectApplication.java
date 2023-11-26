package com.baidak.test_comparus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TestComparusProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestComparusProjectApplication.class, args);
    }
}