package com.example.danque.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    /**
     * 普通队列
     */
    @Value("${danque.rabbitmq.test.exchange.name}")
    private String danqueTestExchangeName;

    @Value("${danque.rabbitmq.test.routing.key}")
    private String danqueTestRoutingKey;

    @Value("${danque.rabbitmq.test.queue.name}")
    private String danqueTestQueueName;

    @Bean("danqueTestExchangeName")
    public TopicExchange etlTaskExchangeName(){
        return ExchangeBuilder.topicExchange(danqueTestExchangeName).durable(true).build();
    }

    @Bean("danqueTestQueueName")
    public Queue etlTaskQueueName(){
        return new Queue(danqueTestQueueName);
    }

    @Bean
    public Binding bindingExchangeMessage(@Qualifier("danqueTestQueueName") Queue queue, @Qualifier("danqueTestExchangeName") TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(danqueTestRoutingKey);
    }


    /**
     * 延迟队列
     */
    @Value("${danque.rabbitmq.delay.exchange.name}")
    private String delayExchangeName;

    @Value("${danque.rabbitmq.delay.routing.key}")
    private String delayRoutingKey;

    @Value("${danque.rabbitmq.delay.queue.name}")
    private String delayQueueName;

    /**
     * 死信队列
     * @return
     */
    @Value("${danque.rabbitmq.dead.exchange.name:danque.rabbitmq.dead.exchange}")
    private String deadLetterExchange;

    @Value("${danque.rabbitmq.dead.routing.key:danque.rabbitmq.dead.routing.key}")
    private String deadRoutingKey;

    @Value("${danque.rabbitmq.dead.queue.name}")
    private String deadQueueaName;


    @Bean("deadLetterQueue")
    public Queue deadLetterQueue(){
        return new Queue(deadQueueaName);
    }

    @Bean("deadLetterExchange")
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    public Binding deadLetterBinding(@Qualifier("deadLetterQueue") Queue queue, @Qualifier("deadLetterExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(deadRoutingKey);
    }

    @Bean("delayExchange")
    public TopicExchange delayExchangeName(){
        return ExchangeBuilder.topicExchange(delayExchangeName).durable(true).build();
    }

    @Bean("delaySyncQueue")
    public Queue delaySyncQueue(){
        Map<String, Object> args = new HashMap<>(2);
        args.put("x-dead-letter-exchange", deadLetterExchange);
        args.put("x-dead-letter-routing-key", deadRoutingKey);
        //死信队列exchange的类型
        args.put("x-delayed-typ", "direct");
        return QueueBuilder.durable(delayQueueName).withArguments(args).build();
    }

    @Bean
    public Binding delayBinding(@Qualifier("delaySyncQueue") Queue queue, @Qualifier("delayExchange") TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(delayRoutingKey);
    }



}
