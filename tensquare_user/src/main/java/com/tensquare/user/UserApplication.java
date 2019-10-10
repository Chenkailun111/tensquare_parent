package com.tensquare.user;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import util.IdWorker;
import util.JwtUtil;

@SpringBootApplication
@EnableEurekaClient
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
		System.out.println("启动了");
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}

	@Bean
	public JwtUtil jwtUtil(){
		return new JwtUtil();
	}

	/**
	 * 将加密的类注入进来,BCrypt强哈希方法 每次加密的结果都不一样。因为中间有一个加密值是随机的
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder(){

		return new BCryptPasswordEncoder();
	}

}
