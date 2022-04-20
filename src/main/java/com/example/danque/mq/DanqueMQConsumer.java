package com.example.danque.mq;

import com.alibaba.fastjson.JSONObject;
import com.example.danque.api.mapper.VehicleMQRecordMapper;
import com.example.danque.entity.VehicleMQRecord;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author danque
 * @date 2022/4/19
 *
 * @desc
 */
@Component
@Slf4j
public class DanqueMQConsumer {

    @Autowired
    private VehicleMQRecordMapper vehicleMQRecordMapper;

    @RabbitListener(queues = "${danque.rabbitmq.test.queue.name}")
    @RabbitHandler
    public void messageConsumer(Message message, String mqMessage, Channel channel) throws Exception {
        //订阅队列消息控制接收速率  每次从队列中取一条消息
        channel.basicQos(1);
        log.info("DanqueMQConsumer-messageConsumer，receive message：{} ", mqMessage);
        //业务处理
        try {
            Thread.sleep(5000);
            VehicleMQRecord vehicleMQRecord = JSONObject.parseObject(mqMessage, VehicleMQRecord.class);
            vehicleMQRecordMapper.insert(vehicleMQRecord);

            //ack表示确认消息。multiple：false只确认该delivery_tag的消息，true确认该delivery_tag的所有消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            //Reject表示拒绝消息。requeue：false表示被拒绝的消息是丢弃；true表示重回队列
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            log.error("DanqueMQConsumer-messageConsumer-error：{}", e);
            //nack表示拒绝消息。multiple表示拒绝指定了delivery_tag的所有未确认的消息，requeue表示是不是重回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
        }
    }

}
