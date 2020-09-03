package com.tr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tr")
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class
})
@MapperScan(basePackages = "com.tr.workflow.dal.mybatis.mapper")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}