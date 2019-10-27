package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

@Component
//@DefaultProperties(defaultFallback = "globalFallCallback") 全局服务降级回调方法
public class LabelClientImpl implements LabelClient {


    /**
     *  @HystrixCommand(
     *             //局部服务降级回调方法
     *             fallbackMethod = "fallback",
     *             commandProperties = {
     *                     //以下四个属性值可在HystrixCommandProperties类查找
     *                     //服务超时时长 ，线程隔离时长
     *                     @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="2100"),
     *                     //最多请求次数
     *                     @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
     *                     //熔断器重试时间（毫秒），默认5000ms
     *                     @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "2500"),
     *                     //触发熔断比例
     *                     @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
     *             }
     *
     *     )
     * @param id
     * @return
     */
    @Override
    public Result findById(String id) {
        //熔断器启动之后，进行服务降级
        System.out.println("熔断器启动了");
        return new Result(false, StatusCode.REMOTEERROR,"熔断器启动了");
    }

    //全局服务降级方法
    public String globalFallCallback(){
        return "服务器忙...";
    }
}
