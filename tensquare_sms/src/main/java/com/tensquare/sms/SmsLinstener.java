package com.tensquare.sms;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsLinstener {
    /**
     *  发送短信
     * @param message
     */

    @Autowired
    private static SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;//模板编号

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;//签名

    @RabbitHandler
    public void sendSms(Map<String,String> message){
        String smscode = message.get("smscode");
        System.out.println("手机号："+message.get("mobile"));
        System.out.println("验证码："+smscode);
        try {
            smsUtil.sendSms(message.get("mobile"),template_code,sign_name,"{\"code\":\""+smscode+"\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
