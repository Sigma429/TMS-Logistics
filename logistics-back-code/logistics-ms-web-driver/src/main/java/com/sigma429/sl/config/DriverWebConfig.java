package com.sigma429.sl.config;

import com.sigma429.sl.interceptor.TokenInterceptor;
import com.sigma429.sl.interceptor.UserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


/**
 * 司机端配置拦截器
 */
@Configuration
@Slf4j
public class DriverWebConfig implements WebMvcConfigurer {

    @Resource
    private UserInterceptor userInterceptor;

    @Resource
    private TokenInterceptor tokenInterceptor;

    private static final String[] EXCLUDE_PATH_PATTERNS = new String[]{
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-resources",
            "/v2/api-docs",
            "/login/**"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截的时候过滤掉swagger相关路径和登录相关接口
        registry.addInterceptor(userInterceptor).excludePathPatterns(EXCLUDE_PATH_PATTERNS).addPathPatterns("/**");
        registry.addInterceptor(tokenInterceptor).excludePathPatterns(EXCLUDE_PATH_PATTERNS).addPathPatterns("/**");
    }
}