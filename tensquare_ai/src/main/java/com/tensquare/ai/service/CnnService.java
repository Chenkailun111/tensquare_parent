package com.tensquare.ai.service;

import com.tensquare.ai.util.CnnUtil;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import util.IKUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;
@Service
public class CnnService {

    @Value("${ai.dataPath}")
    private String dataPath; //合并前的分词语料库

    @Value("${ai.vecModel}")
    private String vecModel; //词向量模型

    @Value("${ai.cnnModel}")
    private String cnnModel;//卷积神经网络模型

    /**
     * 生成卷积神经网络模型
     */
    public void build(){

        try {
            //1.创建计算图对象
            ComputationGraph net = CnnUtil.createComputationGraph(100);
            //2.加载训练数据集
            String [] childPaths={"ai","db","web"};
            DataSetIterator dataSet = CnnUtil.getDataSetIterator(dataPath, childPaths, vecModel);
            //3.训练模型
            net.fit(dataSet);
            //4.保存模型
            new File(cnnModel).delete();
            ModelSerializer.writeModel(net,cnnModel,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回各分类的百分比
     * @param content 文本内容
     * @return
     */
    public Map textClassify(String content){
        //1.分词
        try {
            content=IKUtil.spilt(content," ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //2.获取预言结果
        String [] childPaths={"ai","db","web"};
        Map<String, Double> predictions=null;
        try {
             predictions = CnnUtil.predictions(vecModel, cnnModel, dataPath, childPaths, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return predictions;
    }


}
