package com.tensquare.ai.service;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class Word2VecService {

    @Value("${ai.wordLib}")
    private String wordLib; //合并后的分词语料库文件名

    @Value("${ai.dataPath}")
    private String dataPath; //合并前的分词语料库

    @Value("${ai.vecModel}")
    private String vecModel; //词向量模型


    public void mergeWord(){
        List<String> files = FileUtil.getFiles(dataPath);//获取需要合并的所有文件名称
        try {
            FileUtil.merge(wordLib,files);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 构建词向量模型
     */
    public void build(){

        try {
            //加载分词语料库数据集
            SentenceIterator iterator=new LineSentenceIterator(new File(wordLib));
            Word2Vec vec= new Word2Vec.Builder()
                    .minWordFrequency(5) //分词语料库中词语出现的最少次数
                    .iterations(1) //设置迭代次数
                    .layerSize(100) // 词向量维度
                    .seed(42)//随机种子
                    .windowSize(5) //当前词与预测词在一个句子中的最大距离
                    .iterate(iterator)
                    .build();
            vec.fit();//构建
            new File(vecModel).delete();//删除原模型
            WordVectorSerializer.writeWordVectors(vec,vecModel);//保存新模型
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
