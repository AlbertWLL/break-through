package com.example.danque.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author danque
 * @date 2022/4/19
 * @desc MQ死信(有死信队列、死信消息)
 */
@Component
@Slf4j
public class DanqueMQPublisherDelay {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${danque.rabbitmq.delay.exchange.name}")
    private String exchange;

    @Value("${danque.rabbitmq.delay.routing.key}")
    private String routingkey;

    public void sendMessage(String mqMessage) throws Exception {
        log.info("DanqueMQPublisherDelay-sendMessage,MQ发送的数据为：【{}】",mqMessage);
        try {
            rabbitTemplate.convertAndSend(exchange,routingkey, mqMessage, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    //设置消息的TTL为2分钟
                    message.getMessageProperties().setExpiration("30000");
                    return message;
                }
            });
            log.info("DanqueMQPublisherDelay-sendMessage,MQ发送的成功，exchange：【{}】，routingkey：【{}】，消息为：【{}】",exchange,routingkey,mqMessage);
        }catch (Exception e){
            log.error("DanqueMQPublisherDelay-sendMessage,MQ发送数据失败，异常信息为：{}", e);
            throw new Exception("DanqueMQPublisherDelay-sendMessage-error:{}", e);
        }
    }

}
