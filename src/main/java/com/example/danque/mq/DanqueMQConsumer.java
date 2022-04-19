package com.example.danque.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author danque
 * @date 2022/4/19
 *
 * @desc
 */
@Component
@Slf4j
public class DanqueMQConsumer {

    @RabbitListener(queues = "${danque.rabbitmq.test.queue.name}")
    @RabbitHandler
    public void messageConsumer(Message message, Object obj, Channel channel) throws IOException {
        log.info("message===>{} ", message.getBody());
        log.info("obj===>{} ", JSON.toJSONString(obj));
        log.info("channel===>{} ", channel);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
