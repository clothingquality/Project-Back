package com.co.quality.clothing.config;

import com.co.quality.clothing.utils.Constants;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins(
                        "https://qualityclothingcol.com",
                        "https://testsitedomainparapagina.io",
                        "http://127.0.0.1:5500",
                        "http://127.0.0.1:5502"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
