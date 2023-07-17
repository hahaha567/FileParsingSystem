package com.hahaha.fileparsingsystem.file.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author hahaha567
 * @Date 2023/6/19 16:12
 */
@Configuration
public class RabbitMQConfiguration {


    public static final String UPLOAD_QUEUE = "uploadQueue";
    public static final String UPLOAD_EXCHANGE = "uploadExchange";
    public static final String UPLOAD_KEY = "upload";

    public static final String UPDATE_QUEUE = "updateQueue";
    public static final String UPDATE_EXCHANGE = "updateExchange";
    public static final String UPDATE_KEY = "update";

    public static final String DEAD_QUEUE = "deadQueue";
    public static final String DEAD_EXCHANGE = "deadExchange";
    public static final String DEAD_KEY = "dead";

    @Bean
    public Queue uploadQueue() {
        return QueueBuilder.durable(UPLOAD_QUEUE)
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey(DEAD_KEY)
                .build();
    }

    @Bean
    public DirectExchange uploadExchange() {
        return ExchangeBuilder.directExchange(UPLOAD_EXCHANGE).build();
    }

    @Bean
    public Binding uploadBinding() {
        return BindingBuilder.bind(uploadQueue()).to(uploadExchange()).with(UPLOAD_KEY);
    }


    @Bean
    public Queue updateQueue() {
        return QueueBuilder.durable(UPDATE_QUEUE)
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey(DEAD_KEY)
                .build();
    }

    @Bean
    public DirectExchange updateExchange() {
        return ExchangeBuilder.directExchange(UPDATE_EXCHANGE).build();
    }

    @Bean
    public Binding updateBinding() {
        return BindingBuilder.bind(updateQueue()).to(updateExchange()).with(UPDATE_KEY);
    }

    /**
     * @Description 声明死信队列
     * @Date 20:10 2023/7/9
     * @Param []
     * @return org.springframework.amqp.core.Queue
     **/
    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    /**
     * @Description 声明死信交换机
     * @Date 20:08 2023/7/9
     * @Param []
     * @return org.springframework.amqp.core.DirectExchange
     **/
    @Bean
    public DirectExchange deadExchange() {
        return ExchangeBuilder.directExchange(DEAD_EXCHANGE).build();
    }

    /**
     * @Description 绑定死信队列和交换机
     * @Date 20:12 2023/7/9
     * @Param []
     * @return org.springframework.amqp.core.Binding
     **/
    @Bean
    public Binding deadBind() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(DEAD_KEY);
    }


    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate();
    }

}
