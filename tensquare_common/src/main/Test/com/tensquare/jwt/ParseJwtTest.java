package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * 解析JWT的token信息
 */
public class ParseJwtTest {
    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NTc3MzQ0MDksInJvbGUiOiJhZG1pbiIsImxvZ28iOiJsb2dvLnBuZyJ9.1TAnf_9c-sPbdmozTSVuTPm5zXL2z8FnbzQPeSyrbig";
        //setSigningKey 何种盐方式进行解析
        Claims claims =
                Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("subject:" + claims.getSubject());   //用户
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy‐MM‐dd hh:mm:ss");
        System.out.println("IssuedAt:" + sdf.format(claims.getIssuedAt())); //登录时间
        //过期会报错，所以要处理异常
        //System.out.println("exp:"+sdf.format(claims.getExpiration()));
        System.out.println("自定义role属性"+claims.get("role"));
    }

}


