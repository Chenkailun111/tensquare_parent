package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save( @RequestBody Article article){
        articleService.add(article);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     *  搜索
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/{search}/{page}/{size}",method = RequestMethod.GET)
    public Result findByKeywords(@PathVariable String search,@PathVariable int page,@PathVariable int size ){
        PageResult pageResult = articleService.findByKeywords(search, page, size);
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }
}
