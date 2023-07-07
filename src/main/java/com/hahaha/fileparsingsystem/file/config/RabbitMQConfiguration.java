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

    @Bean
    public Queue uploadQueue() {
        return new Queue(UPLOAD_QUEUE);
    }

    @Bean
    public DirectExchange uploadExchange() {
        return new DirectExchange(UPLOAD_EXCHANGE);
    }

    @Bean
    public Binding uploadBinding() {
        return BindingBuilder.bind(uploadQueue()).to(uploadExchange()).with(UPLOAD_KEY);
    }


    @Bean
    public Queue updateQueue() {
        return new Queue(UPDATE_QUEUE);
    }

    @Bean
    public DirectExchange updateExchange() {
        return new DirectExchange(UPDATE_EXCHANGE);
    }

    @Bean
    public Binding updateBinding() {
        return BindingBuilder.bind(updateQueue()).to(updateExchange()).with(UPDATE_KEY);
    }


    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate();
    }

}
