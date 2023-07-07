package com.hahaha.fileparsingsystem.file.mq;

import com.hahaha.fileparsingsystem.file.config.RabbitMQConfiguration;
import com.hahaha.fileparsingsystem.file.entity.FileRecord;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @Description
 * @Author hahaha567
 * @Date 2023/6/19 17:11
 */

@Service
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @Description 将上传文件的消息发送给队列
     * @Date 17:38 2023/6/19
     * @Param [multipartFile, principal]
     * @return void
     **/
    public void sendUploadMessage(MultipartFile multipartFile, Principal principal) {
        UploadMessage uploadMessage = new UploadMessage(multipartFile, principal);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.UPLOAD_EXCHANGE, RabbitMQConfiguration.UPLOAD_KEY, uploadMessage);
    }

    /**
     * @Description 将更新文件的消息发送给队列
     * @Date 10:18 2023/6/20
     * @Param [multipartFile, fileRecord]
     * @return void
     **/
    public void sendUpdateMessage(MultipartFile multipartFile, FileRecord fileRecord) {
        UpdateMessage updateMessage = new UpdateMessage(multipartFile, fileRecord);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.UPDATE_EXCHANGE, RabbitMQConfiguration.UPDATE_KEY, updateMessage);
    }
}
