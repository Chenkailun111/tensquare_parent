package com.tensquare.ai;

import com.tensquare.ai.service.CnnService;
import com.tensquare.ai.service.Word2VecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class TrainTask {

    @Autowired
    private Word2VecService word2VecService;

    @Autowired
    private CnnService cnnService;

    @Scheduled(cron = "40 23 14 * * ?")
    public void trainModel(){
        System.out.println("合并分词语料库开始");
        word2VecService.mergeWord();
        System.out.println("合并分词语料库结束");

        System.out.println("构建词向量模型开始");
        word2VecService.build();
        System.out.println("构建词向量模型结束");

        System.out.println("构建卷积模型开始");
        cnnService.build();
        System.out.println("构建卷积模型结束");
    }

}
