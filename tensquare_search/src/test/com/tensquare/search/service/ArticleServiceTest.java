package com.tensquare.search.service;

import entity.PageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @ClassName ArticleServiceTest
 * @Author: Administrator
 * @DateTime: 2019/10/18 0018 17:12
 * @Version: 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;
    @Test
    public void add() {
    }

    @Test
    public void findByKeywords() {
        PageResult result = articleService.findByKeywords("spring", 1, 3);
        System.out.println(result);
    }
}