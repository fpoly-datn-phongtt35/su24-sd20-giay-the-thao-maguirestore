package com.datn.maguirestore.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // áp dụng cho tất cả các endpoint
                .allowedOrigins("http://localhost:4200") // cho phép từ domain này
                .allowedMethods("GET", "POST", "PUT", "DELETE") ;// các phương thức HTTP được phép

    }
}
