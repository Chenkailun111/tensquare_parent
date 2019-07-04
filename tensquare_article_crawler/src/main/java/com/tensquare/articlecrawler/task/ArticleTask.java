package com.tensquare.articlecrawler.task;

import com.tensquare.articlecrawler.pipeline.ArticleDbPipeline;
import com.tensquare.articlecrawler.pipeline.ArticleTextPipeline;
import com.tensquare.articlecrawler.processor.ArticleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@Component
public class ArticleTask {

    @Autowired
    private ArticleProcessor articleProcessor;

    @Autowired
    private ArticleDbPipeline articleDbPipeline;

    @Autowired
    private ArticleTextPipeline articleTextPipeline;

    @Autowired
    private RedisScheduler redisScheduler;

    /**
     * 爬取AI文章
     */
    @Scheduled(cron = "50 16 16 * * ?")
    public void aiTask(){
        System.out.println("爬取AI文章");
        Spider spider =Spider.create(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/ai");
        articleDbPipeline.setChannelId("ai");
        articleTextPipeline.setChannelId("ai");
        spider.addPipeline(articleDbPipeline);
        spider.addPipeline(articleTextPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
    }

    /**
     * 爬取db文章
     */
    @Scheduled(cron = "0 19 16 * * ?")
    public void dbTask(){
        System.out.println("爬取DB文章");
        Spider spider =Spider.create(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/db");
        articleDbPipeline.setChannelId("db");
        articleTextPipeline.setChannelId("db");
        spider.addPipeline(articleDbPipeline);
        spider.addPipeline(articleTextPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
    }

    /**
     * 爬取web文章
     */
    @Scheduled(cron = "0 21 16 * * ?")
    public void webTask(){
        System.out.println("爬取web文章");
        Spider spider =Spider.create(articleProcessor);
        spider.addUrl("https://blog.csdn.net/nav/web");
        articleDbPipeline.setChannelId("web");
        articleTextPipeline.setChannelId("web");
        spider.addPipeline(articleDbPipeline);
        spider.addPipeline(articleTextPipeline);
        spider.setScheduler(redisScheduler);
        spider.start();
    }


}
