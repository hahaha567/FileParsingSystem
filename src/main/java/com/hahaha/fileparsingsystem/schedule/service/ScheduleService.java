package com.hahaha.fileparsingsystem.schedule.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class ScheduleService {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * @Description 每天统计下载榜前十名
     * @Date 15:42 2023/6/19
     * @Param []
     * @return void
     **/
    @Scheduled(cron = "0 0 0 * * *")
    public void download() {
        Set<String> result = redisTemplate.opsForZSet().reverseRange("download", 0, 9);
        log.info("下载榜: " + result.toString());
        redisTemplate.delete("download");
    }

    /**
     * @Description 每天统计浏览榜前十名
     * @Date 15:42 2023/6/19
     * @Param []
     * @return void
     **/
    @Scheduled(cron = "0 0 0 * * *")
    public void browse() {
        Set<String> result = redisTemplate.opsForZSet().reverseRange("browse", 0, 9);
        log.info("浏览榜: " + result.toString());
        redisTemplate.delete("browse");
    }

    /**
     * @Description 每天统计点赞榜前十名
     * @Date 15:43 2023/6/19
     * @Param []
     * @return void
     **/
    @Scheduled(cron = "0 0 0 * * *")
    public void like() {
        Set<String> result = redisTemplate.opsForZSet().reverseRange("like", 0, 9);
        log.info("点赞榜: " + result.toString());
        redisTemplate.delete("like");
    }

    /**
     * @Description 每天统计评论榜前十名
     * @Date 15:43 2023/6/19
     * @Param []
     * @return void
     **/
    @Scheduled(cron = "0 0 0 * * *")
    public void comment() {
        Set<String> result = redisTemplate.opsForZSet().reverseRange("comment", 0, 9);
        log.info("评论榜: " + result.toString());
        redisTemplate.delete("comment");
    }




}
