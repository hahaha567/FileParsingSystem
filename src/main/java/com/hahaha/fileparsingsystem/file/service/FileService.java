package com.hahaha.fileparsingsystem.file.service;

import com.hahaha.fileparsingsystem.file.dao.*;
import com.hahaha.fileparsingsystem.file.entity.*;
import com.hahaha.fileparsingsystem.file.exception.NoAccessException;
import com.hahaha.fileparsingsystem.file.exception.OperationFailException;
import com.hahaha.fileparsingsystem.file.mq.MQSender;
import com.hahaha.fileparsingsystem.security.service.UserService;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;


@Service
@Transactional
public class FileService {

    @Value("${data.path}")
    private String dataPath;

    @Autowired
    FileMapper fileMapper;

    @Autowired
    DownloadMapper downloadMapper;

    @Autowired
    BrowseMapper browseMapper;

    @Autowired
    LikesMapper likesMapper;

    @Autowired
    CommentsMapper commentsMapper;

    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MQSender mqSender;

    /**
     * @Description 用于对文件进行保存
     * @Date 15:20 2023/6/19
     * @Param [multipartFile, principal]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    public Result saveFile(MultipartFile multipartFile, Principal principal) throws IOException {
        Long userId = userService.getIdByName(principal.getName());
        // 在data文件夹下创建每个用户的存储文件夹，savaPath为当前文件的存储路径
        String fileName = multipartFile.getOriginalFilename();
        String savePath = dataPath + principal.getName() + "/" + fileName;
        // 文件上传的任务传递给消息队列
        mqSender.sendUploadMessage(multipartFile, principal);
        // 将文件上传记录落库
        Timestamp time = new Timestamp(System.currentTimeMillis());
        fileMapper.insert(userId, fileName, savePath, time, time);
        Result result = new Result(ResultCode.SUCCESSFUL, "文件保存成功", null);
        return result;
    }
    
    /**
     * @Description 用于对文件进行更新
     * @Date 15:15 2023/6/19
     * @Param [fileId, multipartFile, principal]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    public Result updateFile(Long fileId, MultipartFile multipartFile, Principal principal) throws IOException {
        Long userId = userService.getIdByName(principal.getName());
        FileRecord fileRecord = fileMapper.getFileById(fileId);
        if (userId.equals(fileRecord.getUserId())) {
            // 更新数据库
            Timestamp time = new Timestamp(System.currentTimeMillis());
            fileMapper.updateFile(fileId, time);
            mqSender.sendUpdateMessage(multipartFile, fileRecord);
        } else {
            throw new NoAccessException();
        }
        Result result = new Result(ResultCode.SUCCESSFUL, "文件更新成功", null);
        return result;
    }

    /**
     * @Description 用于删除指定文件
     * @Date 15:20 2023/6/19
     * @Param [fileId, principal]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    public Result deleteFile(Long fileId, Principal principal) {
        Long userId = userService.getIdByName(principal.getName());
        FileRecord fileRecord = fileMapper.getFileById(fileId);
        if (userId.equals(fileRecord.getUserId())) {
            // 从数据库中删除记录
            fileMapper.deleteFile(fileId);
            // 从存储中删除文件
            File file = new File(fileRecord.getStorePath());
            if (!file.delete()) {
                throw new OperationFailException("文件删除失败");
            }
        } else {
            throw new NoAccessException();
        }
        Result result = new Result(ResultCode.SUCCESSFUL, "文件删除成功", null);
        return result;
    }

    /**
     * @Description 根据文件id获取指定文件
     * @Date 15:21 2023/6/19
     * @Param [id]
     * @return com.hahaha.fileparsingsystem.file.entity.FileRecord
     **/
    public FileRecord getFile(Long id) {
        return fileMapper.getFileById(id);
    }

    /**
     * @Description 根据文件id下载指定文件
     * @Date 15:23 2023/6/19
     * @Param [fileId, response, principal]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    public Result downloadFile(Long fileId, HttpServletResponse response, Principal principal) throws IOException {
        // 先根据文件id,拿到文件
        Long userId = userService.getIdByName(principal.getName());
        FileRecord fileRecord = getFile(fileId);
        insertDownload(userId, fileId);
        // 增加该文件的下载热度
        redisTemplate.opsForZSet().incrementScore("download", fileId, 1);
        // 设置字符集和内容类型
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Dispostion",
                String.format("attachment;filename=\"%s\"", URLEncoder.encode(fileRecord.getFileName(), "UTF-8")));
        // 将文件取出，并写入到response
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(new File(fileRecord.getStorePath()))
        );
        OutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[1024];
        int length;
        while ((length = bufferedInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        bufferedInputStream.close();
        outputStream.close();
        Result result = new Result(ResultCode.SUCCESSFUL, "文件下载成功", null);
        return result;
    }

    /**
     * @Description 对下载表插入数据
     * @Date 15:24 2023/6/19
     * @Param [userId, fileId]
     * @return void
     **/
    public void insertDownload(Long userId, Long fileId) {
        Download download = new Download();
        download.setUserId(userId);
        download.setFileId(fileId);
        download.setCreateTime(new Timestamp(System.currentTimeMillis()));
        downloadMapper.insert(download);
    }

    /**
     * @Description 根据文件id浏览文件
     * @Date 15:31 2023/6/19
     * @Param [fileId, response, principal]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    public Result viewFile(Long fileId, HttpServletResponse response, Principal principal) throws IOException {
        // 先根据文件id,拿到文件
        Long userId = userService.getIdByName(principal.getName());
        FileRecord fileRecord = getFile(fileId);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        insertBrowse(userId, fileId, time);
        // 增加该文件的阅读热度
        redisTemplate.opsForZSet().incrementScore("browse", fileId, 1);
        // 设置字符集和内容类型
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Dispostion",
                String.format("filename=\"%s\"", URLEncoder.encode(fileRecord.getFileName(), "UTF-8")));
        // 将文件取出，并写入到response
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(new File(fileRecord.getStorePath()))
        );
        OutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[1024];
        int length;
        while ((length = bufferedInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        bufferedInputStream.close();
        outputStream.close();
        Result result = new Result(ResultCode.SUCCESSFUL, "文件预览成功", null);
        return result;
    }

    /** 浏览数据落库
     * @Description
     * @Date 15:40 2023/6/19
     * @Param [userId, fileId, createTime]
     * @return void
     **/
    public void insertBrowse(Long userId, Long fileId, Timestamp createTime) {
        browseMapper.insert(userId, fileId, createTime);
    }

    /**
     * @Description 浏览数据落库
     * @Date 15:40 2023/6/19
     * @Param [like]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    public Result insertLike(Like like) {
        // 增加该文件的点赞热度
        redisTemplate.opsForZSet().incrementScore("like", like.getFileId(), 1);
        like.setCreateTime(new Timestamp(System.currentTimeMillis()));
        likesMapper.insert(like);
        Result result = new Result(ResultCode.SUCCESSFUL, "点赞成功", null);
        return result;
    }

    /**
     * @Description 点赞数据落库
     * @Date 15:41 2023/6/19
     * @Param [comment]
     * @return com.hahaha.fileparsingsystem.file.entity.Result
     **/
    public Result insertComment(Comment comment) {
        // 增加该文件的评论热度
        redisTemplate.opsForZSet().incrementScore("comment", comment.getFileId(), 1);
        comment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        commentsMapper.insert(comment);
        Result result = new Result(ResultCode.SUCCESSFUL, "评论成功", null);
        return result;
    }
}
