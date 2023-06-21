package com.hellobody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication(exclude={MultipartAutoConfiguration.class})
public class HellobodyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellobodyApplication.class, args);
	}

}
