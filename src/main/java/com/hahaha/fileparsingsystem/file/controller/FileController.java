package com.hahaha.fileparsingsystem.file.controller;


import com.hahaha.fileparsingsystem.file.entity.Comment;
import com.hahaha.fileparsingsystem.file.entity.Like;
import com.hahaha.fileparsingsystem.file.entity.Result;
import com.hahaha.fileparsingsystem.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;



@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    /**
     * @Description 上传文件
     * @Date 15:48 2023/6/19
     * @Param [multipartFile, principal]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam("file") MultipartFile multipartFile, Principal principal) throws IOException {
        Result result = fileService.saveFile(multipartFile, principal);
        return result;
    }

    /**
     * @Description 更新文件
     * @Date 15:48 2023/6/19
     * @Param [fileId, multipartFile, principal]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    @PutMapping("/updateFile")
    public Result updateFile(Long fileId, @RequestParam("file") MultipartFile multipartFile, Principal principal) throws IOException {
        Result result = fileService.updateFile(fileId, multipartFile, principal);
        return result;
    }

    /**
     * @Description 删除文件
     * @Date 15:49 2023/6/19
     * @Param [fileId, principal]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    @DeleteMapping("/deleteFile")
    public Result deleteFile(Long fileId, Principal principal) {
        Result result = fileService.deleteFile(fileId, principal);
        return result;
    }

    /**
     * @Description 下载文件
     * @Date 15:49 2023/6/19
     * @Param [fileId, response, principal]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    @GetMapping("/downloadFile")
    public Result downloadFile(Long fileId, HttpServletResponse response, Principal principal) throws IOException {
        Result result = fileService.downloadFile(fileId, response, principal);
        return result;
    }

    /**
     * @Description 浏览文件
     * @Date 15:49 2023/6/19
     * @Param [fileId, response, principal]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    @GetMapping("/viewFile/{fileId}")
    public Result viewFile(@PathVariable Long fileId, HttpServletResponse response, Principal principal) throws IOException {
        Result result = fileService.viewFile(fileId, response, principal);
        return result;
    }

    /**
     * @Description 点赞
     * @Date 15:50 2023/6/19
     * @Param [like]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    @PostMapping("/like")
    public Result like(@RequestBody Like like) {
        Result result = fileService.insertLike(like);
        return result;
    }

    /**
     * @Description 评论
     * @Date 15:50 2023/6/19
     * @Param [comment]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    @PostMapping("/comment")
    public Result comment(@RequestBody Comment comment) {
        Result result = fileService.insertComment(comment);
        return result;
    }



//    // 为了查询登录后的cookie
//    @RequestMapping("/t")
//    public String t(Principal principal) throws IOException {
//        return principal.getName();
//    }
//
//    // 测试redis
//    @RequestMapping("/redis")
//    public String r() {
//        return redisTemplate.opsForZSet().incrementScore("cookie", "hahaha", 1) + "";
//    }
}


