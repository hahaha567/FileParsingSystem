package com.hahaha.fileparsingsystem.file.mq;

import com.hahaha.fileparsingsystem.file.config.RabbitMQConfiguration;
import org.apache.ibatis.annotations.Update;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description
 * @Author hahaha567
 * @Date 2023/6/19 17:10
 */

@Service
public class MQReceiver {

    @Value("${data.path}")
    private String dataPath;

    /**
     * @Description 将上传的文件存储在指定的路径上
     * @Date 17:46 2023/6/19
     * @Param [message]
     * @return void
     **/
    @RabbitListener(queues = RabbitMQConfiguration.UPLOAD_QUEUE)
    public void receiveUpload(Object message) throws IOException {
        UploadMessage uploadMessage = (UploadMessage) message;
        // 在data文件夹下创建每个用户的存储文件夹，savaPath为当前文件的存储路径
        String fileName = uploadMessage.getMultipartFile().getOriginalFilename();
        String savePath = dataPath + uploadMessage.getPrincipal().getName() + "/" + fileName;
        File file = new File(savePath);
        // 如果该用户是第一次上传文件，需要为该用户创建存储文件夹
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        // 写入文件
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(file)
        );
        bufferedOutputStream.write(uploadMessage.getMultipartFile().getBytes());
        bufferedOutputStream.close();
    }

    /**
     * @Description 将更新的文件存储在指定的路径上
     * @Date 10:13 2023/6/20
     * @Param [message]
     * @return void
     **/
    @RabbitListener(queues = RabbitMQConfiguration.UPDATE_QUEUE)
    public void receiveUpdate(Object message) throws IOException {
        UpdateMessage updateMessage = (UpdateMessage) message;
        // 写入文件
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(new File(updateMessage.getFileRecord().getStorePath()), false)
        );
        bufferedOutputStream.write(updateMessage.getMultipartFile().getBytes());
        bufferedOutputStream.close();
    }
}
