package com.hahaha.fileparsingsystem.file.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @Description
 * @Author hahaha567
 * @Date 2023/6/19 17:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadMessage {
    private MultipartFile multipartFile;
    private Principal principal;
}
