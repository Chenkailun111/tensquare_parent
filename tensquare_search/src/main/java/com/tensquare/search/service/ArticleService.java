package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    /**
     *  增加文章
     * @param article
     */
    public void add(Article article){
        article.setId( idWorker.nextId()+ "");
        articleDao.save(article);
    }


    /**
     * 根据关键字查询文章
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public PageResult findByKeywords(String keywords,int page,int size){
        PageRequest pageRequest=PageRequest.of(page-1,size);
        //title或者是内容进行搜索
        Page<Article> pageList = articleDao.findByTitleOrContentLike(keywords, keywords, pageRequest);
        return new PageResult<>(pageList.getTotalElements(),pageList.getContent());
    }


}