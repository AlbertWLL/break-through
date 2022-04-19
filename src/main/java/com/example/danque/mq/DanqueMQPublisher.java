package com.example.danque.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author danque
 * @date 2022/4/19
 * @desc
 */
@Component
@Slf4j
public class DanqueMQPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${danque.rabbitmq.test.exchange.name}")
    private String exchange;

    @Value("${danque.rabbitmq.test.routing.key}")
    private String routingkey;

    public void sendMessage(String message) {
        log.info("MQ发送的数据为：【{}】",message);
        rabbitTemplate.convertAndSend(exchange, routingkey,message);
        log.info("MQ发送的成功，exchange：【{}】，routingkey：【{}】，消息为：【{}】",exchange,routingkey,message);
    }

}
