package com.example.danque.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.danque.common.Result;
import com.example.danque.mq.DanqueMQPublisherDelay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: RabbitMQ 发送消息
 * @ClassName:RabbitMQController
 * @author: danque
 * @date: 2022/4/27 10:06
 */
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/putHash")
    public Result opsForHash(@RequestBody String param) {
        try {
            redisTemplate.opsForValue().set("key001",param);
            redisTemplate.opsForHash().put("cache","co_id_000111", JSON.toJSONString(param));


            Object o = redisTemplate.opsForHash().get("cache", "co_id_000111");
            System.out.println("???????????????????" + o);
        } catch (Exception e) {
            log.error("RabbitMQController", e);
        }
        return Result.success(null);
    }


}
