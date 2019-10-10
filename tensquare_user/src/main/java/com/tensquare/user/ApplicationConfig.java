package com.tensquare.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 将拦截器注入到容器之中
 */
@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器要拦截的对象和请求
        registry.addInterceptor(jwtFilter)
                .addPathPatterns("/**").excludePathPatterns("/**/login");
    }

}
