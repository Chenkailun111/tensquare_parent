package com.tensquare.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.tensquare.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsLinstener {
    /**
     *  发送短信，消费者项目一直开着，一有短信就消费掉，发送短信
     * @param message
     */

    SmsUtil smsUtil = new SmsUtil();

    @Value("${aliyun.sms.template_code}")
    private String template_code;//模板编号

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;//签名

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    @RabbitHandler
    public void sendSms(Map<String,String> message){//routingkey sms
        String smscode = message.get("smscode"); //验证码
        String mobile = message.get("mobile");
        System.out.println("手机号："+mobile); //手机号
        System.out.println("验证码："+smscode);
        try {
            //param 模板，key-value 对应短信验证码{\"code\":\"+"变量"+\"}
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(mobile, template_code, sign_name, accessKeyId, accessKeySecret, "{\"code\":\"" + smscode + "\"}");
            System.out.println("code:"+sendSmsResponse.getCode()+"/n"+
                    "bizID"+sendSmsResponse.getBizId()+"/n"
                    +"message"+sendSmsResponse.getMessage()+"/n"
                    +"requestID"+sendSmsResponse.getRequestId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
