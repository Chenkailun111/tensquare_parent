package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(method= RequestMethod.POST) public Result save(@RequestBody Comment comment){
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "提交成功 ");
    }

    //文章id查询所有评论列表
    @RequestMapping(value="/article/{articleid}",method= RequestMethod.GET)
    public Result findByArticleid(@PathVariable String articleid){

        return new Result(true, StatusCode.OK, "查询成功", commentService.findByArticleid(articleid)); }

}

/**
 *
 * 为什么在吐槽和文章评论中使用Mongodb而不使用mysql?
 * 吐槽和评论都是数据量较大且价值较低的数据，为了减轻mysql的压力，我们使用 mongodb
 */