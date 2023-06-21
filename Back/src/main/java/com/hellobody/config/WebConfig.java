package com.hellobody.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hellobody.interceptor.JwtInterceptor;
import com.hellobody.util.DownUtil;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	private String pathI = DownUtil.getPath("img");
	private String pathV = DownUtil.getPath("video");
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**")
        .addResourceLocations("file:"+pathI+"/");
        registry.addResourceHandler("/video/**")
        .addResourceLocations("file:"+pathV+"/");
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*");;
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setDefaultEncoding("UTF-8"); // 파일 인코딩 설정
	    multipartResolver.setMaxUploadSizePerFile(20 * 1024 * 1024); // 파일당 업로드 크기 제한 (20MB)
	    return multipartResolver;
	}
	
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
		.addPathPatterns("/api/**")
		.excludePathPatterns("/api/user/**",
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/v2/api-docs",
				"/api/league");
	}
	
}
