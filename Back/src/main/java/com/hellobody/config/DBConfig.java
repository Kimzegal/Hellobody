package com.hellobody.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.hellobody.model.dao")
public class DBConfig {

}
