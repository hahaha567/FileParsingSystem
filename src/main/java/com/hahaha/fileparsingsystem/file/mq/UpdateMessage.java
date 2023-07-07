package com.hahaha.fileparsingsystem.file.mq;

import com.hahaha.fileparsingsystem.file.entity.FileRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author hahaha567
 * @Date 2023/6/20 10:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessage {
    private MultipartFile multipartFile;
    private FileRecord fileRecord;
}
