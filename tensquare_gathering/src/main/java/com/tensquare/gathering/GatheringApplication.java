package com.tensquare.gathering;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * Spring Cache使用方法与Spring对事务管理的配置相似。
 * Spring Cache的核心就是对某个方法进行缓存，其实质就是缓存该方法的返回结果，
 * 并把方法参数和结果用键值对的方式存放到缓存中，
 * 当再次调用该方法使用相应的参数时，就会直接从缓存里面取出指定的结果进行返回。
 */
@SpringBootApplication
@EnableCaching  //开启缓存,使用这个注解的方法在执行后会缓存其返回结果。
@EnableEurekaClient
public class GatheringApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatheringApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	
}
