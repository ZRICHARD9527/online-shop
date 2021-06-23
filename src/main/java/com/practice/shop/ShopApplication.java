package com.practice.shop;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//本身就是Spring的一个组件
//SpringBootApplication标注为SpringBoot的应用
@SpringBootApplication
public class ShopApplication {
    public static void main(String[] args) {
        //将SpringBoot应用启动
        SpringApplication.run(ShopApplication.class, args);

    }

}
