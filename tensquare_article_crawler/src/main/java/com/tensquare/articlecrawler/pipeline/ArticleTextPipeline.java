package com.tensquare.articlecrawler.pipeline;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import util.HTMLUtil;
import util.IKUtil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@Component
public class ArticleTextPipeline implements Pipeline {

    @Value("${ai.dataPath}")
    private String dataPath;


    private String channelId;//频道ID

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

        String title=resultItems.get("title");//获取标题
        String content= HTMLUtil.delHTMLTag( resultItems.get("content"));//获取正文

        try {
            //分词
            PrintWriter printWriter=new PrintWriter(new File(dataPath+"/" +channelId+"/"+ UUID.randomUUID()+".txt"));
            printWriter.print(IKUtil.spilt(title + content, " "));
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
