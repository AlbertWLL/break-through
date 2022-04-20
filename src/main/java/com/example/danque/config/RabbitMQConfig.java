package com.example.danque.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

//    @Bean
//    public MessageConverter messageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }

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
}
