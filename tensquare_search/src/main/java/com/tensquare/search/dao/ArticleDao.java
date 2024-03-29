package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *  文章数据访问接口
 */
public interface ArticleDao extends ElasticsearchRepository<Article,String> {

    //根据关键字查询文章
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);

}
