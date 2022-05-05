package com.example.danque.api.controller;

import com.example.danque.common.Result;
import com.example.danque.mq.DanqueMQPublisherDelay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: RabbitMQ 发送消息
 * @ClassName:RabbitMQController
 * @author: danque
 * @date: 2022/4/27 10:06
 */
@RestController
@RequestMapping("/rabbitmq")
@Slf4j
public class RabbitMQController {

    @Autowired
    private DanqueMQPublisherDelay dnqueMQPublisherDelay;

    @PostMapping("/publishMessage")
    public Result publishMessage(@RequestBody String param) {
        try {
            dnqueMQPublisherDelay.sendMessage(param);
        } catch (Exception e) {
            log.error("RabbitMQController", e);
        }
        return Result.success(null);
    }


}
