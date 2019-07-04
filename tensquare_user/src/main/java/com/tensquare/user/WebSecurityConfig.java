package com.tensquare.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //表示开始说明需要的权限
                .antMatchers("/**").permitAll() //拦截路径；需要的权限：表示所有路径任何权限都可以访问
                .anyRequest().authenticated()  //任何请求需要认证后才能访问
                .and().csrf().disable(); //表示csrf失效，网站攻击；非这个网站强求不能访问
    }
}
