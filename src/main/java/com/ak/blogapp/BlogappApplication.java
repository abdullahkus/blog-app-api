package com.ak.blogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BlogappApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogappApplication.class, args);
    }

}
