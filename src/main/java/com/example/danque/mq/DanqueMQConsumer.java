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
        log.info("DanqueMQConsumer-messageConsumer，receive message：{} ", mqMessage);
        //业务处理
        try {
            VehicleMQRecord vehicleMQRecord = JSONObject.parseObject(mqMessage, VehicleMQRecord.class);
            vehicleMQRecordMapper.insert(vehicleMQRecord);
        }catch (Exception e){
            log.error("DanqueMQConsumer-messageConsumer-error：{}", e);
        }
        //ACK机制
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
