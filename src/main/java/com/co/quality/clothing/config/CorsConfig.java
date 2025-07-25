package com.co.quality.clothing.config;

import com.co.quality.clothing.utils.Constants;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins(
                        Constants.ADMIN_PAGE,
                        Constants.USERS_PAGE,
                        Constants.ADMIN_PAGE_PROD,
                        Constants.USERS_PAGE_PROD
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
