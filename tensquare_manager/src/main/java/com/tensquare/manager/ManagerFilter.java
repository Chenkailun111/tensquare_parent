package com.tensquare.manager;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return "pre"; //过滤类型，请求之前过滤，post是之后
    }

    @Override
    public int filterOrder() {
        return 0; //执行顺序，一堆过滤器数字越小越先执行
    }

    @Override
    public boolean shouldFilter() {
        return true; //过滤器是否开启
    }

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 返回任何一个object类型都继续执行
     * setSendZuulResponse(false)表示不再继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("管理后台网关zuul过滤器");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request =    requestContext.getRequest();
        if(request.getMethod().equals("OPTIONS")){
            //第一次进入网关会进入内部分发路径请求，要放行
            return null;
        }
        String url = request.getRequestURL().toString();
        if(url.indexOf("/admin/login")>=0){
            //登录放行
            System.out.println("登陆页面");
            return null;
        }
        //不是登录，后台登录接口请求鉴权
        String authHeader = request.getHeader("Authorization");
        if(authHeader!=null && authHeader.startsWith("Bearer ") ){
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if(claims!=null){
                if("admin_claims".equals(claims.get("roles"))){
                    //把头信息转发下去并放行
                    requestContext.addZuulRequestHeader("Authorization",authHeader);
                    System.out.println("token 验证通过，添加了头信息："+authHeader);
                    return null;
                }
            }
        }
        requestContext.setSendZuulResponse(false);// 终止运行,没有头信息
        requestContext.setResponseStatusCode(401);//http状态码
        requestContext.setResponseBody("无权访问");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");

        return null;
    }
}