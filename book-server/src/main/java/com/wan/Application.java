package com.wan;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wanfeng
 * @date 2021/3/22 20:15
 */
@SpringBootApplication
@EnableSwagger2Doc
public class Application {
	public static void main(String[] args) {
		// 这句不加，虚拟机上项目无法启动
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(Application.class, args);
	}
}
