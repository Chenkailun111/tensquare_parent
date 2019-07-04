package com.tensquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * JSON Web Token（JWT）
 * 是一个非常轻巧的规范。这个规范允许我们使用JWT在用户和服务器之间传递安全可靠的信息。
 * 它由三部分组成，头部、载荷与签名。头部（Header）
 * 头部用于描述关于该JWT的最基本的信息，例如其类型以及签名所用的算法等。
 *              {"typ":"JWT","alg":"HS256"}
 * 载荷就是存放有效信息的地方。这个名字像是特指飞机上承载的货品，这些有效信息包含三个部分
 * （1）标准中注册的声明（建议但不强制使用）
 * （2）公共的声明
 * 公共的声明可以添加任何的信息，一般添加用户的相关信息或其他业务需要的必要信息.
 * 但不建议添加敏感信息，因为该部分在客户端可解密.
 * （3）私有的声明
 * 私有声明是提供者和消费者所共同定义的声明，一般不建议存放敏感信息，
 * 因为base64是对称解密的，意味着该部分信息可以归类为明文信息。
 * 这个指的就是自定义的claim。比如前面那个结构举例中的admin和name都属于自定的claim。
 * 这些claim跟JWT标准规定的claim区别在于：JWT规定的claim，JWT的接收方在拿到JWT之后，
 * 都知道怎么对这些标准的claim进行验证(还不知道是否能够验证)；
 * 而 private claims不会验证，除非明确告诉接收方要对这些claim进行验证以及规则才行。

 * 签证（signature）
 * jwt的第三部分是一个签证信息，这个签证信息由三部分组成：
 *
 * header (base64后的)
 * payload (base64后的)
 * secret

 * 将这三部分用.连接成一个完整的字符串,构成了最终的jwt:
 */
public class CreateJwtTest {
    public static void main(String[] args) {
        //为了方便测试，我们将过期时间设置为1分钟
        long now = System.currentTimeMillis();//当前时间 long exp = now + 1000*60;//过期时间为1分钟
        long exp = now + 1000*60;//过期时间为1分钟
        JwtBuilder builder = Jwts.builder().setId("888") //id
                .setSubject("小白") //用户名
                .setIssuedAt(new Date())  //登录时间
                .signWith(SignatureAlgorithm.HS256, "itcast")
                //.setExpiration(new Date(exp))//设置过期时间
        //添加自定义的属性
                .claim("role","admin")
                .claim("logo","logo.png");
                //头部信息，用于设置签名秘钥
        System.out.println(builder.compact()); //得到字符串
//  eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NTc3MzMxNjZ9.Ti4RiWJYHNIJQiKgXMpwPJdXPl_tWY8fxbYiZj4OsSU
    }
}
