package com.izakaya.sion.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // ✅ MenuService와 동일한 설정을 사용
    @Value("${app.upload.menu-dir:uploads/menu}")
    private String menuUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // ✅ 상대경로여도 "절대경로 URI"로 바꿔서 확실히 일치시킴
        Path uploadDir = Paths.get(menuUploadDir).toAbsolutePath().normalize();
        String uploadUri = uploadDir.toUri().toString(); // ex) file:/.../uploads/menu/

        registry.addResourceHandler("/uploads/menu/**")
                .addResourceLocations(uploadUri);
    }
}