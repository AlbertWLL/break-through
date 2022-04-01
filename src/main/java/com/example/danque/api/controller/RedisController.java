package com.example.danque.api.controller;

import com.alibaba.fastjson.JSON;
import com.example.danque.api.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/HelloWorld")
public class RedisController {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @GetMapping("/setKey")
    public void redisTest(){

        User user = new User();
        user.setId(11);
        user.setUsername("test");
        user.setPassword("hello redis");
        redisTemplate.opsForValue().set("key1", JSON.toJSONString(user));
        Object key1 = redisTemplate.opsForValue().get("key1");
        System.out.println("redis-value:"+key1);
    }
}
