package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//发生错的回调方法：fallback
@FeignClient(value="tensquare-base",fallback = LabelClientImpl.class)
public interface LabelClient {
    /**
     *  根据ID查询标签
     *  @PathVariable("id")必须加上
     * @param id
     * @return
     */
    @RequestMapping(value="/label/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id);
}
