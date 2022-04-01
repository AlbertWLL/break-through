//package com.example.danque.api.controller;
//
//import com.example.danque.api.bo.User;
//import com.example.danque.config.RedisTemplateService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @author danque
// * @date 2022/4/1
// * @desc
// */
//class RedisControllerTest {
//
//    @Autowired
//    RedisTemplateService redisTemplateService;
//
//    @Test
//    void redisTest() {
//        User user = new User();
//        user.setId(11);
//        user.setUsername("test");
//        user.setPassword("hello redis");
//        redisTemplateService.set("key1",user);
//
//        User us = redisTemplateService.get("key1",User.class);
//        System.out.println(us.getUsername()+":  "+us.getPassword());
//
//    }
//}
