package com.wsy.tizuobackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //覆盖所有请求
        registry.addMapping("/**")
                //允许携带cookie
                .allowCredentials(true)
                //允许的域名，注意要用Patterns，否则和允许cookie冲突
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "GET", "DELETE", "UPDATE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
